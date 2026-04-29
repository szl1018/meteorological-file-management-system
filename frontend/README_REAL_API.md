# 前端已切换到真实后端API模式

## ✅ 完成状态

前端代码已成功从Mock模式切换到真实后端API连接模式。

---

## 📂 Mock代码备份位置

所有Mock模式的代码已备份到：
```
D:\气象局上传下载系统\frontend\mock-backup\
```

### 备份文件列表
- ✅ `mock/` - Mock数据目录
- ✅ `auth.mock.js` - 认证API Mock版本
- ✅ `file.mock.js` - 文件API Mock版本
- ✅ `user.mock.js` - 用户API Mock版本
- ✅ `log.mock.js` - 日志API Mock版本
- ✅ `request.mock.js` - Axios封装 Mock版本
- ✅ `Login.mock.vue` - 登录页 Mock版本
- ✅ `Files.mock.vue` - 文件页 Mock版本
- ✅ `README.md` - 备份说明文档

---

## 🔄 已恢复的文件

以下文件已恢复为连接真实后端API的版本：

### API文件
- ✅ `src/api/auth.js` - 连接真实认证接口
- ✅ `src/api/file.js` - 连接真实文件接口
- ✅ `src/api/user.js` - 连接真实用户接口
- ✅ `src/api/log.js` - 连接真实日志接口

### 工具文件
- ✅ `src/utils/request.js` - Axios请求封装（移除Mock拦截器）

### 页面组件
- ✅ `src/views/Login.vue` - 移除Mock提示信息
- ✅ `src/views/Files.vue` - 移除Mock下载提示

### 已删除
- ✅ `src/mock/` - Mock数据目录已删除

---

## 🚀 当前配置

| 配置项 | 值 |
|--------|-----|
| **API模式** | 真实后端API |
| **后端地址** | http://localhost:8080/api |
| **认证方式** | HttpSession (Cookie) |
| **跨域配置** | 已配置（Vite代理） |
| **Mock模式** | ❌ 已禁用 |

---

## 📋 下一步操作

### 1. 启动后端服务
后端代码开发完成后，启动Spring Boot应用：
```bash
cd D:\气象局上传下载系统\backend
mvn spring-boot:run
```

### 2. 启动前端服务
```bash
cd D:\气象局上传下载系统\frontend
npm run dev
```

### 3. 访问应用
打开浏览器访问：`http://localhost:3000`

### 4. 使用默认账号登录
- 用户名：`admin`
- 密码：`admin123`

---

## 🔧 如何恢复Mock模式（如需前端UI测试）

如果需要在没有后端的情况下测试前端UI：

### 方式一：使用备份文件
```bash
cd D:\气象局上传下载系统\frontend\mock-backup

# 恢复API文件
cp auth.mock.js ../src/api/auth.js
cp file.mock.js ../src/api/file.js
cp user.mock.js ../src/api/user.js
cp log.mock.js ../src/api/log.js
cp request.mock.js ../src/utils/request.js

# 恢复页面组件
cp Login.mock.vue ../src/views/Login.vue
cp Files.mock.vue ../src/views/Files.vue

# 恢复mock数据
cp -r mock ../src/
```

### 方式二：查看备份文档
详细说明请查看：
```
D:\气象局上传下载系统\frontend\mock-backup\README.md
```

---

## 📝 API接口说明

### 基础URL
- 开发环境：`http://localhost:8080/api`
- 前端代理：`/api` → `http://localhost:8080/api`

### 接口列表
| 模块 | 接口 | 方法 |
|------|------|------|
| 认证 | `/auth/login` | POST |
| 认证 | `/auth/logout` | POST |
| 认证 | `/auth/current` | GET |
| 文件 | `/files` | GET/POST |
| 文件 | `/files/:id` | GET/DELETE |
| 文件 | `/files/:id/download` | GET |
| 用户 | `/users` | GET/POST |
| 用户 | `/users/:id` | DELETE |
| 用户 | `/users/:id/password` | PUT |
| 用户 | `/user/info` | GET |
| 用户 | `/user/password` | PUT |
| 日志 | `/logs` | GET |

---

## ✨ 前端特性

- ✅ **Vue 3** - 组合式API
- ✅ **Element Plus** - UI组件库
- ✅ **Pinia** - 状态管理（支持持久化）
- ✅ **Vue Router** - 路由管理（支持路由守卫）
- ✅ **Axios** - HTTP客户端
- ✅ **自动登录** - Session/Cookie认证
- ✅ **权限控制** - 管理员/普通用户权限分离
- ✅ **统一响应处理** - 错误拦截和提示
- ✅ **文件上传** - 支持拖拽上传
- ✅ **文件下载** - 流式下载

---

## ⚠️ 重要提示

1. **后端必须先启动**
   - 前端需要连接后端API
   - 确保Oracle数据库已初始化
   - 确保后端服务运行在8080端口

2. **跨域问题**
   - 开发环境使用Vite代理解决
   - 生产环境需要配置Nginx或后端CORS

3. **Session管理**
   - 登录后服务器创建Session
   - 通过JSESSIONID Cookie维持会话
   - Session超时时间：30分钟

---

**更新时间**: 2026-04-29
**状态**: ✅ 已完成Mock模式切换
