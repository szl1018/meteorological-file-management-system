/**
 * 系统常量配置
 */

/**
 * 用户角色
 */
export const UserRole = {
  ADMIN: 'ADMIN',
  USER: 'USER'
}

/**
 * 用户角色映射
 */
export const UserRoleMap = {
  [UserRole.ADMIN]: '管理员',
  [UserRole.USER]: '普通用户'
}

/**
 * 操作日志类型
 */
export const LogAction = {
  UPLOAD: 'upload',
  DOWNLOAD: 'download',
  DELETE: 'delete',
  PASSWORD_CHANGE: 'password_change'
}

/**
 * 操作日志类型映射
 */
export const LogActionMap = {
  [LogAction.UPLOAD]: '文件上传',
  [LogAction.DOWNLOAD]: '文件下载',
  [LogAction.DELETE]: '文件删除',
  [LogAction.PASSWORD_CHANGE]: '密码修改'
}

/**
 * 允许的文件类型
 */
export const AllowedFileTypes = [
  'application/pdf',                                    // PDF
  'application/msword',                                 // Word (.doc)
  'application/vnd.openxmlformats-officedocument.wordprocessingml.document',  // Word (.docx)
  'application/vnd.ms-excel',                           // Excel (.xls)
  'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',        // Excel (.xlsx)
  'application/vnd.ms-powerpoint',                      // PowerPoint (.ppt)
  'application/vnd.openxmlformats-officedocument.presentationml.presentation', // PowerPoint (.pptx)
  'image/jpeg',                                         // JPG
  'image/png',                                          // PNG
  'image/gif',                                          // GIF
  'image/bmp',                                          // BMP
  'text/plain'                                          // TXT
]

/**
 * 文件大小限制（字节）
 */
export const MaxFileSize = 100 * 1024 * 1024  // 100MB

/**
 * 分页大小选项
 */
export const PageSizes = [10, 20, 50, 100]

/**
 * 默认分页大小
 */
export const DefaultPageSize = 10

/**
 * Session 超时时间（毫秒）
 */
export const SessionTimeout = 30 * 60 * 1000  // 30分钟

/**
 * 日期时间格式
 */
export const DateTimeFormat = 'YYYY-MM-DD HH:mm:ss'

/**
 * 日期格式
 */
export const DateFormat = 'YYYY-MM-DD'

/**
 * 时间格式
 */
export const TimeFormat = 'HH:mm:ss'
