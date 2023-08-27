package dev.boudot.tama.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.boudot.tama.api.GameObjects.Player;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Player> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Player> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // template.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(Player.class));
        return template;
    }

    @Bean
    public Jackson2HashMapper playerHashMapper(ObjectMapper objectMapper) {
        return new Jackson2HashMapper(objectMapper, true);
    }

}
