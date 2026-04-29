<template>
  <div class="files-container">
    <div class="toolbar">
      <el-button type="primary" @click="handleUpload">
        <el-icon><Upload /></el-icon>
        上传文件
      </el-button>
    </div>

    <el-table :data="fileList" border stripe v-loading="loading">
      <el-table-column type="index" label="序号" width="60" />
      <el-table-column prop="fileName" label="文件名" min-width="200" />
      <el-table-column prop="fileSize" label="文件大小" width="120">
        <template #default="{ row }">
          {{ formatFileSize(row.fileSize) }}
        </template>
      </el-table-column>
      <el-table-column prop="uploadedAt" label="上传时间" width="180" />
      <el-table-column label="操作" width="120" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleDownload(row)">
            <el-icon><Download /></el-icon>
            下载
          </el-button>
          <el-button v-if="userStore.isAdmin" type="danger" link @click="handleDelete(row)">
            <el-icon><Delete /></el-icon>
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-model:current-page="pagination.page"
      v-model:page-size="pagination.pageSize"
      :total="pagination.total"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="fetchFileList"
      @current-change="fetchFileList"
      style="margin-top: 20px; justify-content: flex-end"
    />

    <!-- 上传对话框 -->
    <el-dialog v-model="uploadDialogVisible" title="上传文件" width="500px">
      <el-upload
        ref="uploadRef"
        :auto-upload="false"
        :limit="1"
        :on-change="handleFileChange"
        :on-exceed="handleExceed"
        drag
      >
        <el-icon class="el-icon--upload"><UploadFilled /></el-icon>
        <div class="el-upload__text">
          将文件拖到此处，或<em>点击上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            支持 PDF、Word、Excel、PPT、图片等格式，文件大小不超过100MB
          </div>
        </template>
      </el-upload>
      <template #footer>
        <el-button @click="uploadDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="uploading" @click="confirmUpload">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/store/user'
import { getFileList, uploadFile, downloadFile, deleteFile } from '@/api/file'
import { formatFileSize } from '@/utils'
import { ElMessage, ElMessageBox } from 'element-plus'

const userStore = useUserStore()

const loading = ref(false)
const fileList = ref([])
const uploadDialogVisible = ref(false)
const uploading = ref(false)
const selectedFile = ref(null)
const uploadRef = ref(null)

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// 获取文件列表
const fetchFileList = async () => {
  loading.value = true
  try {
    const data = await getFileList({
      page: pagination.page,
      pageSize: pagination.pageSize
    })
    fileList.value = data.list
    pagination.total = data.total
  } catch (error) {
    console.error('获取文件列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 上传文件
const handleUpload = () => {
  uploadDialogVisible.value = true
}

const handleFileChange = (file) => {
  selectedFile.value = file.raw
}

const handleExceed = () => {
  ElMessage.warning('只能选择一个文件')
}

const confirmUpload = async () => {
  if (!selectedFile.value) {
    ElMessage.warning('请选择要上传的文件')
    return
  }

  uploading.value = true
  try {
    const formData = new FormData()
    formData.append('file', selectedFile.value)
    await uploadFile(formData)
    ElMessage.success('上传成功')
    uploadDialogVisible.value = false
    selectedFile.value = null
    uploadRef.value?.clearFiles()
    fetchFileList()
  } catch (error) {
    console.error('上传失败:', error)
  } finally {
    uploading.value = false
  }
}

// 下载文件
const handleDownload = (row) => {
  const url = downloadFile(row.id)
  if (url === '#') {
    ElMessage.info(`Mock模式：模拟下载文件 "${row.fileName}"`)
  } else {
    const link = document.createElement('a')
    link.href = url
    link.download = row.fileName
    link.click()
  }
}

// 删除文件
const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确定要删除文件 "${row.fileName}" 吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })

  try {
    await deleteFile(row.id)
    ElMessage.success('删除成功')
    fetchFileList()
  } catch (error) {
    console.error('删除失败:', error)
  }
}

onMounted(() => {
  fetchFileList()
})
</script>

<style scoped>
.files-container {
  background: white;
  padding: 20px;
  border-radius: 4px;
}

.toolbar {
  margin-bottom: 20px;
}
</style>
