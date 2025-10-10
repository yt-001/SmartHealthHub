package com.xitian.smarthealthhub.config;

import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cookie配置类，用于配置标准的HttpOnly Cookie相关设置
 */
@Configuration
public class CookieConfig {

    /**
     * 配置SameSite属性
     * @return CookieSameSiteSupplier
     */
    @Bean
    public CookieSameSiteSupplier cookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofStrict();
    }
}