# 气象局文件上传下载管理系统

## 项目简介

政府内部使用的文件上传下载管理系统，采用前后端分离架构。

## 技术栈

### 后端
- Spring Boot 2.7.18
- MyBatis Plus 3.5.5
- JDK 1.8
- Oracle Database
- HttpSession 会话管理

### 前端
- Vue 3
- Vite
- Element Plus
- Pinia
- Vue Router
- Axios

## 功能特性

### 所有用户
- 用户登录/退出
- 文件上传
- 文件下载
- 查看自己的文件
- 修改密码

### 管理员
- 用户管理（添加/删除/重置密码）
- 查看所有文件
- 删除文件
- 查看操作日志

## 项目结构

```
D:\气象局上传下载系统
├── backend/                    # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/filesystem/
│   │   │   │       ├── FileSystemApplication.java    # 启动类
│   │   │   │       ├── config/                       # 配置类
│   │   │   │       ├── controller/                   # 控制器
│   │   │   │       ├── service/                      # 业务逻辑
│   │   │   │       ├── mapper/                       # 数据访问
│   │   │   │       ├── entity/                       # 实体类
│   │   │   │       ├── dto/                          # 数据传输对象
│   │   │   │       ├── vo/                           # 视图对象
│   │   │   │       ├── enums/                        # 枚举类
│   │   │   │       ├── interceptor/                  # 拦截器
│   │   │   │       ├── exception/                    # 异常处理
│   │   │   │       └── util/                         # 工具类
│   │   │   └── resources/
│   │   │       ├── application.yml                   # 主配置文件
│   │   │       ├── application-dev.yml               # 开发环境配置
│   │   │       └── application-prod.yml              # 生产环境配置
│   │   └── test/
│   └── pom.xml                                        # Maven配置
├── frontend/                   # 前端项目
│   ├── src/
│   │   ├── api/                # API接口
│   │   ├── components/         # 公共组件
│   │   ├── views/              # 页面组件
│   │   ├── router/             # 路由配置
│   │   ├── store/              # 状态管理
│   │   ├── utils/              # 工具类
│   │   ├── App.vue             # 根组件
│   │   └── main.js             # 入口文件
│   ├── index.html
│   ├── package.json
│   └── vite.config.js
└── database/                   # 数据库脚本
    └── oracle_init.sql         # Oracle初始化脚本
```

## 快速开始

### 1. 数据库准备

#### 1.1 创建Oracle用户

```sql
-- 以sysdba身份连接Oracle
sqlplus / as sysdba

-- 创建用户
CREATE USER file_system IDENTIFIED BY "file_system"
DEFAULT TABLESPACE USERS;

GRANT CONNECT, RESOURCE TO file_system;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE TO file_system;
```

#### 1.2 执行初始化脚本

```bash
# 进入数据库目录
cd D:\气象局上传下载系统\database

# 执行初始化脚本
sqlplus file_system/file_system @oracle_init.sql
```

**默认管理员账号**：
- 用户名：`admin`
- 密码：`admin123`

⚠️ **首次登录后请立即修改密码！**

### 2. 后端启动

#### 2.1 配置数据库连接

编辑 `backend/src/main/resources/application-dev.yml`：

```yaml
spring:
  datasource:
    druid:
      url: jdbc:oracle:thin:@localhost:1521:xe
      username: file_system
      password: file_system
```

#### 2.2 安装依赖并启动

```bash
# 进入后端目录
cd D:\气象局上传下载系统\backend

# 使用Maven构建项目
mvn clean install

# 启动应用
mvn spring-boot:run
```

或使用IDE直接运行 `FileSystemApplication.java`

后端服务地址：`http://localhost:8080/api`

### 3. 前端启动

#### 3.1 安装依赖

```bash
# 进入前端目录
cd D:\气象局上传下载系统\frontend

# 使用npm安装依赖
npm install
```

#### 3.2 启动开发服务器

```bash
npm run dev
```

前端访问地址：`http://localhost:3000`

## 开发说明

### 后端开发规范

- Controller层：处理HTTP请求，参数校验
- Service层：业务逻辑处理
- Mapper层：数据库操作
- 统一异常处理：`GlobalExceptionHandler`
- 统一响应格式：`Result<T>`

### 前端开发规范

- API调用统一放在 `src/api/` 目录
- 使用Pinia进行状态管理
- 路由配置在 `src/router/index.js`
- 公共组件放在 `src/components/` 目录

## 接口说明

### 认证相关
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/logout` - 用户退出
- `GET /api/auth/current` - 获取当前用户信息

### 文件管理
- `POST /api/files` - 上传文件
- `GET /api/files` - 获取文件列表
- `GET /api/files/:id/download` - 下载文件
- `DELETE /api/files/:id` - 删除文件（管理员）

### 用户管理（管理员）
- `GET /api/users` - 获取用户列表
- `POST /api/users` - 添加用户
- `DELETE /api/users/:id` - 删除用户
- `PUT /api/users/:id/password` - 重置密码

### 个人中心
- `GET /api/user/info` - 获取个人信息
- `PUT /api/user/password` - 修改密码

### 操作日志（管理员）
- `GET /api/logs` - 获取操作日志

详细接口文档请查看 [政府内部文件管理系统-开发文档.md](../政府内部文件管理系统-开发文档.md)

## 部署说明

### 后端打包部署

```bash
cd backend

# 打包
mvn clean package -DskipTests

# 运行jar包
java -jar target/file-system.jar --spring.profiles.active=prod
```

### 前端打包部署

```bash
cd frontend

# 打包
npm run build

# 将dist目录部署到Nginx
```

## 注意事项

1. **文件存储**：文件内容以BLOB形式存储在Oracle数据库中，无需文件系统操作权限
2. **Session管理**：默认Session超时时间为30分钟
3. **文件大小限制**：单个文件最大100MB
4. **密码安全**：密码使用BCrypt加密存储

## 常见问题

### Q: 忘记管理员密码怎么办？
A: 在Oracle数据库中执行以下SQL重置密码为 `admin123`：
```sql
UPDATE SYS_USERS
SET PASSWORD = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi'
WHERE USERNAME = 'admin';
COMMIT;
```

### Q: 如何修改文件上传大小限制？
A: 在 `application.yml` 中修改以下配置：
```yaml
spring:
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB
```

### Q: 如何备份数据？
A: 使用Oracle的expdp工具导出数据库即可，文件内容已包含在数据库中

## 联系方式

如有问题请联系开发团队。

---

**文档版本**：v1.0
**更新日期**：2026-04-28
