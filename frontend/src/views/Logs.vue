<template>
  <div class="logs-container">
    <el-form :inline="true" :model="queryForm" class="query-form">
      <el-form-item label="用户">
        <el-input v-model="queryForm.username" placeholder="请输入用户名" clearable />
      </el-form-item>
      <el-form-item label="操作类型">
        <el-select v-model="queryForm.action" placeholder="请选择" clearable>
          <el-option label="文件上传" value="upload" />
          <el-option label="文件下载" value="download" />
          <el-option label="文件删除" value="delete" />
          <el-option label="密码修改" value="password_change" />
        </el-select>
      </el-form-item>
      <el-form-item label="日期范围">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="logList" border stripe v-loading="loading">
      <el-table-column type="index" label="序号" width="60" />
      <el-table-column prop="username" label="用户" width="120" />
      <el-table-column prop="action" label="操作类型" width="120">
        <template #default="{ row }">
          {{ getActionText(row.action) }}
        </template>
      </el-table-column>
      <el-table-column prop="fileName" label="文件名" min-width="200" />
      <el-table-column prop="ipAddress" label="IP地址" width="140" />
      <el-table-column prop="createdAt" label="操作时间" width="180" />
    </el-table>

    <el-pagination
      v-model:current-page="pagination.page"
      v-model:page-size="pagination.pageSize"
      :total="pagination.total"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="fetchLogList"
      @current-change="fetchLogList"
      style="margin-top: 20px; justify-content: flex-end"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getLogList } from '@/api/log'
import { LogActionMap } from '@/utils/constant'

const loading = ref(false)
const logList = ref([])
const dateRange = ref([])

const queryForm = reactive({
  username: '',
  action: ''
})

const pagination = reactive({
  page: 1,
  pageSize: 20,
  total: 0
})

const getActionText = (action) => {
  return LogActionMap[action] || action
}

const fetchLogList = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      pageSize: pagination.pageSize
    }

    if (queryForm.username) {
      params.username = queryForm.username
    }
    if (queryForm.action) {
      params.action = queryForm.action
    }
    if (dateRange.value && dateRange.value.length === 2) {
      params.startDate = dateRange.value[0]
      params.endDate = dateRange.value[1]
    }

    const data = await getLogList(params)
    logList.value = data.list
    pagination.total = data.total
  } catch (error) {
    console.error('获取日志列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleQuery = () => {
  pagination.page = 1
  fetchLogList()
}

const handleReset = () => {
  queryForm.username = ''
  queryForm.action = ''
  dateRange.value = []
  pagination.page = 1
  fetchLogList()
}

onMounted(() => {
  fetchLogList()
})
</script>

<style scoped>
.logs-container {
  background: white;
  padding: 20px;
  border-radius: 4px;
}

.query-form {
  margin-bottom: 20px;
}
</style>
