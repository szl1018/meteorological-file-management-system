import request from '@/utils/request'
import { ENABLE_MOCK, mockLogin, delay } from '@/mock'

/**
 * 用户登录
 */
export async function login(data) {
  if (ENABLE_MOCK) {
    return await mockLogin(data.username, data.password)
  }
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

/**
 * 用户退出
 */
export async function logout() {
  if (ENABLE_MOCK) {
    await delay(300)
    return
  }
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

/**
 * 获取当前用户信息
 */
export async function getCurrentUser() {
  if (ENABLE_MOCK) {
    await delay(300)
    // 从localStorage获取
    const userStr = localStorage.getItem('user')
    if (userStr) {
      const userState = JSON.parse(userStr)
      return userState.userInfo
    }
    throw new Error('未登录')
  }
  return request({
    url: '/auth/current',
    method: 'get'
  })
}
