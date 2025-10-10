// StatusCode.java
package com.xitian.smarthealthhub.bean;

public enum StatusCode {
    // 成功
    SUCCESS(200, "成功"),

    // 通用错误
    FAIL(400, "失败"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    REQUEST_TIMEOUT(408, "请求超时"),

    // 服务器错误
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    NOT_IMPLEMENTED(501, "服务未实现"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    SYSTEM_ERROR(505, "系统错误"),

    // 业务相关错误
    VALIDATION_ERROR(1001, "参数验证失败"),
    BUSINESS_ERROR(1002, "业务处理失败"),
    DATA_NOT_FOUND(1003, "数据不存在"),
    DATA_DUPLICATE(1004, "数据重复"),

    // 用户相关
    USER_NOT_FOUND(2001, "用户不存在"),
    USER_DISABLED(2002, "用户已被禁用"),
    USERNAME_OR_PASSWORD_ERROR(2003, "用户名或密码错误"),
    USER_ALREADY_EXISTS(2004, "用户已存在"),

    // 权限相关
    PERMISSION_DENIED(3001, "权限不足"),
    ROLE_NOT_FOUND(3002, "角色不存在"),
    
    // 认证相关
    INVALID_TOKEN(4001, "无效的令牌"),
    EXPIRED_TOKEN(4002, "令牌已过期");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
