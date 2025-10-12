package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.dto.LoginRequest;
import com.xitian.smarthealthhub.service.UsersService;
import com.xitian.smarthealthhub.service.impl.UsersServiceImpl;
import com.xitian.smarthealthhub.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
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
    private UsersService usersService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, Object> refreshTokenRedisTemplate;

    /**
     * 用户登录接口
     * @param loginRequest 登录请求对象，包含手机号、密码和角色
     * @param response HttpServletResponse对象，用于设置HttpOnly Cookie
     * @return 操作结果
     */
    @PostMapping("/login")
    public ResultBean<Map<String, String>> login(@RequestBody LoginRequest loginRequest,
                                                 HttpServletResponse response) {
        try {
            String phone = loginRequest.getPhone();
            String password = loginRequest.getPassword();
            Byte role = loginRequest.getRole();
            
            // 1. 先从数据库获取用户信息，不进行认证
            Users user = usersService.getUserByPhone(phone);
            
            // 2. 检查用户是否存在
            if (user == null) {
                return ResultBean.fail(StatusCode.UNAUTHORIZED, "用户不存在");
            }
            
            // 3. 如果提供了角色参数，则验证用户角色是否匹配
            if (role != null && !user.getRole().equals(role)) {
                return ResultBean.fail(StatusCode.UNAUTHORIZED, "用户角色不匹配");
            }
            
            // 4. 检查用户状态
            if (user.getStatus() != 0) { // 0 表示正常状态
                return ResultBean.fail(StatusCode.FORBIDDEN, "用户账户状态异常");
            }

            // 5. 角色和状态都验证通过后，再进行密码认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(phone, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 6. 生成访问令牌（携带角色信息）
            String userRole = getUserRoleName(user.getRole());
            String accessToken = jwtUtil.generateAccessToken(user.getPhone(), userRole);
            
            // 7. 生成刷新令牌
            String refreshToken = jwtUtil.generateRefreshToken(user.getPhone());
            
            // 8. 将刷新令牌存储在Redis中
            String refreshKey = "refresh_token:" + user.getPhone();
            refreshTokenRedisTemplate.opsForValue().set(refreshKey, refreshToken, 30, TimeUnit.DAYS);

            // 9. 设置HttpOnly Cookie（仅设置访问令牌）
            response.addCookie(new jakarta.servlet.http.Cookie("access_token", accessToken) {{
                setHttpOnly(true);
                setSecure(false); // 生产环境中应设为true
                setPath("/");
                setMaxAge(Math.toIntExact(JwtUtil.JWT_TOKEN_VALIDITY));
            }});

            // 10. 构造响应（仅返回用户相关信息，不返回令牌）
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("role", user.getRole().toString());
            userInfo.put("username", user.getUsername());
            userInfo.put("message", "登录成功");

            return ResultBean.success(userInfo);
        } catch (UsernameNotFoundException e) {
            // 用户不存在或状态异常
            return ResultBean.fail(StatusCode.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            // 认证失败
            return ResultBean.fail(StatusCode.UNAUTHORIZED, "用户名或密码错误");
        }
    }

    /**
     * 刷新访问令牌
     * @param response HttpServletResponse对象，用于设置新的HttpOnly Cookie
     * @return 操作结果
     */
    @PostMapping("/refresh")
    public ResultBean<Map<String, String>> refresh(HttpServletResponse response, 
                                                   @CookieValue(name = "access_token", required = false) String accessToken) {
        try {
            if (accessToken == null) {
                return ResultBean.fail(StatusCode.UNAUTHORIZED, "缺少访问令牌");
            }
            
            // 从访问令牌中获取用户名
            String username = jwtUtil.getUsernameFromToken(accessToken);
            
            // 从Redis中获取存储的刷新令牌
            String refreshKey = "refresh_token:" + username;
            String storedRefreshToken = (String) refreshTokenRedisTemplate.opsForValue().get(refreshKey);
            
            // 检查刷新令牌是否有效
            if (storedRefreshToken == null) {
                return ResultBean.fail(StatusCode.UNAUTHORIZED, "无效的刷新令牌");
            }
            
            // 验证刷新令牌是否过期
            if (!jwtUtil.validateToken(storedRefreshToken, username)) {
                return ResultBean.fail(StatusCode.UNAUTHORIZED, "刷新令牌已过期");
            }
            
            // 获取用户信息以生成新的访问令牌
            Users user = usersService.getUserByPhone(username);
            if (user == null) {
                return ResultBean.fail(StatusCode.UNAUTHORIZED, "用户不存在");
            }
            
            // 生成新的访问令牌（携带角色信息）
            String userRole = getUserRoleName(user.getRole());
            String newAccessToken = jwtUtil.generateAccessToken(username, userRole);
            
            // 设置新的HttpOnly Cookie
            response.addCookie(new jakarta.servlet.http.Cookie("access_token", newAccessToken) {{
                setHttpOnly(true);
                setSecure(false); // 生产环境中应设为true
                setPath("/");
                setMaxAge(Math.toIntExact(JwtUtil.JWT_TOKEN_VALIDITY));
            }});

            Map<String, String> result = new HashMap<>();
            result.put("message", "令牌刷新成功");
            return ResultBean.success(result);
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.UNAUTHORIZED, "刷新令牌无效");
        }
    }

    /**
     * 用户登出
     * @param response HttpServletResponse对象，用于清除Cookie
     * @return 操作结果
     */
    @PostMapping("/logout")
    public ResultBean<String> logout(HttpServletResponse response, 
                                     @CookieValue(name = "access_token", required = false) String accessToken) {
        // 清除Cookie
        response.addCookie(new jakarta.servlet.http.Cookie("access_token", "") {{
            setHttpOnly(true);
            setSecure(false);
            setPath("/");
            setMaxAge(0); // 立即过期
        }});
        
        // 如果有访问令牌，则从Redis中删除对应的刷新令牌和用户权限缓存
        if (accessToken != null) {
            String username = jwtUtil.getUsernameFromToken(accessToken);
            String refreshKey = "refresh_token:" + username;
            refreshTokenRedisTemplate.delete(refreshKey);
            
            // 清除用户权限缓存
            if (usersService instanceof UsersServiceImpl) {
                ((UsersServiceImpl) usersService).clearUserCache(username);
            }
        }
        
        // 清除安全上下文
        SecurityContextHolder.clearContext();
        
        return ResultBean.success("登出成功");
    }
    
    /**
     * 根据角色代码获取角色名称
     * @param roleCode 角色代码
     * @return 角色名称
     */
    private String getUserRoleName(Byte roleCode) {
        return switch (roleCode) {
            case 0 -> "ROLE_ADMIN";
            case 1 -> "ROLE_DOCTOR";
            case 2 -> "ROLE_USER";
            default -> "ROLE_UNKNOWN";
        };
    }
}