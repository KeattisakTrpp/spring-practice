package practice.spring.services;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.spring.exceptions.ResourceNotFoundException;
import practice.spring.abstracts.InvertedIndex;
import practice.spring.models.index.ReviewIndex;
import practice.spring.models.review.Review;
import practice.spring.repositories.ReviewRepository;

import java.util.*;

@Service
@Setter
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    private InvertedIndex invertedIndex = new ReviewIndex();


    public Optional<Review> findReviewById(long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isEmpty()) {
           throw new ResourceNotFoundException("Review not found");
        }
        return  review;
    }

    public List<Review> findReviewByKeyword(String query){
        List<Review> reviews;
        Set<Long> setId = new HashSet<>();

        if(invertedIndex.getValue(query) == null ){
            reviews = reviewRepository.findByReviewContaining(query);
            for (Review review: reviews) {
                setId.add(review.getReviewID());
            }
            invertedIndex.push(query, setId);
        } else {
            setId = invertedIndex.getValue(query);
            reviews = reviewRepository.findAllById(setId);
        }
        return addTag(query, reviews);
    }

    public Review updateReviewById(long id, String review) {
        Optional<Review> record = reviewRepository.findById(id);
        if(record.isEmpty()){
            throw new ResourceNotFoundException("Review not found");
        }
        invertedIndex.update(record.get());
        record.get().setReview(review);
        return reviewRepository.save(record.get());
    }

    private List<Review> addTag(String query, List<Review> reviews) {
        List<Review> result = new ArrayList<>();
        for(Review review: reviews){
            result.add(new Review(review.getReviewID(), review.getReview().replaceAll(query, "<keyword> " + query + " </keyword>")));
        }
        return result;
    }
}
