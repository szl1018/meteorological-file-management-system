import { defineStore } from 'pinia'
import { login as loginApi, logout as logoutApi, getCurrentUser } from '@/api/auth'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: null
  }),

  getters: {
    isLogin: (state) => !!state.userInfo,
    isAdmin: (state) => state.userInfo?.role === 'ADMIN'
  },

  actions: {
    /**
     * 登录
     */
    async login(loginForm) {
      const data = await loginApi(loginForm)
      this.userInfo = data.userInfo
      return data
    },

    /**
     * 退出登录
     */
    async logout() {
      try {
        await logoutApi()
      } finally {
        this.userInfo = null
      }
    },

    /**
     * 获取当前用户信息
     */
    async fetchUserInfo() {
      try {
        const data = await getCurrentUser()
        this.userInfo = data
        return data
      } catch (error) {
        this.userInfo = null
        throw error
      }
    }
  },

  persist: true
})
