# SmartHealthHub 社区卫生服务管理系统

## 项目简介

SmartHealthHub是一个基于Spring Boot的社区卫生服务管理系统，提供用户管理、医生档案、病历记录等功能。

## 技术栈

- Spring Boot 3.5.6
- Java 17
- MySQL 8.0+
- MyBatis Plus
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
   - 设置SameSite属性为 `Strict`

2. **安全配置类**: `com.xitian.smarthealthhub.config.SecurityConfig`
   - 配置Spring Security安全策略
   - 设置无状态会话管理策略

3. **依赖项**:
   - lettuce-core
   - spring-boot-starter-security
   - jjwt-api (JWT库)

4. **application.yml配置**:
   ```yaml
   spring:
     servlet:
       session:
         cookie:
           http-only: true
           secure: false # 在生产环境中应设为true
           same-site: strict
           max-age: 1800
   
   server:
     port: 9000
   ```

### 安全特性

- **HttpOnly**: Cookie无法通过JavaScript访问，防止XSS攻击
- **Secure**: 在生产环境中应启用，确保Cookie只在HTTPS下传输
- **SameSite**: 设置为Strict，防止CSRF攻击
- **JWT令牌**: 无状态认证机制，支持访问令牌和刷新令牌
- **Spring Security**: 提供全面的安全保护，包括认证和授权

### 测试接口

项目提供了基于JWT的认证接口:

1. 用户登录: `POST /auth/login`
2. 刷新令牌: `POST /auth/refresh`
3. 用户登出: `POST /auth/logout`

以及测试用户创建接口:

4. 创建测试用户: `POST /test/createUser`

## 部署说明

1. 确保安装了Java 17+和MySQL 8.0+
2. 创建数据库并导入数据结构
3. 修改`application.yml`中的数据库配置
4. 运行项目: `mvn spring-boot:run`

## 注意事项

1. 数据库连接信息需要根据实际情况修改
2. 在生产环境中，应该使用HTTPS