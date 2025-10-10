# SmartHealthHub 社区卫生服务管理系统

## 项目简介

SmartHealthHub是一个基于Spring Boot的社区卫生服务管理系统，提供用户管理、医生档案、病历记录等功能。

## 技术栈

- Spring Boot 3.5.6
- Java 17
- MySQL 8.0+
- MyBatis Plus
- Redis (用于Session管理)
- Spring Security
- Lombok
- Maven

## 功能模块

1. 用户管理
2. 医生档案管理
3. 病历记录管理
4. Session管理（支持标准HttpOnly Cookie）

## 标准HttpOnly Cookie 配置

本项目已经配置了标准的HttpOnly Cookie支持，以增强安全性，防止XSS攻击窃取Cookie信息。

同时，项目也支持基于JWT的认证方式，包含访问令牌和刷新令牌机制。

### 配置说明

1. **Cookie配置类**: `com.xitian.smarthealthhub.config.CookieConfig`
   - 设置Cookie名称为 `SMART_HEALTH_SESSION`
   - 启用HttpOnly属性
   - 设置SameSite属性为 `Strict`
   - Cookie路径设置为 `/`

2. **Session配置类**: `com.xitian.smarthealthhub.config.SessionConfig`
   - 启用Redis HTTP Session
   - 设置Session超时时间为30分钟

3. **Web安全配置类**: `com.xitian.smarthealthhub.config.WebSecurityConfig`
   - 配置Spring Security安全策略
   - 设置会话管理策略
   - 配置登出时删除Cookie

4. **依赖项**:
   - spring-session-data-redis
   - lettuce-core
   - spring-boot-starter-security

5. **application.yml配置**:
   ```yaml
   spring:
     session:
       store-type: redis
       timeout: 1800s
       redis:
         namespace: smarthealth:session
   
     data:
       redis:
         host: localhost
         port: 6379
         timeout: 2000ms
         lettuce:
           pool:
             max-active: 8
             max-idle: 8
             min-idle: 0
             max-wait: -1ms
   
   server:
     servlet:
       session:
         cookie:
           http-only: true
           secure: false # 在生产环境中应设为true
           same-site: strict
           max-age: 1800
   ```

### 安全特性

- **HttpOnly**: Cookie无法通过JavaScript访问，防止XSS攻击
- **Secure**: 在生产环境中应启用，确保Cookie只在HTTPS下传输
- **SameSite**: 设置为Strict，防止CSRF攻击
- **Redis存储**: Session存储在Redis中，支持分布式部署
- **Spring Security**: 提供全面的安全保护，包括认证和授权

### 测试接口

项目提供了用于测试Session功能的接口:

1. 创建Session: `POST /session/create`
2. 获取Session信息: `GET /session/info`
3. 销毁Session: `DELETE /session/destroy`

同时也提供了基于JWT的认证接口:

1. 用户登录: `POST /auth/login`
2. 刷新令牌: `POST /auth/refresh`
3. 用户登出: `POST /auth/logout`

## 部署说明

1. 确保安装了Java 17+、MySQL 8.0+和Redis
2. 创建数据库并导入数据结构
3. 修改`application.yml`中的数据库和Redis配置
4. 运行项目: `mvn spring-boot:run`

## 注意事项

1. Redis服务器需要在localhost:6379运行
2. 数据库连接信息需要根据实际情况修改
3. 在生产环境中，应该设置Redis密码并使用HTTPS