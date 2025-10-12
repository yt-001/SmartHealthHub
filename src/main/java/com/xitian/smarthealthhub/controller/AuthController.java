package com.xitian.smarthealthhub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.dto.LoginRequestDTO;
import com.xitian.smarthealthhub.domain.dto.UserRegistrationDTO;
import com.xitian.smarthealthhub.domain.dto.DoctorAuthenticationDTO;
import com.xitian.smarthealthhub.domain.dto.PatientAuthenticationDTO;
import com.xitian.smarthealthhub.service.UsersService;
import com.xitian.smarthealthhub.service.DoctorProfilesService;
import com.xitian.smarthealthhub.service.UserProfilesService;
import com.xitian.smarthealthhub.service.impl.UsersServiceImpl;
import com.xitian.smarthealthhub.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
    private DoctorProfilesService doctorProfilesService;
    
    @Autowired
    private UserProfilesService userProfilesService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, Object> refreshTokenRedisTemplate;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户注册接口
     * @param userRegistrationDTO 用户注册信息
     * @return 操作结果
     */
    @PostMapping("/register")
    public ResultBean<String> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            // 1. 检查密码和确认密码是否一致
            if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())) {
                return ResultBean.fail(StatusCode.BAD_REQUEST, "两次输入的密码不一致");
            }
            
            // 2. 检查手机号是否已存在
            Users existingUser = usersService.getUserByPhone(userRegistrationDTO.getPhone());
            if (existingUser != null) {
                return ResultBean.fail(StatusCode.USER_ALREADY_EXISTS, "该手机号已被注册");
            }
            
            // 3. 创建新用户
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            // 对密码进行加密
            String encodedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());
            
            // 处理角色参数（已通过DTO验证确保是"0"、"1"或"2"）
            String roleStr = userRegistrationDTO.getRole();
            Byte role = null;
            if (roleStr != null && !roleStr.isEmpty()) {
                role = Byte.parseByte(roleStr);
            }
            
            // 根据角色设置默认状态：
            // 管理员(0)或医生(1)设置为未激活状态(2)
            // 患者(2)设置为正常状态(0)
            Byte status = (role != null && (role == 0 || role == 1)) ? (byte) 2 : (byte) 0;
            
            Users newUser = Users.builder()
                    .username(userRegistrationDTO.getUsername())
                    .realName(userRegistrationDTO.getRealName())
                    .phone(userRegistrationDTO.getPhone())
                    .email(userRegistrationDTO.getEmail())
                    .gender(userRegistrationDTO.getGender())
                    .role(role != null ? role : (byte) 2) // 默认为患者角色
                    .status(status) // 根据角色设置状态
                    .isDeleted((byte) 0) // 未删除
                    .birthDate(userRegistrationDTO.getBirthDate())
                    .passwordHash(encodedPassword)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            
            // 4. 保存用户到数据库
            boolean saved = usersService.save(newUser);
            if (saved) {
                return ResultBean.success("注册成功");
            } else {
                return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "注册失败，请稍后重试");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "注册过程中发生错误: " + e.getMessage());
        }
    }

    /**
     * 用户登录接口
     * @param loginRequestDTO 登录请求对象，包含手机号、密码和角色
     * @param response HttpServletResponse对象，用于设置HttpOnly Cookie
     * @return 操作结果
     */
    @PostMapping("/login")
    public ResultBean<Map<String, String>> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO,
                                                 HttpServletResponse response) {
        try {
            String phone = loginRequestDTO.getPhone();
            String password = loginRequestDTO.getPassword();
            Byte role = loginRequestDTO.getRole();
            
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
     * 医生认证接口
     * @param doctorAuthDTO 医生认证信息
     * @return 操作结果
     */
    @PostMapping("/doctor-authenticate")
    public ResultBean<String> doctorAuthenticate(@Valid @RequestBody DoctorAuthenticationDTO doctorAuthDTO) {
        try {
            // 1. 检查用户是否存在且为医生角色
            Users user = usersService.getById(doctorAuthDTO.getUserId());
            if (user == null) {
                return ResultBean.fail(StatusCode.USER_NOT_FOUND, "用户不存在");
            }
            
            if (user.getRole() != 1) { // 1 表示医生角色
                return ResultBean.fail(StatusCode.BAD_REQUEST, "该用户不是医生角色");
            }
            
            // 2. 检查是否已经存在医生档案
            DoctorProfiles existingProfile = doctorProfilesService.getOne(
                new LambdaQueryWrapper<DoctorProfiles>()
                    .eq(DoctorProfiles::getUserId, doctorAuthDTO.getUserId())
            );
            
            if (existingProfile != null) {
                return ResultBean.fail(StatusCode.BAD_REQUEST, "该医生已存在认证信息");
            }
            
            // 3. 创建医生档案
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            DoctorProfiles doctorProfile = DoctorProfiles.builder()
                    .userId(doctorAuthDTO.getUserId())
                    .deptId(doctorAuthDTO.getDeptId())
                    .title(doctorAuthDTO.getTitle())
                    .specialty(doctorAuthDTO.getSpecialty())
                    .qualificationNo(doctorAuthDTO.getQualificationNo())
                    .isDeleted((byte) 0) // 未删除
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            
            // 4. 保存医生档案到数据库
            boolean saved = doctorProfilesService.save(doctorProfile);
            if (saved) {
                return ResultBean.success("医生认证信息提交成功，待审核通过");
            } else {
                return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "医生认证信息提交失败，请稍后重试");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "医生认证过程中发生错误: " + e.getMessage());
        }
    }
    
    /**
     * 患者认证接口
     * @param patientAuthDTO 患者认证信息
     * @return 操作结果
     */
    @PostMapping("/patient-authenticate")
    public ResultBean<String> patientAuthenticate(@Valid @RequestBody PatientAuthenticationDTO patientAuthDTO) {
        try {
            // 1. 检查用户是否存在且为患者角色
            Users user = usersService.getById(patientAuthDTO.getUserId());
            if (user == null) {
                return ResultBean.fail(StatusCode.USER_NOT_FOUND, "用户不存在");
            }
            
            if (user.getRole() != 2) { // 2 表示患者角色
                return ResultBean.fail(StatusCode.BAD_REQUEST, "该用户不是患者角色");
            }
            
            // 2. 检查是否已经存在患者档案
            LambdaQueryWrapper<UserProfiles> userProfilesQuery = new LambdaQueryWrapper<>();
            userProfilesQuery.eq(UserProfiles::getUserId, patientAuthDTO.getUserId());
            UserProfiles existingProfile = userProfilesService.getOne(userProfilesQuery);
            
            if (existingProfile != null) {
                return ResultBean.fail(StatusCode.BAD_REQUEST, "该患者已存在认证信息");
            }
            
            // 3. 创建患者档案
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            UserProfiles userProfile = UserProfiles.builder()
                    .userId(patientAuthDTO.getUserId())
                    .idCard(patientAuthDTO.getIdCard())
                    .bloodType(patientAuthDTO.getBloodType())
                    .allergyHistory(patientAuthDTO.getAllergyHistory())
                    .chronicDisease(patientAuthDTO.getChronicDisease())
                    .currentSymptoms(patientAuthDTO.getCurrentSymptoms())
                    .currentPlan(patientAuthDTO.getCurrentPlan())
                    .nextStep(patientAuthDTO.getNextStep())
                    .address(patientAuthDTO.getAddress())
                    .emergencyName(patientAuthDTO.getEmergencyName())
                    .emergencyPhone(patientAuthDTO.getEmergencyPhone())
                    .idCardVerified((byte) 2) // 设置为审核中状态
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            
            // 4. 保存患者档案到数据库
            boolean saved = userProfilesService.save(userProfile);
            if (saved) {
                return ResultBean.success("患者认证信息提交成功");
            } else {
                return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "患者认证信息提交失败，请稍后重试");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "患者认证过程中发生错误: " + e.getMessage());
        }
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