package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 认证控制器，处理登录和令牌刷新
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, Object> refreshTokenRedisTemplate;

    /**
     * 用户登录接口
     * @param phone 手机号
     * @param password 密码
     * @return 访问令牌和刷新令牌
     */
    @PostMapping("/login")
    public ResultBean<Map<String, String>> login(@RequestParam String phone, @RequestParam String password) {
        try {
            // 认证用户
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(phone, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(phone);

            // 生成访问令牌
            String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
            
            // 生成刷新令牌
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
            
            // 将刷新令牌存储在Redis中
            String refreshKey = "refresh_token:" + userDetails.getUsername();
            refreshTokenRedisTemplate.opsForValue().set(refreshKey, refreshToken, 30, TimeUnit.DAYS);

            // 构造响应
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);
            tokens.put("token_type", "Bearer");
            tokens.put("expires_in", String.valueOf(JwtUtil.JWT_TOKEN_VALIDITY));

            return ResultBean.success(tokens);
        } catch (Exception e) {
            return ResultBean.fail(com.xitian.smarthealthhub.bean.StatusCode.UNAUTHORIZED, "用户名或密码错误");
        }
    }

    /**
     * 刷新访问令牌
     * @param refreshToken 刷新令牌
     * @return 新的访问令牌
     */
    @PostMapping("/refresh")
    public ResultBean<Map<String, String>> refresh(@RequestParam String refreshToken) {
        try {
            // 验证刷新令牌
            String username = jwtUtil.getUsernameFromToken(refreshToken);
            
            // 从Redis中获取存储的刷新令牌
            String refreshKey = "refresh_token:" + username;
            String storedRefreshToken = (String) refreshTokenRedisTemplate.opsForValue().get(refreshKey);
            
            // 检查刷新令牌是否有效
            if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
                return ResultBean.fail(com.xitian.smarthealthhub.bean.StatusCode.UNAUTHORIZED, "无效的刷新令牌");
            }
            
            // 验证刷新令牌是否过期
            if (!jwtUtil.validateToken(refreshToken, username)) {
                return ResultBean.fail(com.xitian.smarthealthhub.bean.StatusCode.UNAUTHORIZED, "刷新令牌已过期");
            }
            
            // 生成新的访问令牌
            String newAccessToken = jwtUtil.generateAccessToken(username);
            
            // 构造响应
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", newAccessToken);
            tokens.put("token_type", "Bearer");
            tokens.put("expires_in", String.valueOf(JwtUtil.JWT_TOKEN_VALIDITY));

            return ResultBean.success(tokens);
        } catch (Exception e) {
            return ResultBean.fail(com.xitian.smarthealthhub.bean.StatusCode.UNAUTHORIZED, "刷新令牌无效");
        }
    }

    /**
     * 用户登出
     * @return 操作结果
     */
    @PostMapping("/logout")
    public ResultBean<String> logout() {
        // 获取当前认证用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            
            // 从Redis中删除刷新令牌
            String refreshKey = "refresh_token:" + username;
            refreshTokenRedisTemplate.delete(refreshKey);
            
            // 清除安全上下文
            SecurityContextHolder.clearContext();
        }
        
        return ResultBean.success("登出成功");
    }
}