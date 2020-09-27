package practice.spring.configs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import practice.spring.models.review.Review;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job transactionJob(Step step1) {
        return jobBuilderFactory.get("transactionJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public ItemReader<Review> fileReader() {
        return new JsonItemReaderBuilder<Review>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Review.class))
                .resource(new ClassPathResource("static/review.json"))
                .name("file-reader")
                .build();
    }

    @Bean
    public ItemWriter<Review> dbWriter() {
        return new JdbcBatchItemWriterBuilder<Review>()
                .dataSource(dataSource)
                .sql("INSERT INTO review VALUES (:reviewID, :review)")
                .beanMapped()
                .build();
    }

    @Bean
    public ItemProcessor<Review, Review> processor() {
        return new ItemProcessor<Review, Review>() {
            @Override
            public Review process(Review review) {
                return review;
            }
        };
    }

    @Bean
    public Step step(ItemReader<Review> step1ItemReader,
                     ItemProcessor<Review, Review> step1ItemProcessor,
                     ItemWriter<Review> step1ItemWriter) {
        return stepBuilderFactory.get("Import Review Data")
                .<Review, Review>chunk(10)
                .reader(step1ItemReader)
                .processor(step1ItemProcessor)
                .writer(step1ItemWriter)
                .build();
    }
}
