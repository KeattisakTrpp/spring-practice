package practice.spring.models.review;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="review")
@NoArgsConstructor
@Data
public class Review implements ReviewInterface {

    @Id
    private long reviewID;
    @Column(columnDefinition="TEXT")
    private String review;

    @Version
    @Column(name = "VERSION")
    private long version;

    public Review(long reviewID, String review) {
        this.reviewID = reviewID;
        this.review = review;
    }
}
