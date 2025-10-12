package com.xitian.smarthealthhub.config;

import com.xitian.smarthealthhub.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsersService usersService;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 密码编码器
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器
     * @param authConfig 认证配置
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * 配置安全过滤链
     * @param http HttpSecurity对象
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用CSRF保护（根据实际需求决定是否启用）
            .csrf(AbstractHttpConfigurer::disable)
            // 配置会话管理
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用无状态会话
            )
            // 配置授权规则
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/auth/login", "/auth/register", "/auth/doctor-authenticate", "/auth/patient-authenticate", "/auth/refresh", "/test/**").permitAll() // 允许访问公共接口
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // 管理员接口仅允许管理员访问
                .requestMatchers("/doctor/**").hasAuthority("ROLE_DOCTOR") // 医生接口仅允许医生访问
                .requestMatchers("/user/**").hasAuthority("ROLE_USER") // 用户接口仅允许用户访问
                .anyRequest().authenticated() // 其他请求需要认证
            )
            // 禁用表单登录
            .formLogin(AbstractHttpConfigurer::disable)
            // 禁用HTTP基本认证
            .httpBasic(AbstractHttpConfigurer::disable)
            // 添加JWT认证过滤器
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}