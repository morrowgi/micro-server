package com.aol.micro.server.elasticache;
import java.util.Optional;

public interface DistributedCacheManager<V> {
        boolean add(String key, int exp, V value);
        Optional<V> get(String key);
}
