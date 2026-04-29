import request from '@/utils/request'

/**
 * 获取用户列表（管理员）
 */
export function getUserList(params) {
  return request({
    url: '/users',
    method: 'get',
    params
  })
}

/**
 * 添加用户（管理员）
 */
export function addUser(data) {
  return request({
    url: '/users',
    method: 'post',
    data
  })
}

/**
 * 删除用户（管理员）
 */
export function deleteUser(id) {
  return request({
    url: `/users/${id}`,
    method: 'delete'
  })
}

/**
 * 重置用户密码（管理员）
 */
export function resetUserPassword(id, data) {
  return request({
    url: `/users/${id}/password`,
    method: 'put',
    data
  })
}

/**
 * 修改自己的密码
 */
export function changePassword(data) {
  return request({
    url: '/user/password',
    method: 'put',
    data
  })
}

/**
 * 获取个人信息
 */
export function getUserInfo() {
  return request({
    url: '/user/info',
    method: 'get'
  })
}
