package com.aol.micro.server.memcached;



import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;


import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Slf4j
@Configuration
public class ConfigureMemcached {


    private final String hostname;
    private final int port;
    private final int retryAfterSecs;
    private final int maxRetries;

    @Autowired
    public ConfigureMemcached(@Value("${memcached.hostname:null}") String hostname,
                              @Value("${memcached.port:6379}") int port,
                              @Value("${memcached.retry.after.seconds:1}") int retryAfterSecs,
                              @Value("${memcached.max.retries:3}") int maxRetries) {
        this.hostname = hostname;
        this.port = port;
        this.retryAfterSecs = retryAfterSecs;
        this.maxRetries = maxRetries;
    }


    @Bean(name = "transientCache")
    public DistributedCacheManager transientCache() throws IOException, URISyntaxException {
        try {
            log.info("Creating Memcached Data connection for memcached cluster: {}", hostname);
            return new TransientMemcachedDataConnection(createMemcachedClient(), retryAfterSecs, maxRetries);
        }
     catch (Exception e) {
            log.error("Failed to create transient data connection", e);
            return null;
        }
    }

    @Bean(name = "memcachedClient")
    public MemcachedClient createMemcachedClient() throws IOException {
        try {
            log.info("Starting an instance of memcache client towards memcached cluster");
            return new MemcachedClient(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            log.error("Could not initilise connection to memcached cluster", e);
            return null;
        }

    }
}

