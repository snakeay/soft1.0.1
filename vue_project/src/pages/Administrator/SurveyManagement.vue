<template>
  <div class="questionnaire-management">
    <h2>问卷管理</h2>
    <!-- 使用Element UI的表格展示问卷数据 -->
    <el-table :data="questionnaires" style="width: 100%" border>
      <el-table-column prop="id" label="问卷ID" width="120" align="center"></el-table-column>
      <el-table-column prop="title" label="问卷主题" align="center"></el-table-column>
      <!-- 查看问卷题目操作列 -->
      <el-table-column label="查看问卷题目" width="180" align="center">
        <template v-slot="scope">
          <el-button type="primary" size="medium" @click="viewQuestionnaire(scope.row.id, scope.row.title)">
            查看题目
          </el-button>
        </template>
      </el-table-column>
      <!-- 查询问卷答案操作列 -->
      <el-table-column label="查询问卷答案" width="180" align="center">
        <template v-slot="scope">
          <el-button type="success" size="medium" @click="viewAnswers(scope.row.id, scope.row.title)">
            查询答案
          </el-button>
        </template>
      </el-table-column>
      <!-- 删除问卷操作列 -->
      <el-table-column label="操作" width="180" align="center">
        <template v-slot="scope">
          <el-button type="danger" size="medium" @click="deleteForm(scope.row.id)">
            删除表单
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
import { getAllQuestionnaire, adminDeleteForm } from '../../api/index'; // 

const router = useRouter();
const questionnaires = ref([]);

const fetchQuestionnaires = async () => {
  try {
    const result = await getAllQuestionnaire();
    questionnaires.value = result.data;
  } catch (error) {
    ElMessage.error('获取问卷列表失败');
  }
};

const viewQuestionnaire = (id,title) => {
  // 跳转到查看问卷题目界面，并传递问卷ID
  router.push({ name: 'AdminCheckQuestions', query: { questionnaireCoreId : id, formtitle : title } });
};

const viewAnswers = (id,title) => {
  // 跳转到查询问卷答案界面，并传递问卷ID
  router.push({ name: 'AdminCheckAnswers', query: { questionnaireCoreId : id, formtitle : title } });
};

const deleteForm = async (id) => {
  if (confirm('确定要删除该问卷吗？这将不可逆！')) { // 弹出确认框
    try {
      await adminDeleteForm(id);
      ElMessage.success('问卷删除成功');
      fetchQuestionnaires(); // 重新获取问卷列表
    } catch (error) {
      ElMessage.error('删除问卷失败');
    }
  }
};

// 组件加载完成后获取问卷列表
fetchQuestionnaires();
</script>

<style scoped>
.questionnaire-management {
  padding: 30px;
  background: linear-gradient(to right, #6a82fb, #fc5c7d);
  border-radius: 15px;
  margin: 30px;
  color: #fff;
  text-align: center;
}

h2 {
  font-size: 2.5rem; /* 增大标题字体 */
  margin-bottom: 2rem;
  background-color: rgba(0, 0, 0, 0.5);
  padding: 10px;
  border-radius: 5px;
}

.el-table {
  margin-top: 20px;
  background-color: rgba(255, 255, 255, 0.8);
  font-size: 1.2rem; /* 增大表格字体 */
}

.el-table th {
  background-color: #3e8ef7;
  color: #fff;
}

.el-button {
  margin: 5px;
  border-radius: 20px;
  padding: 10px; /* 增大按钮内边距 */
  transition: background-color 0.3s, box-shadow 0.3s;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.el-button:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
}
.el-table-column--operation button {
  margin: 5px; /* 为按钮添加外边距 */
}

/* 可以为删除按钮添加特定的颜色或样式 */
.el-button--danger {
  background-color: #f56c6c; /* 红色背景 */
  border-color: #e45656; /* 红色边框 */
}

.el-button--danger:hover {
  background-color: #e45656; /* 悬停时更深的红色 */
}

</style>