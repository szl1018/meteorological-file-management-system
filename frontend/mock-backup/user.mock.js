import request from '@/utils/request'
import { ENABLE_MOCK, mockUsers, mockPaginate, delay } from '@/mock'

/**
 * 获取用户列表（管理员）
 */
export async function getUserList(params) {
  if (ENABLE_MOCK) {
    await delay(500)
    const page = params.page || 1
    const pageSize = params.pageSize || 10
    return mockPaginate(mockUsers, page, pageSize)
  }
  return request({
    url: '/users',
    method: 'get',
    params
  })
}

/**
 * 添加用户（管理员）
 */
export async function addUser(data) {
  if (ENABLE_MOCK) {
    await delay(800)
    const newUser = {
      id: mockUsers.length + 1,
      username: data.username,
      name: data.name,
      role: data.role,
      createdAt: new Date().toLocaleString('zh-CN', { hour12: false }).replace(/\//g, '-')
    }
    mockUsers.push(newUser)
    return newUser
  }
  return request({
    url: '/users',
    method: 'post',
    data
  })
}

/**
 * 删除用户（管理员）
 */
export async function deleteUser(id) {
  if (ENABLE_MOCK) {
    await delay(500)
    const index = mockUsers.findIndex(u => u.id === id)
    if (index > -1) {
      mockUsers.splice(index, 1)
    }
    return
  }
  return request({
    url: `/users/${id}`,
    method: 'delete'
  })
}

/**
 * 重置用户密码（管理员）
 */
export async function resetUserPassword(id, data) {
  if (ENABLE_MOCK) {
    await delay(500)
    return
  }
  return request({
    url: `/users/${id}/password`,
    method: 'put',
    data
  })
}

/**
 * 修改自己的密码
 */
export async function changePassword(data) {
  if (ENABLE_MOCK) {
    await delay(500)
    return
  }
  return request({
    url: '/user/password',
    method: 'put',
    data
  })
}

/**
 * 获取个人信息
 */
export async function getUserInfo() {
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
    url: '/user/info',
    method: 'get'
  })
}
