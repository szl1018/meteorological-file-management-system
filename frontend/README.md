# 前端项目说明

## 安装依赖

```bash
npm install
```

## 开发模式运行

```bash
npm run dev
```

访问地址：http://localhost:3000

## 生产构建

```bash
npm run build
```

构建产物在 `dist` 目录

## 技术栈

- Vue 3 - 渐进式 JavaScript 框架
- Vite - 新一代前端构建工具
- Element Plus - Vue 3 组件库
- Vue Router - 路由管理
- Pinia - 状态管理
- Axios - HTTP 客户端

## 项目结构

```
src/
├── api/              # API 接口
├── assets/           # 静态资源
├── components/       # 公共组件
├── router/           # 路由配置
├── store/            # 状态管理
├── styles/           # 全局样式
├── utils/            # 工具函数
├── views/            # 页面组件
├── App.vue           # 根组件
└── main.js           # 入口文件
```

## 环境变量

- `.env.development` - 开发环境
- `.env.production` - 生产环境
