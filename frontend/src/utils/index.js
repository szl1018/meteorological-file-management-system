/**
 * 工具函数
 */

/**
 * 格式化文件大小
 * @param {number} bytes 字节数
 * @returns {string} 格式化后的文件大小
 */
export function formatFileSize(bytes) {
  if (bytes === 0) return '0 B'
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB', 'TB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return (bytes / Math.pow(k, i)).toFixed(2) + ' ' + sizes[i]
}

/**
 * 格式化日期时间
 * @param {string|Date} date 日期
 * @param {string} format 格式
 * @returns {string} 格式化后的日期
 */
export function formatDateTime(date, format = 'YYYY-MM-DD HH:mm:ss') {
  if (!date) return ''
  const d = new Date(date)
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const hours = String(d.getHours()).padStart(2, '0')
  const minutes = String(d.getMinutes()).padStart(2, '0')
  const seconds = String(d.getSeconds()).padStart(2, '0')

  return format
    .replace('YYYY', year)
    .replace('MM', month)
    .replace('DD', day)
    .replace('HH', hours)
    .replace('mm', minutes)
    .replace('ss', seconds)
}

/**
 * 下载文件
 * @param {string} url 文件URL
 * @param {string} filename 文件名
 */
export function downloadFile(url, filename) {
  const link = document.createElement('a')
  link.href = url
  link.download = filename || ''
  link.click()
}

/**
 * 复制到剪贴板
 * @param {string} text 要复制的文本
 * @returns {Promise<boolean>}
 */
export async function copyToClipboard(text) {
  try {
    await navigator.clipboard.writeText(text)
    return true
  } catch (error) {
    console.error('复制失败:', error)
    return false
  }
}

/**
 * 防抖函数
 * @param {Function} func 要防抖的函数
 * @param {number} delay 延迟时间（毫秒）
 * @returns {Function}
 */
export function debounce(func, delay = 300) {
  let timer = null
  return function (...args) {
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => {
      func.apply(this, args)
    }, delay)
  }
}

/**
 * 节流函数
 * @param {Function} func 要节流的函数
 * @param {number} delay 延迟时间（毫秒）
 * @returns {Function}
 */
export function throttle(func, delay = 300) {
  let timer = null
  return function (...args) {
    if (timer) return
    timer = setTimeout(() => {
      func.apply(this, args)
      timer = null
    }, delay)
  }
}

/**
 * 深拷贝
 * @param {any} obj 要拷贝的对象
 * @returns {any}
 */
export function deepClone(obj) {
  if (obj === null || typeof obj !== 'object') return obj
  if (obj instanceof Date) return new Date(obj)
  if (obj instanceof Array) return obj.map(item => deepClone(item))

  const clonedObj = {}
  for (const key in obj) {
    if (obj.hasOwnProperty(key)) {
      clonedObj[key] = deepClone(obj[key])
    }
  }
  return clonedObj
}

/**
 * 获取文件扩展名
 * @param {string} filename 文件名
 * @returns {string}
 */
export function getFileExtension(filename) {
  const lastDotIndex = filename.lastIndexOf('.')
  return lastDotIndex > -1 ? filename.slice(lastDotIndex + 1).toLowerCase() : ''
}

/**
 * 判断是否为图片文件
 * @param {string} filename 文件名
 * @returns {boolean}
 */
export function isImageFile(filename) {
  const ext = getFileExtension(filename)
  return ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp'].includes(ext)
}

/**
 * 判断是否为PDF文件
 * @param {string} filename 文件名
 * @returns {boolean}
 */
export function isPdfFile(filename) {
  return getFileExtension(filename) === 'pdf'
}

/**
 * 判断是否为Word文件
 * @param {string} filename 文件名
 * @returns {boolean}
 */
export function isWordFile(filename) {
  const ext = getFileExtension(filename)
  return ['doc', 'docx'].includes(ext)
}

/**
 * 判断是否为Excel文件
 * @param {string} filename 文件名
 * @returns {boolean}
 */
export function isExcelFile(filename) {
  const ext = getFileExtension(filename)
  return ['xls', 'xlsx'].includes(ext)
}

/**
 * 获取文件图标
 * @param {string} filename 文件名
 * @returns {string}
 */
export function getFileIcon(filename) {
  const ext = getFileExtension(filename)

  const iconMap = {
    // 图片
    jpg: 'Picture',
    jpeg: 'Picture',
    png: 'Picture',
    gif: 'Picture',
    bmp: 'Picture',
    webp: 'Picture',
    // PDF
    pdf: 'Document',
    // Word
    doc: 'Document',
    docx: 'Document',
    // Excel
    xls: 'Grid',
    xlsx: 'Grid',
    // PowerPoint
    ppt: 'Document',
    pptx: 'Document',
    // 压缩文件
    zip: 'Folder',
    rar: 'Folder',
    '7z': 'Folder',
    // 文本
    txt: 'Document',
    // 视频
    mp4: 'VideoPlay',
    avi: 'VideoPlay',
    mov: 'VideoPlay',
    // 音频
    mp3: 'Headset',
    wav: 'Headset'
  }

  return iconMap[ext] || 'Document'
}
