<template>
  <div class="login-container">
    <div class="login-box">
      <h2>政府内部文件管理系统</h2>

      <!-- Mock模式提示 -->
      <el-alert
        v-if="isMockMode"
        title="Mock测试模式"
        type="info"
        :closable="false"
        style="margin-bottom: 20px"
      >
        <template #default>
          <div style="font-size: 12px; line-height: 1.8">
            <div>当前为Mock数据模式，可以使用以下测试账号：</div>
            <div style="margin-top: 5px; color: #409eff;">
              管理员: admin / 任意密码
            </div>
            <div style="color: #67c23a;">
              普通用户: zhangsan / 任意密码
            </div>
          </div>
        </template>
      </el-alert>

      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" @submit.prevent="handleLogin">
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            size="large"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" style="width: 100%" :loading="loading" @click="handleLogin">
            登 录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ENABLE_MOCK } from '@/mock'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const isMockMode = computed(() => ENABLE_MOCK)

const loginForm = ref({
  username: '',
  password: ''
})

const loginFormRef = ref(null)
const loading = ref(false)

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await loginFormRef.value.validate()
  loading.value = true

  try {
    await userStore.login(loginForm.value)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error) {
    console.error('登录失败:', error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

.login-box h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}
</style>
