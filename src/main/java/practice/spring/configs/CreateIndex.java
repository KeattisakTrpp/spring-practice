package practice.spring.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import practice.spring.jsonTemplate.ReviewJson;
import practice.spring.models.index.IndexSingleTon;
import practice.spring.models.index.ReviewIndex;

@Configuration
@Slf4j
public class CreateIndex {
    private Resource foodIndex = new ClassPathResource("static/food-index.json");

    @Bean
    CommandLineRunner runner() {
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();
            ReviewJson[] indexJson = objectMapper.readValue(foodIndex.getInputStream(), ReviewJson[].class);
            ReviewIndex invertedIndex = new ReviewIndex();

            for (ReviewJson r : indexJson) {
                invertedIndex.push(r.getQuery(), r.getIndex());
            }
            IndexSingleTon.getInstance().setInvertedIndex(invertedIndex);
            log.info("Create Index Finish");

        };
    }

}
