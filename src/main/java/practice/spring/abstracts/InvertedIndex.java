package practice.spring.abstracts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public abstract class InvertedIndex<K, V, M> {

    public abstract HashMap<K,Set<V>> getHashMap();

    public void push(K key, Set<V> value) {
        this.getHashMap().put(key, value);
    }

    public Set<V> getValue(K key) {
        return this.getHashMap().get(key);
    }

    public Set<K> getKeyByValue(V value) {
        Set<K> keys = new HashSet<>();
        for (Map.Entry map : this.getHashMap().entrySet()) {
            if(this.getHashMap().get(map.getKey()).contains(value)){
                keys.add((K) map.getKey());
            }
        }
        return keys;
    }

    public abstract void update(M m);

}
