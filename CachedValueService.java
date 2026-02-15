package com.test.springBoot.assignment;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachedValueService {

    private final AtomicInteger counter = new AtomicInteger();

    @Cacheable(cacheNames = "default")
    public String compute(String input) {
        // return a value that changes on each actual invocation so we can detect caching
        int cnt = counter.incrementAndGet();
        return input + "-v" + cnt;
    }

    public int getInvocationCount() {
        return counter.get();
    }
}

