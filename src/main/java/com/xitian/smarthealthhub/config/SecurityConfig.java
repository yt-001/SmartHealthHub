package com.xitian.smarthealthhub.config;

import com.xitian.smarthealthhub.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
     * CORS配置源
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 允许所有来源（生产环境中应指定具体域名）
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 允许的HTTP方法
        configuration.setAllowedHeaders(Arrays.asList("*")); // 允许所有请求头
        configuration.setAllowCredentials(true); // 允许携带凭证（如Cookie）
        configuration.setExposedHeaders(Arrays.asList("Authorization")); // 暴露Authorization响应头
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 对所有路径应用CORS配置
        return source;
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
            // 启用CSRF保护
            .csrf(csrf -> csrf.disable()) // 当前仍禁用，如需启用请删除.disable()
            // 配置CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // 配置会话管理
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用无状态会话
            )
            // 配置授权规则
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/auth/login", "/auth/register", "/auth/doctor-authenticate", "/auth/patient-authenticate", "/auth/refresh").permitAll() // 允许访问公共接口
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // 管理员接口仅允许管理员访问
                .requestMatchers("/doctor/**").hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN") // 医生接口允许医生和管理员访问
                .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN") // 用户接口允许用户、医生和管理员访问
                .requestMatchers("/user/updateProfile").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN") // 用户信息更新接口允许所有角色访问
                .requestMatchers("/doctor/profiles").hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")
                .requestMatchers("/schedule/**").hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN") // 排班接口允许医生和管理员访问
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