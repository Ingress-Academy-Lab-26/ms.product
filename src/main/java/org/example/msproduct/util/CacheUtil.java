package org.example.msproduct.util;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

@Component
@RequiredArgsConstructor
public class CacheUtil {
    private final RedissonClient redisson;

    public <T> T getBucket(String cacheKey) {
        RBucket<T> bucket = redisson.getBucket(cacheKey);
        return bucket == null ? null :
                bucket.get();
    }

    @Async
    public <T> void setBucket(String cacheKey, T value, Long expireTime, TemporalUnit temporalUnit) {
        RBucket<T> bucket = redisson.getBucket(cacheKey);
        bucket.set(value);
        bucket.expire(Duration.of(expireTime, temporalUnit));
    }

    public void deleteFromCache(String cacheKey) {
        RBucket<Object> bucket = redisson.getBucket(cacheKey);
        if (bucket.isExists()) {
            bucket.delete();
        }
    }
}
