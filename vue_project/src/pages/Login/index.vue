<template>
  <div class="login-container">
    <el-form
      :model="loginForm"
      ref="formRef"
      label-width="80px"
      class="login-form"

      :rules="loginRules" 
    >
      <h2>用户登录</h2>
      <el-form-item label="用户名" prop="account">
        <el-input
          v-model="loginForm.account"
          ref="account"
          name="account"
          autocomplete="off"
          placeholder="请输入用户名"
        ></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
          type="password"
          v-model="loginForm.password"
          autocomplete="off"
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="toAllForm">登录</el-button><!--@click.native.prevent="login"-->
        <el-button type="primary" @click="toRegister">注册</el-button>
        <el-button type="warning" @click="toAdminLogin">去管理员登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts">
 import { defineComponent } from 'vue'
  export default  defineComponent({
    name:'Login'
  })
</script>
<script lang="ts" setup>
import { ref } from "vue"
import { getLogin } from '../../api/index'; // 确保这是正确的导入路径
import { useUserInfoStore } from '../../stores/userInfo';
import { ElMessage } from 'element-plus'; // 导入 Element Plus 的消息提示组件
import type { ElForm } from 'element-plus';
import { useRouter } from 'vue-router'
import { getToken, removeToken, setToken } from '../../utils/token-utils';

const userInfoStore = useUserInfoStore()
const router = useRouter()
type FormInstance = InstanceType<typeof ElForm>
const formRef = ref<FormInstance>()
const loading = ref(false)

//账号密码参数
const loginForm = ref({
      account: "user1",
      password: "user123456",
})
// 校验规则
const validateUsername = (rule: any, value: any, callback: any) => {
  if (value.length < 4) {
    callback(new Error('用户名长度不能小于4位'))
  } else {
    callback()
  }
}
// 校验规则
const validatePassword = (rule: any, value: any, callback: any) => {
  if (value.length < 6) {
    callback(new Error('密码长度不能小于6位'))
  } else {
    callback()
  }
}
// 校验规则
const loginRules = {
  account: [{ required: true, validator: validateUsername }],
  password: [{ required: true, trigger: 'blur', validator: validatePassword }]
}


//点击登录的回调
const toAllForm = async () => {
  // console.log('点击登录');
  await formRef.value?.validate()
  loading.value = true
  try {
    // await getUserInfo(loginForm.value)
    await userInfoStore.login(loginForm.value)
    router.push({ name: "Group" });
    alert("登陆成功");
  } finally {
    loading.value = false
  }
}


const toRegister = ()=> {
  router.push({ name: "Register" });
}

const toAdminLogin = () => {
  router.push({name:"AdminLogin"});
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
.login-form {
  width: 400px;
  text-align: center;
}
</style>
