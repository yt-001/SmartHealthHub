package com.xitian.smarthealthhub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * 刷新令牌配置类
 */
@Configuration
public class RefreshTokenConfig {

    /**
     * 配置用于存储刷新令牌的Redis模板
     * @param connectionFactory Redis连接工厂
     * @return RedisTemplate
     */
    @Bean("refreshTokenRedisTemplate")
    public RedisTemplate<String, Object> refreshTokenRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}