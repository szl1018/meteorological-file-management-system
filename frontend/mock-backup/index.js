/**
 * Mock 数据配置
 * 用于在没有后端API的情况下测试前端UI
 */

// 是否启用Mock模式（生产环境自动关闭）
export const ENABLE_MOCK = import.meta.env.MODE === 'development'

/**
 * 操作日志类型映射
 */
export const LogActionMap = {
  upload: '文件上传',
  download: '文件下载',
  delete: '文件删除',
  password_change: '密码修改'
}

/**
 * Mock用户数据
 */
export const mockUsers = [
  {
    id: 1,
    username: 'admin',
    name: '系统管理员',
    role: 'ADMIN',
    createdAt: '2024-01-01 10:00:00'
  },
  {
    id: 2,
    username: 'zhangsan',
    name: '张三',
    role: 'USER',
    createdAt: '2024-01-02 09:00:00'
  },
  {
    id: 3,
    username: 'lisi',
    name: '李四',
    role: 'USER',
    createdAt: '2024-01-03 14:00:00'
  },
  {
    id: 4,
    username: 'wangwu',
    name: '王五',
    role: 'USER',
    createdAt: '2024-01-04 16:00:00'
  },
  {
    id: 5,
    username: 'zhaoliu',
    name: '赵六',
    role: 'USER',
    createdAt: '2024-01-05 11:00:00'
  }
]

/**
 * Mock文件数据
 */
export const mockFiles = [
  {
    id: 1,
    fileName: '2024年度工作报告.pdf',
    fileSize: 2048576,
    mimeType: 'application/pdf',
    uploadedBy: 2,
    uploadedByName: '张三',
    createdAt: '2024-01-10 09:30:00'
  },
  {
    id: 2,
    fileName: '会议纪要.docx',
    fileSize: 524288,
    mimeType: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
    uploadedBy: 2,
    uploadedByName: '张三',
    createdAt: '2024-01-11 14:20:00'
  },
  {
    id: 3,
    fileName: '数据统计表.xlsx',
    fileSize: 1048576,
    mimeType: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    uploadedBy: 3,
    uploadedByName: '李四',
    createdAt: '2024-01-12 10:15:00'
  },
  {
    id: 4,
    fileName: '项目方案.pptx',
    fileSize: 3145728,
    mimeType: 'application/vnd.openxmlformats-officedocument.presentationml.presentation',
    uploadedBy: 3,
    uploadedByName: '李四',
    createdAt: '2024-01-13 16:45:00'
  },
  {
    id: 5,
    fileName: '系统架构图.png',
    fileSize: 524288,
    mimeType: 'image/png',
    uploadedBy: 4,
    uploadedByName: '王五',
    createdAt: '2024-01-14 11:20:00'
  },
  {
    id: 6,
    fileName: '用户手册.pdf',
    fileSize: 1572864,
    mimeType: 'application/pdf',
    uploadedBy: 4,
    uploadedByName: '王五',
    createdAt: '2024-01-15 09:00:00'
  },
  {
    id: 7,
    fileName: '需求文档.docx',
    fileSize: 786432,
    mimeType: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
    uploadedBy: 5,
    uploadedByName: '赵六',
    createdAt: '2024-01-16 15:30:00'
  },
  {
    id: 8,
    fileName: '测试报告.pdf',
    fileSize: 2621440,
    mimeType: 'application/pdf',
    uploadedBy: 5,
    uploadedByName: '赵六',
    createdAt: '2024-01-17 10:45:00'
  },
  {
    id: 9,
    fileName: '界面设计.jpg',
    fileSize: 1048576,
    mimeType: 'image/jpeg',
    uploadedBy: 2,
    uploadedByName: '张三',
    createdAt: '2024-01-18 13:20:00'
  },
  {
    id: 10,
    fileName: '数据备份.xlsx',
    fileSize: 2097152,
    mimeType: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    uploadedBy: 3,
    uploadedByName: '李四',
    createdAt: '2024-01-19 09:50:00'
  },
  {
    id: 11,
    fileName: '培训材料.pptx',
    fileSize: 4194304,
    mimeType: 'application/vnd.openxmlformats-officedocument.presentationml.presentation',
    uploadedBy: 4,
    uploadedByName: '王五',
    createdAt: '2024-01-20 14:10:00'
  },
  {
    id: 12,
    fileName: '操作指南.txt',
    fileSize: 16384,
    mimeType: 'text/plain',
    uploadedBy: 5,
    uploadedByName: '赵六',
    createdAt: '2024-01-21 11:30:00'
  },
  {
    id: 13,
    fileName: '年度总结.pdf',
    fileSize: 1835008,
    mimeType: 'application/pdf',
    uploadedBy: 2,
    uploadedByName: '张三',
    createdAt: '2024-01-22 16:00:00'
  },
  {
    id: 14,
    fileName: '流程图.png',
    fileSize: 786432,
    mimeType: 'image/png',
    uploadedBy: 3,
    uploadedByName: '李四',
    createdAt: '2024-01-23 10:20:00'
  },
  {
    id: 15,
    fileName: '预算表.xlsx',
    fileSize: 921600,
    mimeType: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    uploadedBy: 4,
    uploadedByName: '王五',
    createdAt: '2024-01-24 15:40:00'
  }
]

