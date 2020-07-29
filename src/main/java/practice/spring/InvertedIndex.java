package practice.spring;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class InvertedIndex {
    private HashMap<String, Set<Long>> indexed;

    public InvertedIndex(){
        this.indexed = new HashMap<>();
    }

    public void push(String key, Set<Long> value) {
        this.indexed.put(key, value);
    }

    public Set<Long> getValue(String key) {
        return this.indexed.get(key);
    }

    public Set<String> getKeyByValue(long value) {
        Set<String> keys = new HashSet<>();
        for (Map.Entry map : this.indexed.entrySet()) {
            if(this.indexed.get(map.getKey().toString()).contains(value)){
                keys.add(map.getKey().toString());
            }
        }
        return keys;
    }

    public void update(long id, String review) {
        // add id to keyword
        for (Map.Entry map : indexed.entrySet()) {
            // if review contain new keyword
            if(review.contains(map.getKey().toString())) {
                indexed.get(map.getKey().toString()).add(id);
            }
        }

        // remove id from keyword
        Set<String> keyList = this.getKeyByValue(id);
        for(String key: keyList){
            // if review dose not contain old keyword
            if(!review.contains(key)) {
                this.indexed.get(key).remove(id);
            }
        }
    }

}
