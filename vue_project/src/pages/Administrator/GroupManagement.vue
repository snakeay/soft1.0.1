<template>
  <div class="group-management">
    <h2>用户组管理</h2>
    <!-- 使用Element UI的表格展示用户组数据 -->
    <el-table :data="groupList" style="width: 100%" border>
      <el-table-column prop="id" label="组ID" width="100" align="center"></el-table-column>
      <el-table-column prop="groupName" label="用户组名" align="center"></el-table-column>
      <!-- 删除操作按钮列 -->
      <el-table-column label="删除" width="120" align="center">
        <template v-slot="scope">
          <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">
            <i class="el-icon-delete"></i> 删除
          </el-button>
        </template>
      </el-table-column>
      <!-- 成员管理操作按钮列 -->
      <el-table-column label="成员管理" width="120" align="center">
        <template v-slot="scope">
          <el-button type="primary" size="small" @click="handleMemberManagement(scope.row.id)">
            <i class="el-icon-user"></i> 成员管理
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElTable, ElTableColumn, ElButton, ElMessage } from 'element-plus';
import { getAllGroups, deleteGroup } from '../../api/index'; // 导入API函数

const router = useRouter();
const groupList = ref([]);

const fetchGroups = async () => {
  try {
    const result = await getAllGroups();
    groupList.value = result.data;
  } catch (error) {
    ElMessage.error('获取用户组列表失败');
  }
};

const handleDelete = async (groupId) => {
  if (confirm('确定要删除该用户组吗?')) {
    try {
      await deleteGroup(groupId);
      ElMessage.success('用户组删除成功');
      await fetchGroups(); // 重新获取用户组列表以刷新表格
    } catch (error) {
      ElMessage.error('删除用户组失败');
    }
  }
};

const handleMemberManagement = (groupId) => {
  // 跳转到用户组成员管理界面，并传递用户组ID
  console.log(groupId);
  router.push({ name: 'GroupMembersManagement', query: { groupId } });
};

// 组件加载完成后获取用户组列表
fetchGroups();
</script>

<style scoped>
.group-management {
  padding: 30px;
  background-size: cover;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  border-radius: 15px;
  margin: 30px;
  color: #fff; /* 文字颜色，适应暗色背景 */
  text-align: center; /* 标题居中 */
}

h2 {
  font-size: 2rem;
  margin-bottom: 2rem;
  background-color: rgba(0, 0, 0, 0.5); /* 半透明背景，提高可读性 */
  padding: 10px;
  border-radius: 5px;
}

.el-table {
  margin-top: 20px;
  background-color: rgba(255, 255, 255, 0.8); /* 表格背景色，适应暗色主题 */
}

.el-table th {
  background-color: #3e8ef7; /* 表格头部背景色 */
  color: #fff; /* 表格头部文字颜色 */
}

.el-table tr {
  transition: background-color 0.3s;
}

.el-table tr:hover {
  background-color: #e1ecf4;
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

.el-icon-delete {
  margin-right: 5px;
}

.el-icon-user {
  margin-right: 5px;
}

</style>