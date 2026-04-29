<template>
  <div class="users-container">
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>
        添加用户
      </el-button>
    </div>

    <el-table :data="userList" border stripe v-loading="loading">
      <el-table-column type="index" label="序号" width="60" />
      <el-table-column prop="username" label="用户名" width="150" />
      <el-table-column prop="name" label="姓名" width="150" />
      <el-table-column prop="role" label="角色" width="120">
        <template #default="{ row }">
          {{ UserRoleMap[row.role] || '-' }}
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="180" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleResetPassword(row)">重置密码</el-button>
          <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-model:current-page="pagination.page"
      v-model:page-size="pagination.pageSize"
      :total="pagination.total"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="fetchUserList"
      @current-change="fetchUserList"
      style="margin-top: 20px; justify-content: flex-end"
    />

    <!-- 添加用户对话框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑用户' : '添加用户'" width="500px">
      <el-form :model="userForm" :rules="rules" ref="userFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!isEdit">
          <el-input v-model="userForm.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="userForm.name" />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="userForm.role" style="width: 100%">
            <el-option label="普通用户" :value="UserRole.USER" />
            <el-option label="管理员" :value="UserRole.ADMIN" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmSave" :loading="saving">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getUserList, addUser, deleteUser, resetUserPassword } from '@/api/user'
import { UserRoleMap, UserRole } from '@/utils/constant'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const userList = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const saving = ref(false)
const userFormRef = ref(null)

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const userForm = reactive({
  username: '',
  password: '',
  name: '',
  role: UserRole.USER
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }]
}

const fetchUserList = async () => {
  loading.value = true
  try {
    const data = await getUserList({
      page: pagination.page,
      pageSize: pagination.pageSize
    })
    userList.value = data.list
    pagination.total = data.total
  } catch (error) {
    console.error('获取用户列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(userForm, {
    username: '',
    password: '',
    name: '',
    role: UserRole.USER
  })
  dialogVisible.value = true
}

const confirmSave = async () => {
  await userFormRef.value.validate()
  saving.value = true

  try {
    await addUser(userForm)
    ElMessage.success('添加成功')
    dialogVisible.value = false
    fetchUserList()
  } catch (error) {
    console.error('添加失败:', error)
  } finally {
    saving.value = false
  }
}

const handleResetPassword = async (row) => {
  const { value } = await ElMessageBox.prompt('请输入新密码', '重置密码', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /^.{6,}$/,
    inputErrorMessage: '密码长度不能少于6位'
  })

  try {
    await resetUserPassword(row.id, { newPassword: value })
    ElMessage.success('密码重置成功')
  } catch (error) {
    console.error('重置密码失败:', error)
  }
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确定要删除用户 "${row.name}" 吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })

  try {
    await deleteUser(row.id)
    ElMessage.success('删除成功')
    fetchUserList()
  } catch (error) {
    console.error('删除失败:', error)
  }
}

onMounted(() => {
  fetchUserList()
})
</script>

<style scoped>
.users-container {
  background: white;
  padding: 20px;
  border-radius: 4px;
}

.toolbar {
  margin-bottom: 20px;
}
</style>
