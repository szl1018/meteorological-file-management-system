import request from '@/utils/request'
import { ENABLE_MOCK, mockFiles, mockPaginate, delay } from '@/mock'

/**
 * 上传文件
 */
export async function uploadFile(formData) {
  if (ENABLE_MOCK) {
    await delay(1000)
    // Mock上传成功
    return {
      id: mockFiles.length + 1,
      fileName: formData.get('file').name,
      fileSize: formData.get('file').size,
      uploadedAt: new Date().toLocaleString('zh-CN', { hour12: false }).replace(/\//g, '-')
    }
  }
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
export async function getFileList(params) {
  if (ENABLE_MOCK) {
    await delay(500)
    const page = params.page || 1
    const pageSize = params.pageSize || 10
    return mockPaginate(mockFiles, page, pageSize)
  }
  return request({
    url: '/files',
    method: 'get',
    params
  })
}

/**
 * 获取文件详情
 */
export async function getFileDetail(id) {
  if (ENABLE_MOCK) {
    await delay(300)
    const file = mockFiles.find(f => f.id === id)
    if (!file) {
      throw new Error('文件不存在')
    }
    return file
  }
  return request({
    url: `/files/${id}`,
    method: 'get'
  })
}

/**
 * 下载文件
 */
export function downloadFile(id) {
  if (ENABLE_MOCK) {
    // Mock下载：返回一个假链接
    return '#'
  }
  return `/api/files/${id}/download`
}

/**
 * 删除文件（管理员）
 */
export async function deleteFile(id) {
  if (ENABLE_MOCK) {
    await delay(500)
    const index = mockFiles.findIndex(f => f.id === id)
    if (index > -1) {
      mockFiles.splice(index, 1)
    }
    return
  }
  return request({
    url: `/files/${id}`,
    method: 'delete'
  })
}
