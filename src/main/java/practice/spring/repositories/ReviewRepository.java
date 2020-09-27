package practice.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.spring.models.review.Review;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewContaining(String query);

}
