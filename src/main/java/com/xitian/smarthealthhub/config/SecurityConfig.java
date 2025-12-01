package com.xitian.smarthealthhub.config;

import com.xitian.smarthealthhub.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Map;

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
                // 公共接口路径 - 无需认证
                .requestMatchers(PermissionConfig.getPublicPaths()).permitAll()
                // 动态配置权限路径
                .requestMatchers("/**").access((authentication, object) -> {
                    String requestPath = object.getRequest().getRequestURI();
                    
                    // 匹配路径和角色
                    for (Map.Entry<String, RoleCombination> entry : PermissionConfig.getPathRoleMapping().entrySet()) {
                        String pattern = entry.getKey();
                        RoleCombination roleCombination = entry.getValue();
                        
                        // 路径匹配
                        if (pattern.equals("/**") || matchPath(pattern, requestPath)) {
                            return authorizationManager(authentication.get(), roleCombination.getRoles());
                        }
                    }
                    
                    // 默认情况下需要认证
                    return authorizationManager(authentication.get(), new String[]{});
                })
            )
            // 禁用表单登录
            .formLogin(AbstractHttpConfigurer::disable)
            // 禁用HTTP基本认证
            .httpBasic(AbstractHttpConfigurer::disable)
            // 添加JWT认证过滤器
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    /**
     * 路径匹配
     * @param pattern 模式
     * @param path 路径
     * @return 是否匹配
     */
    private boolean matchPath(String pattern, String path) {
        if (pattern.endsWith("/**")) {
            String prefix = pattern.substring(0, pattern.length() - 3);
            return path.startsWith(prefix);
        } else if (pattern.contains("*")) {
            // 简单的通配符匹配
            String regex = pattern.replace("*", "[^/]*");
            return path.matches(regex);
        } else {
            return path.equals(pattern);
        }
    }
    
    /**
     * 授权管理器
     * @param authentication 认证信息
     * @param requiredRoles 需要的角色
     * @return 授权决策
     */
    private AuthorizationDecision authorizationManager(
            Authentication authentication,
            String[] requiredRoles) {
        
        if (requiredRoles.length == 0) {
            // 如果没有指定角色，则需要认证
            return new AuthorizationDecision(authentication.isAuthenticated());
        }
        
        // 检查用户是否是管理员（管理员可以访问所有接口）
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        
        if (isAdmin) {
            // 管理员可以访问所有接口
            return new AuthorizationDecision(true);
        }
        
        // 检查用户是否具有所需角色之一
        boolean hasRole = Arrays.stream(requiredRoles)
                .anyMatch(role -> authentication.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals(role)));
        
        return new AuthorizationDecision(hasRole);
    }
}