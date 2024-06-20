<template>
  <div class="group-members-management">
    <h2>用户组成员管理 - 用户组ID: {{ groupId }}</h2>
    <!-- 使用Element UI的表格展示用户组成员数据 -->
    <el-table :data="groupUsers" style="width: 100%" border>
      <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
      <el-table-column prop="account" label="用户名" align="center"></el-table-column>
      <el-table-column prop="nickname" label="昵称" align="center"></el-table-column>
      <el-table-column prop="password" label="密码" align="center"></el-table-column>
      <!-- 角色列 -->
      <el-table-column label="角色" width="100" align="center">
        <template v-slot="scope">
          <span v-if="scope.$index === 0">管理员</span>
          <span v-else>成员</span>
        </template>
      </el-table-column>
      <!-- 操作列 -->
      <el-table-column label="操作" width="120" align="center">
        <template v-slot="scope">
          <el-button
            type="danger"
            size="small"
            v-if="scope.$index !== 0"
            @click="handleRemoveUser(scope.row.id)"
          >
            移出
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { ElTable, ElTableColumn, ElButton, ElMessage } from 'element-plus';
import { checkGroupUsers, deleteUserFormGroup } from '../../api/index'; // 导入API函数

const groupUsers = ref([]);
const route = useRoute();
const groupId = route.query.groupId;
const fetchGroupUsers = async () => {
  try {
    const result = await checkGroupUsers(groupId);
    groupUsers.value = result.data;
  } catch (error) {
    ElMessage.error('获取用户组成员列表失败');
  }
};

const handleRemoveUser = async (userId) => {
  if (confirm('确定要从用户组中移出该用户吗?')) {
    try {
      await deleteUserFormGroup(groupId, userId);
      ElMessage.success('用户已从用户组中移出');
      await fetchGroupUsers(); // 重新获取用户组成员列表以刷新表格
    } catch (error) {
      ElMessage.error('移除用户失败');
    }
  }
};

// 组件加载完成后获取用户组成员列表
fetchGroupUsers();
</script>

<style scoped>
.group-members-management {
  padding: 30px;
  background: linear-gradient(to right, #6a82fb, #fc5c7d);
  border-radius: 15px;
  margin: 30px;
  color: #fff;
  text-align: center;
}

h2 {
  font-size: 2rem;
  margin-bottom: 2rem;
  background-color: rgba(0, 0, 0, 0.5);
  padding: 10px;
  border-radius: 5px;
}

.el-table {
  margin-top: 20px;
  background-color: rgba(255, 255, 255, 0.8);
}

.el-table th {
  background-color: #3e8ef7;
  color: #fff;
}

.el-table tr {
  transition: background-color 0.3s;
}

.el-table tr:hover {
  background-color: rgba(255, 255, 255, 0.9);
}

.el-table .admin-row {
  background-color: #ffe5b4; /* 管理员行的特殊背景色 */
}

.el-table .member-row {
  background-color: transparent; /* 普通成员行的背景色 */
}

.el-button {
  border-radius: 20px;
  padding: 8px 15px;
  transition: background-color 0.3s, box-shadow 0.3s;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.el-button:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
}

.el-button:disabled {
  background-color: #ccc;
  color: #fff;
  border-color: #ccc;
}

/* 为管理员行添加特殊样式 */
.el-table .is-admin {
  background-color: #ffe5b4;
}

/* 禁用删除按钮的样式 */
.el-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

</style>