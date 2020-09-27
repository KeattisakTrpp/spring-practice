package practice.spring.services;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import practice.spring.models.review.Review;
import practice.spring.repositories.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewServiceTest {
    Mockery mockery = new Mockery();

    private ReviewRepository reviewRepository;
    private Review mockReview;
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        reviewRepository = mockery.mock(ReviewRepository.class);
        mockReview = new Review(1, "this is review 1");
        reviewService = new ReviewService();
        reviewService.setReviewRepository(reviewRepository);
    }

    @Test
    void findReviewById() {
        mockery.checking(new Expectations(){{
            oneOf(reviewRepository).findById(1l);
            will(returnValue(Optional.of(mockReview)));
        }});

        Optional<Review> review = reviewService.findReviewById(1);

        Assert.assertEquals(mockReview.getReview() ,review.get().getReview());
        Assert.assertEquals(mockReview.getReviewID() ,review.get().getReviewID());

    }

    @Test
    void findReviewByKeyword() {
        mockery.checking(new Expectations(){{
            oneOf(reviewRepository).findByReviewContaining("review") ;
            will(returnValue(List.of(mockReview)));
        }});

        List<Review> reviews = reviewService.findReviewByKeyword("review");
        List<Review> expect = prepareData();

        Assert.assertEquals(expect.get(0).getReview() ,reviews.get(0).getReview());
        Assert.assertEquals(expect.get(0).getReviewID() ,reviews.get(0).getReviewID());

    }

    @Test
    void updateReviewById() {
        Review newReview = new Review(1, "this is new review");
        mockery.checking(new Expectations(){{
            oneOf(reviewRepository).findById(newReview.getReviewID());
            will(returnValue(Optional.of(newReview)));

            oneOf(reviewRepository).save(newReview);
            will(returnValue(newReview));
        }});

        Review review = reviewService.updateReviewById(newReview.getReviewID(), newReview.getReview());

        Assert.assertEquals(newReview.getReviewID(), review.getReviewID());
        Assert.assertEquals(newReview.getReview(), review.getReview());

    }

    private List<Review> prepareData() {
        List<Review> reviewList = new ArrayList<>();
        Review review = new Review(1, "this is <keyword> review </keyword> 1");
        reviewList.add(review);
        return reviewList;
    }
}
