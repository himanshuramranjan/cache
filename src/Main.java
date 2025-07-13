import models.Cache;
import models.ThreadSafeCache;
import strategy.lfu.LFUEvictionPolicy;
import strategy.lru.LRUEvictionPolicy;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Cache<Integer, String> lruCache = new ThreadSafeCache<>(3, new LRUEvictionPolicy<>());
        Cache<Integer, String> lfuCache = new ThreadSafeCache<>(3, new LFUEvictionPolicy<>());

        lruCache.put(1, "A");
        lruCache.put(2, "B");
        lruCache.put(3, "C");
        lruCache.get(1);
        lruCache.put(4, "D");

        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");
        lfuCache.get(1);
        lfuCache.get(1);
        lfuCache.get(2);
        lfuCache.put(4, "D");
        lfuCache.remove(List.of(4, 1));
    }
}