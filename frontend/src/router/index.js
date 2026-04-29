import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ElMessage } from 'element-plus'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    component: () => import('@/views/Layout.vue'),
    redirect: '/files',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'files',
        name: 'Files',
        component: () => import('@/views/Files.vue'),
        meta: { title: '文件管理', icon: 'Folder' }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/Profile.vue'),
        meta: { title: '个人中心', icon: 'User' }
      },
      {
        path: 'users',
        name: 'Users',
        component: () => import('@/views/Users.vue'),
        meta: { title: '用户管理', icon: 'UserFilled', requiresAdmin: true }
      },
      {
        path: 'logs',
        name: 'Logs',
        component: () => import('@/views/Logs.vue'),
        meta: { title: '操作日志', icon: 'Document', requiresAdmin: true }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()

  // 如果访问登录页，已登录则跳转到首页
  if (to.path === '/login') {
    if (userStore.isLogin) {
      next('/')
    } else {
      next()
    }
    return
  }

  // 需要登录的页面
  if (to.meta.requiresAuth) {
    if (!userStore.isLogin) {
      ElMessage.warning('请先登录')
      next('/login')
      return
    }

    // 需要管理员权限的页面
    if (to.meta.requiresAdmin && !userStore.isAdmin) {
      ElMessage.error('无权限访问')
      next('/')
      return
    }
  }

  next()
})

export default router
