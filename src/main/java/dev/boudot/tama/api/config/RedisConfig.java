package dev.boudot.tama.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * This class represents the Redis configuration for the Tamagochi API.
 * It provides a RedisTemplate bean to be used for Redis operations.
 * The RedisTemplate is configured with a RedisConnectionFactory.
 */
@Configuration
public class RedisConfig {

    /**
     * Creates a RedisTemplate bean to be used for Redis operations.
     * @param redisConnectionFactory the RedisConnectionFactory to be used for the RedisTemplate
     * @return the RedisTemplate bean
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
