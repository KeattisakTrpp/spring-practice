package practice.spring;


import practice.spring.abstracts.InvertedIndex;
import practice.spring.models.Review;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReviewIndex extends InvertedIndex<String, Long, Review> {

    private HashMap<String, Set<Long>> map;

    public ReviewIndex() {
        this.map = new HashMap<>();
    }

    @Override
    public HashMap<String, Set<Long>> getHashMap() {
        return this.map;
    }

    @Override
    public void update(Review review) {
        //add id to keyword
        for (Map.Entry map : this.getHashMap().entrySet()) {
            // if review contain new keyword
            if(review.getReview().contains(map.getKey().toString())) {
                this.getHashMap().get(map.getKey()).add(review.getReviewID());
            }
        }

        // remove id from keyword
        Set<String> keyList = this.getKeyByValue(review.getReviewID());
        for(String key: keyList){
            // if review dose not contain old keyword
            if(!review.getReview().contains(key)) {
                this.getHashMap().get(key).remove(review.getReviewID());
            }
        }
    }
}
