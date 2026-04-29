import request from '@/utils/request'

/**
 * 上传文件
 */
export function uploadFile(formData) {
  return request({
    url: '/files',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

/**
 * 获取文件列表
 */
export function getFileList(params) {
  return request({
    url: '/files',
    method: 'get',
    params
  })
}

/**
 * 获取文件详情
 */
export function getFileDetail(id) {
  return request({
    url: `/files/${id}`,
    method: 'get'
  })
}

/**
 * 下载文件
 */
export function downloadFile(id) {
  return `/api/files/${id}/download`
}

/**
 * 删除文件（管理员）
 */
export function deleteFile(id) {
  return request({
    url: `/files/${id}`,
    method: 'delete'
  })
}