/**
 * Mock操作日志数据
 */
export const mockLogs = [
  {
    id: 1,
    userId: 2,
    username: '张三',
    action: 'upload',
    fileName: '2024年度工作报告.pdf',
    ipAddress: '192.168.1.100',
    createdAt: '2024-01-10 09:30:00'
  },
  {
    id: 2,
    userId: 2,
    username: '张三',
    action: 'upload',
    fileName: '会议纪要.docx',
    ipAddress: '192.168.1.100',
    createdAt: '2024-01-11 14:20:00'
  },
  {
    id: 3,
    userId: 3,
    username: '李四',
    action: 'upload',
    fileName: '数据统计表.xlsx',
    ipAddress: '192.168.1.101',
    createdAt: '2024-01-12 10:15:00'
  },
  {
    id: 4,
    userId: 3,
    username: '李四',
    action: 'download',
    fileName: '2024年度工作报告.pdf',
    ipAddress: '192.168.1.101',
    createdAt: '2024-01-12 10:25:00'
  },
  {
    id: 5,
    userId: 2,
    username: '张三',
    action: 'download',
    fileName: '会议纪要.docx',
    ipAddress: '192.168.1.100',
    createdAt: '2024-01-13 09:15:00'
  },
  {
    id: 6,
    userId: 1,
    username: '系统管理员',
    action: 'delete',
    fileName: '旧文件.pdf',
    ipAddress: '192.168.1.1',
    createdAt: '2024-01-13 17:00:00'
  },
  {
    id: 7,
    userId: 4,
    username: '王五',
    action: 'upload',
    fileName: '系统架构图.png',
    ipAddress: '192.168.1.102',
    createdAt: '2024-01-14 11:20:00'
  },
  {
    id: 8,
    userId: 5,
    username: '赵六',
    action: 'password_change',
    fileName: null,
    ipAddress: '192.168.1.103',
    createdAt: '2024-01-14 14:30:00'
  },
  {
    id: 9,
    userId: 4,
    username: '王五',
    action: 'upload',
    fileName: '用户手册.pdf',
    ipAddress: '192.168.1.102',
    createdAt: '2024-01-15 09:00:00'
  },
  {
    id: 10,
    userId: 5,
    username: '赵六',
    action: 'upload',
    fileName: '需求文档.docx',
    ipAddress: '192.168.1.103',
    createdAt: '2024-01-16 15:30:00'
  },
  {
    id: 11,
    userId: 2,
    username: '张三',
    action: 'download',
    fileName: '数据统计表.xlsx',
    ipAddress: '192.168.1.100',
    createdAt: '2024-01-17 10:10:00'
  },
  {
    id: 12,
    userId: 5,
    username: '赵六',
    action: 'upload',
    fileName: '测试报告.pdf',
    ipAddress: '192.168.1.103',
    createdAt: '2024-01-17 10:45:00'
  },
  {
    id: 13,
    userId: 1,
    username: '系统管理员',
    action: 'delete',
    fileName: '过期文件.docx',
    ipAddress: '192.168.1.1',
    createdAt: '2024-01-18 12:00:00'
  },
  {
    id: 14,
    userId: 3,
    username: '李四',
    action: 'download',
    fileName: '项目方案.pptx',
    ipAddress: '192.168.1.101',
    createdAt: '2024-01-18 14:20:00'
  },
  {
    id: 15,
    userId: 2,
    username: '张三',
    action: 'upload',
    fileName: '界面设计.jpg',
    ipAddress: '192.168.1.100',
    createdAt: '2024-01-18 13:20:00'
  }
]

/**
 * 模拟延迟
 */
export const delay = (ms = 500) => new Promise(resolve => setTimeout(resolve, ms))

/**
 * Mock登录
 */
export async function mockLogin(username, password) {
  await delay(800)

  const user = mockUsers.find(u => u.username === username)

  if (!user) {
    throw new Error('用户不存在')
  }

  // 任何密码都可以登录（mock模式）
  return {
    userInfo: user
  }
}

/**
 * Mock分页
 */
export function mockPaginate(data, page = 1, pageSize = 10) {
  const start = (page - 1) * pageSize
  const end = start + pageSize
  const list = data.slice(start, end)

  return {
    total: data.length,
    list,
    page,
    pageSize
  }
}
