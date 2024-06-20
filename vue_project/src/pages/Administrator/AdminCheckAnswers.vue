<template>
  <div class="questionnaire-answers">
    <h2>问卷答案查询 - 问卷标题: {{ title }}</h2>
    <!-- 使用Element UI的表格展示提交问卷的用户数据 -->
    <el-table :data="submitters" style="width: 100%" border>
      <el-table-column label="序号" width="80" align="center">
        <template v-slot="scope">
          <span>{{ scope.$index + 1 }}</span> <!-- 序号从1开始 -->
        </template>
      </el-table-column>
      <el-table-column prop="nickname" label="昵称" align="center"></el-table-column>
      <el-table-column label="操作" width="180" align="center">
        <template v-slot="scope">
          <el-button type="primary" size="small" @click="viewAnswers(scope.row.id,scope.row.nickname)">
            查询答案
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { useRouter } from 'vue-router';
import { ElTable, ElTableColumn, ElButton, ElMessage } from 'element-plus';
import { getAllAnswerForm } from '../../api/index'; // 导入API函数

const route = useRoute();
const router = useRouter();
const questionnaireId = route.query.questionnaireCoreId; // 从路由参数中获取问卷ID
const title = ref(route.query.formtitle); // 从路由参数中获取问卷标题
const submitters = ref([]); // 存储提交问卷的用户信息

const fetchSubmitters = async () => {
  try {
    const result = await getAllAnswerForm(questionnaireId);
    const Answers = Object.entries(result.data).map(([key, value]) => {
      return { 
      id: value,
      nickname: key, };
    });
    submitters.value=Answers;
  } catch (error) {
    ElMessage.error('获取问卷提交者列表失败');
  }
};

const viewAnswers = (answerCenterId,nickName) => {
  // 跳转到查看用户答案界面，并传递答案中心节点的ID
  router.push({ name: 'UserAnswers', query: { answerCenterId,nickName } });
};

// 组件加载完成后获取提交问卷的用户列表
fetchSubmitters();
</script>

<style scoped>
.questionnaire-answers {
  padding: 30px;
  background: #f5f7fa;
  border-radius: 15px;
  margin: 30px;
  color: #333;
}

h2 {
  color: #333;
  font-size: 1.5rem;
  margin-bottom: 1rem;
  text-align: center;
}

/* 表格的序号列样式 */
.el-table-column--selection .cell {
  text-align: center;
}
</style>