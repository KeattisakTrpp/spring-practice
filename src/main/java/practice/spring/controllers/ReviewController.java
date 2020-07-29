package practice.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.spring.models.Review;
import practice.spring.services.ReviewService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;


    @GetMapping("/{id}")
    public Optional<Review> getReviewById(@PathVariable long id) {
        return reviewService.findReviewById(id);
    }

    @GetMapping
    public List<Review> getReviewByKeyword(@RequestParam String query) {
        return reviewService.findReviewByKeyword(query);
    }

    @PutMapping("/{id}")
    public Review updateReviewById(@PathVariable long id,@RequestBody Review review){
        return reviewService.updateReviewById(id, review.getReview());
    }
}
