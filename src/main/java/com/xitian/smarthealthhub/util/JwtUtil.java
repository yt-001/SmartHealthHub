package com.xitian.smarthealthhub.util;

import com.xitian.smarthealthhub.service.UsersService;
import com.xitian.smarthealthhub.service.impl.UsersServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT工具类，用于生成和验证访问令牌与刷新令牌
 */
@Component
public class JwtUtil {

    // 令牌有效期（5小时）
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    // 刷新令牌有效期（30天）
    public static final long REFRESH_TOKEN_VALIDITY = 30 * 24 * 60 * 60;

    // 密钥（使用固定密钥，避免每次重启后令牌失效）
    private static final String SECRET = "smart_health_hub_secret_key_for_jwt_token_generation_and_validation";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());

    /**
     * 生成令牌
     * @param claims 自定义声明
     * @param subject 主题（通常是用户标识）
     * @param validity 有效期
     * @return 令牌
     */
    private String doGenerateToken(Map<String, Object> claims, String subject, long validity) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity * 1000))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 生成访问令牌
     * @param userDetails 用户信息
     * @param role 用户角色
     * @return 访问令牌
     */
    public String generateAccessToken(String userDetails, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // 在令牌中添加角色信息
        return doGenerateToken(claims, userDetails, JWT_TOKEN_VALIDITY);
    }
    
    /**
     * 生成访问令牌（为了向后兼容）
     * @param userDetails 用户信息
     * @return 访问令牌
     */
    public String generateAccessToken(String userDetails) {
        return generateAccessToken(userDetails, null);
    }

    /**
     * 生成刷新令牌
     * @param userDetails 用户信息
     * @return 刷新令牌
     */
    public String generateRefreshToken(String userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails, REFRESH_TOKEN_VALIDITY);
    }

    /**
     * 从令牌中获取用户名
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    /**
     * 从令牌中获取角色
     * @param token 令牌
     * @return 角色
     */
    public String getRoleFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("role", String.class));
    }

    /**
     * 从令牌中获取过期时间
     * @param token 令牌
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从令牌中获取声明信息
     * @param token 令牌
     * @param claimsResolver 声明解析器
     * @param <T> 声明类型
     * @return 声明值
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 解析令牌中的所有声明
     * @param token 令牌
     * @return 所有声明
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    /**
     * 验证令牌是否过期
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 验证令牌
     * @param token 令牌
     * @param userDetails 用户信息
     * @return 是否有效
     */
    public Boolean validateToken(String token, String userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails) && !isTokenExpired(token));
    }
}