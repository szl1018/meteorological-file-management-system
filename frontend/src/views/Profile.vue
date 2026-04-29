<template>
  <div class="profile-container">
    <el-card header="个人信息">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="用户名">{{ userInfo?.username }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ userInfo?.name }}</el-descriptions-item>
        <el-descriptions-item label="角色">{{ UserRoleMap[userInfo?.role] || '-' }}</el-descriptions-item>
        <el-descriptions-item label="注册时间">{{ userInfo?.createdAt }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card header="修改密码" style="margin-top: 20px">
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="100px" style="max-width: 500px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleChangePassword" :loading="loading">确认修改</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/store/user'
import { getUserInfo, changePassword } from '@/api/user'
import { UserRoleMap } from '@/utils/constant'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()

const userInfo = ref(null)
const loading = ref(false)
const passwordFormRef = ref(null)

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const fetchUserInfo = async () => {
  try {
    const data = await getUserInfo()
    userInfo.value = data
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}

const handleChangePassword = async () => {
  await passwordFormRef.value.validate()
  loading.value = true

  try {
    await changePassword({
      oldPassword: passwordForm.oldPassword,
      newPassword: passwordForm.newPassword
    })
    ElMessage.success('密码修改成功，请重新登录')
    await userStore.logout()
    window.location.href = '/login'
  } catch (error) {
    console.error('修改密码失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchUserInfo()
})
</script>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/store/user'
import { getUserInfo, changePassword } from '@/api/user'
import { UserRoleMap } from '@/utils/constant'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()

const userInfo = ref(null)
const loading = ref(false)
const passwordFormRef = ref(null)

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const fetchUserInfo = async () => {
  try {
    const data = await getUserInfo()
    userInfo.value = data
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}

const handleChangePassword = async () => {
  await passwordFormRef.value.validate()
  loading.value = true

  try {
    await changePassword({
      oldPassword: passwordForm.oldPassword,
      newPassword: passwordForm.newPassword
    })
    ElMessage.success('密码修改成功，请重新登录')
    await userStore.logout()
    window.location.href = '/login'
  } catch (error) {
    console.error('修改密码失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchUserInfo()
})
</script>

<style scoped>
.profile-container {
  max-width: 800px;
}
</style>
