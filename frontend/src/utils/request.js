import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

// 创建axios实例
const request = axios.create({
  baseURL: '/api',
  timeout: 30000,
  withCredentials: true
})

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data

    // 如果响应码不是200，则认为是错误
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')

      // 401: 未登录
      if (res.code === 401) {
        router.push('/login')
      }

      return Promise.reject(new Error(res.message || '请求失败'))
    }

    return res.data
  },
  error => {
    console.error('请求错误:', error)
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)

export default request
