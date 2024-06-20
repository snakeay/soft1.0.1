<template>
  <div class="super-admin-dashboard">
    <!-- 功能区域的卡片 -->
    <div class="admin-cards">
      <el-card class="admin-card" v-for="item in adminItems" :key="item.title">
        <div class="admin-title">{{ item.title }}</div>
        <div class="admin-description">{{ item.description }}</div>
        <el-button type="primary" @click="goToManagement(item.route)">
          {{ item.buttonText }}
        </el-button>
      </el-card>
    </div>
    
    <!-- 退出登录按钮 -->
    <el-button class="logout-button" type="danger" @click="logout">
      退出登录
    </el-button>
  </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';
import { ElMessage, ElCard, ElButton } from 'element-plus';
import { removeToken } from '../../utils/token-utils' ;

const router = useRouter();
const adminItems = [
  {
    title: '用户管理',
    description: '查看和删除用户',
    route: 'UserManagement',
    buttonText: '管理用户'
  },
  {
    title: '用户组管理',
    description: '查看和编辑用户组',
    route: 'GroupManagement',
    buttonText: '管理用户组'
  },
  {
    title: '问卷管理',
    description: '查询所有问卷及其数据',
    route: 'SurveyManagement',
    buttonText: '查询问卷'
  },
];

const goToManagement = (routeName) => {
  router.push({ name: routeName });
  ElMessage.success(`跳转到${routeName}界面`);
};


// 退出登录函数
const logout = () => {
  removeToken()
  // 跳转到登录页面，并传递一个标志表示用户已登录退出
  router.push({ name: 'Login', query: { logout: 'true' } });
  ElMessage.success('您已成功退出登录');
};
</script>

<style scoped>

.super-admin-dashboard {
  display: flex;
  flex-direction: column;
  align-items: center; /* 水平居中所有子元素 */
  justify-content: space-around;
  min-height: 90vh;
  padding: 20px;
  background-color: #f5f5f5;
}

.admin-cards {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  width: 100%;
}

.admin-card {
  border-radius: 20px; /* 圆角更大 */
  overflow: hidden;
  min-height: 300px; /* 增加高度 */
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.25); /* 增加阴影 */
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  flex: 1 1 300px;
  max-width: calc(50% - 20px);
  margin: 20px;
  background: linear-gradient(45deg, #6a82fb, #fc5c7d); /* 背景渐变色 */
  color: white; /* 文字颜色 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 30px; /* 增加内边距 */
  text-align: center; /* 文本居中 */
}

.admin-title {
  font-size: 2rem; /* 增大字体大小 */
  margin-bottom: 20px; /* 增加底部外边距 */
}

.admin-description {
  font-size: 1.2rem; /* 增大字体大小 */
  margin-bottom: 30px; /* 增加底部外边距 */
}

.el-button {
  padding: 12px 36px; /* 增加内边距 */
  border-radius: 50px; /* 圆形边缘 */
  background: linear-gradient(to right, #9c27b0, #e91e63); /* 按钮渐变色 */
  color: white;
  border: none; /* 移除边框 */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3); /* 添加阴影 */
  transition: all 0.3s ease; /* 平滑过渡 */
  margin-top: 20px;
}

.el-button:hover {
  transform: translateY(-5px); /* 悬停时上移 */
}


.logout-button {
  margin-top: 30px; 
  padding: 15px 30px;
  font-size: 18px;
  border-radius: 50px;
  background: linear-gradient(to right, #ff6ec7, #7873f5);
  color: white;
  border: none;
  box-shadow: 0 8px 15px rgba(255, 111, 199, 0.6);
  transition: all 0.3s ease;
  width: auto; /* 按钮宽度自适应 */
  text-align: center; /* 按钮内文本居中 */
  cursor: pointer;
}

.logout-button:hover {
  transform: translateY(-5px);
  background: linear-gradient(to right, #ff73d8, #7b6ff5);
}


@media (max-width: 768px) {
  .admin-card {
    max-width: 100%;
    margin: 10px;
  }
}
</style>