# Mock 模式代码备份

## 📁 备份说明

此目录包含了前端Mock模式的完整代码备份，用于测试前端UI效果。

---

## 📂 备份文件列表

### Mock数据
- `mock/` - Mock数据目录（包含index.js）

### Mock版本的API文件
- `auth.mock.js` - 认证API（Mock版本）
- `file.mock.js` - 文件API（Mock版本）
- `user.mock.js` - 用户API（Mock版本）
- `log.mock.js` - 日志API（Mock版本）
- `request.mock.js` - Axios请求封装（Mock版本）

### Mock版本的页面组件
- `Login.mock.vue` - 登录页（Mock版本，包含提示信息）
- `Files.mock.vue` - 文件管理页（Mock版本，下载提示）

---

## 🔄 如何恢复Mock模式

如需恢复Mock模式进行前端测试，执行以下步骤：

### 1. 恢复API文件
```bash
cd D:\气象局上传下载系统\frontend\mock-backup
cp auth.mock.js ../src/api/auth.js
cp file.mock.js ../src/api/file.js
cp user.mock.js ../src/api/user.js
cp log.mock.js ../src/api/log.js
cp request.mock.js ../src/utils/request.js
```

### 2. 恢复页面组件
```bash
cp Login.mock.vue ../src/views/Login.vue
cp Files.mock.vue ../src/views/Files.vue
```

### 3. 确保mock目录存在
```bash
cp -r mock ../src/
```

### 4. 修改mock/index.js
确保 `ENABLE_MOCK` 为 `true`：
```javascript
export const ENABLE_MOCK = true
```

---

## 🚫 当前状态

前端已配置为连接**真实后端API**，Mock模式已关闭。

---

## 📝 备份时间

2026-04-29
