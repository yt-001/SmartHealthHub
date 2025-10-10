package com.xitian.smarthealthhub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Session配置类，启用Redis HTTP Session
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800) // 30分钟
public class SessionConfig {
    
    // Session相关配置已在application.yml中定义
    // 此类主要用于启用Redis HTTP Session功能
}