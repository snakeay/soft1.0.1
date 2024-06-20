<template>
  <div class="user-management">
    <h2>用户管理</h2>
    <!-- 使用Element UI的表格展示用户数据 -->
    <el-table :data="users" style="width: 100%" border highlight-current-row>
      <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
      <el-table-column prop="account" label="用户名" align="center"></el-table-column>
      <el-table-column prop="nickname" label="昵称" align="center"></el-table-column>
      <!-- 密码列 -->
      <el-table-column prop="password" label="密码" align="center"></el-table-column>
      <!-- 删除操作按钮列 -->
      <el-table-column label="操作" width="120" align="center">
        <template v-slot="scope">
          <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">
            <i class="el-icon-delete"></i> 删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>



<script lang="ts" setup>
import { ref } from 'vue';
import { ElTable, ElTableColumn, ElButton, ElMessage } from 'element-plus';
import { getAllUsers, deleteUsers } from '../../api/index'; // 导入getAllUsers和deleteUsers函数

const users = ref([]);

const fetchUsers = async () => {
  try {
    const result = await getAllUsers();
    users.value = result.data;
  } catch (error) {
    ElMessage.error('获取用户列表失败');
  }
};

const handleDelete = async (userId) => {
  if (confirm('确定要删除该用户吗?')) { // 弹出确认框
    try {
      // 调用deleteUsers函数并传递userId
      await deleteUsers(userId);
      ElMessage.success('用户删除成功');
      await fetchUsers(); // 重新获取用户列表以刷新表格
    } catch (error) {
      ElMessage.error('删除用户失败');
    }
  }
};

fetchUsers();
</script>

<style scoped>
.user-management {
  padding: 30px;
  background: #f5f7fa; /* 浅蓝色背景 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  border-radius: 15px;
  margin: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

h2 {
  color: #333;
  font-size: 2rem;
  margin-bottom: 2rem;
  text-align: center;
  border-bottom: 2px solid #1890ff; /* 标题下划线 */
  padding-bottom: 10px;
}

.el-table {
  margin-top: 20px;
  background-color: #ffffff; /* 表格背景色 */
}

.el-table th {
  background-color: #eef1f6; /* 表格头部背景色 */
  color: #333; /* 表格头部文字颜色 */
}

.el-table tr {
  transition: background-color 0.3s; /* 行悬停过渡效果 */
}

.el-table tr:hover {
  background-color: #e1ecf4; /* 行悬停背景色 */
}

.el-button--danger.is-active, .el-button--danger:active {
  color: #fff;
  background-color: #ff4d4f; /* 红色按钮点击效果 */
  border-color: #ff4d4f;
}

.el-button i {
  margin-right: 5px; /* 图标与文字间距 */
}

@media (max-width: 768px) {
  .user-management {
    padding: 15px;
  }

  h2 {
    font-size: 1.5rem;
  }
}

.el-table__row {
  animation: fadeIn 1s;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

/* 密码列的样式 */
.el-table .password-column {
  font-family: monospace; /* 等宽字体，便于查看 */
  color: #333; /* 正常文字颜色 */
}
</style>