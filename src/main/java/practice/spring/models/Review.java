package practice.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="review")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {
    @Id
    private long reviewID;

    @Column(columnDefinition="TEXT")
    private String review;

    public Review(String review) {
        this.review = review;
    }
}
