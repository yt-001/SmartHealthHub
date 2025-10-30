package com.xitian.smarthealthhub.config;

import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.service.UsersService;
import com.xitian.smarthealthhub.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private RedisTemplate<String, Object> refreshTokenRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String username = null;
        String refreshToken = extractTokenFromCookie(request, "refresh_token");

        // 直接从刷新令牌获取用户名（如果存在）
        if (refreshToken != null) {
            try {
                // 从刷新令牌中获取用户名
                username = jwtUtil.getUsernameFromToken(refreshToken);
                
                // 检查Redis中是否存在有效的刷新令牌
                String refreshKey = "refresh_token:" + username;
                String storedRefreshToken = (String) refreshTokenRedisTemplate.opsForValue().get(refreshKey);
                
                // 验证刷新令牌是否有效
                if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken) 
                    || !jwtUtil.validateToken(refreshToken, username)) {
                    username = null;
                }
            } catch (Exception e) {
                logger.warn("无法解析刷新令牌: " + e.getMessage());
                username = null;
            }
        }

        // 如果令牌有效且上下文中没有认证信息，则设置认证
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
            
            // 从刷新令牌中获取角色信息
            String role = null;
            if (refreshToken != null) {
                try {
                    role = jwtUtil.getRoleFromToken(refreshToken);
                } catch (Exception e) {
                    logger.warn("无法从刷新令牌中获取角色信息: " + e.getMessage());
                }
            }
            
            // 如果刷新令牌中没有角色信息，说明令牌有问题，不进行认证
            if (role != null && !role.isEmpty()) {
                // 构建UserDetails对象
                userDetails = new User(username, "", true, true, true, true,
                        Collections.singletonList(new SimpleGrantedAuthority(role)));
            }

            if (userDetails != null) {
                // 验证用户状态是否正常
                Users user =
                    ((UsersService) userDetailsService).getUserByPhone(username);
                if (user != null && user.getStatus() == 0) { // 0 表示正常状态
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        
        chain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookieName.equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return null;
    }
}