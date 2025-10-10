package com.xitian.smarthealthhub.config;

import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Cookie配置类，用于配置标准的HttpOnly Cookie相关设置
 */
@Configuration
public class CookieConfig {

    /**
     * 配置Cookie序列化器，设置标准的HttpOnly和SameSite属性
     * @return CookieSerializer
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        // 设置Cookie名称
        serializer.setCookieName("SMART_HEALTH_SESSION");
        // 设置Cookie路径
        serializer.setCookiePath("/");
        // 设置HttpOnly属性，防止XSS攻击
        serializer.setUseHttpOnlyCookie(true);
        // 设置Secure属性，确保Cookie只在HTTPS下传输
        serializer.setUseSecureCookie(false); // 在生产环境中应设为true
        // 设置SameSite属性，防止CSRF攻击
        serializer.setSameSite("Strict");
        // 设置Cookie域（根据实际部署情况修改）
        // serializer.setDomainName("yourdomain.com");
        return serializer;
    }

    /**
     * 配置SameSite属性
     * @return CookieSameSiteSupplier
     */
    @Bean
    public CookieSameSiteSupplier cookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofStrict();
    }
}