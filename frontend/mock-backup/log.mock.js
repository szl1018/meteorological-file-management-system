import request from '@/utils/request'
import { ENABLE_MOCK, mockLogs, mockPaginate, delay, LogActionMap } from '@/mock'

/**
 * 获取操作日志（管理员）
 */
export async function getLogList(params) {
  if (ENABLE_MOCK) {
    await delay(500)
    let filteredLogs = [...mockLogs]

    // 按用户名筛选
    if (params.username) {
      filteredLogs = filteredLogs.filter(log => log.username.includes(params.username))
    }

    // 按操作类型筛选
    if (params.action) {
      filteredLogs = filteredLogs.filter(log => log.action === params.action)
    }

    // 按日期范围筛选
    if (params.startDate && params.endDate) {
      filteredLogs = filteredLogs.filter(log => {
        const logDate = log.createdAt.split(' ')[0].replace(/-/g, '')
        const start = params.startDate.replace(/-/g, '')
        const end = params.endDate.replace(/-/g, '')
        return logDate >= start && logDate <= end
      })
    }

    const page = params.page || 1
    const pageSize = params.pageSize || 20
    return mockPaginate(filteredLogs, page, pageSize)
  }
  return request({
    url: '/logs',
    method: 'get',
    params
  })
}
