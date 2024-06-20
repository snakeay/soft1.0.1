<template>
  <div class="questionnaire-page">
    <h1>{{ title }}</h1>

    
    <!-- 查询全部作答情况按钮 -->
    <el-button type="primary" @click="toAllUserDetail">查询该表的全部作答情况</el-button>

    <div class="question-list">
      <div v-for="(question, index) in detailList" :key="index" class="question-item">
        <div class="question-header">
          <h2>题目{{ index + 1 }}: {{ question.questionTitle }} ({{ getQuestionTypeDescription(question.type) }})</h2>
          <el-button type="success" @click="searchquestiondetail(question.questionTitle)">查询该题目</el-button>
        </div>
        <!-- 题目内容 -->
        <div class="question-content">
          <!-- 单选题 -->
          <div v-if="question.type === 0">
            <div v-for="(option, optionIndex) in question.questionContent" :key="optionIndex">
              <label>
                <input type="radio" :name="'question' + index" :value="optionIndex" v-model="question.selectedOption">
                {{ option }}
              </label>
            </div>
          </div>
          <!-- 多选题 -->
          <div v-else-if="question.type === 1">
            <div v-for="option in question.questionContent" :key="option">
              <label>
                <input type="checkbox" :value="option" v-model="question.selectedOptions">
                {{ option }}
              </label>
            </div>
          </div>
          <!-- 填空题 -->
          <div v-else-if="question.type === 2">
            <input type="text" placeholder="请输入答案">
          </div>
        </div>
      </div>
    </div>
     <!-- 删除表单按钮，放置在题目展示下方 -->
    <el-button type="danger" class="delete-form-button" @click="deleteForm">删除表单</el-button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getForm, adminDeleteForm } from '../../api/index';
import { useRoute, useRouter } from 'vue-router';
import { ElButton, ElMessage } from 'element-plus';

const route = useRoute();
const router = useRouter();
const id = route.query.formid;
const title = ref(route.query.formtitle);
const detailList = ref([]);

const getQuestionTypeDescription = (type) => {
  switch (type) {
    case 0:
      return '单选';
    case 1:
      return '多选';
    case 2:
      return '填空';
    default:
      return '未知类型';
  }
};

const getDetail = async () => {
  const result = await getForm(id);
  detailList.value = result.data.map((item) => ({
    type: item.type,
    questionTitle: item.questionTitle,
    questionContent: item.questionContent,
    selectedOptions: [],
  }));
};

const searchquestiondetail = async (questiontitle) => {
  router.push({ name: "QuestionDetail", query: { id, questiontitle } });
};

const toAllUserDetail = () => {
  router.push({ name: "SelectUser", query: { id } });
};

const deleteForm = async () => {
  if (confirm('确定要删除该问卷吗？这将不可逆！')) {
    try {
      await adminDeleteForm(id);
      ElMessage.success('问卷删除成功');
      router.push({ name: 'AllFormCreate' }); // 确保这个路由名称与你的路由配置匹配
    } catch (error) {
      ElMessage.error('删除问卷失败');
    }
  }
};

onMounted(() => {
  getDetail();
});
</script>

<style scoped>
.questionnaire-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 50px;
  background: linear-gradient(to right, #6a82fb, #fc5c7d);
  border-radius: 15px;
  color: #fff;
  font-family: 'Segoe UI', 'Helvetica Neue', Helvetica, Arial, sans-serif;
  min-height: 100vh;
  margin-top: -50px;
}

h1 {
  font-size: 2.5rem;
  margin-bottom: 20px;
  color: #ffffff;
}

.el-button {
  margin: 10px;
  border-radius: 20px;
  padding: 10px 20px;
  transition: background-color 0.3s, box-shadow 0.3s;
}

.el-button--danger {
  background-color: #f56c6c;
  border-color: #e45656;
  color: white;
}

.el-button--danger:hover {
  background-color: #e45656;
}

.question-list {
  width: 90%;
  max-width: 1000px;
  margin-top: 30px;
}

.question-item {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 20px;
  word-wrap: break-word;
}

.question-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

h2 {
  font-size: 1.5rem;
  color: #ffffff;
}

.el-button--success {
  background-color: #67c23a;
  border-color: #67c23a;
  color: white;
}

.el-button--success:hover {
  background-color: #5daf34;
}

@media (max-width: 768px) {
  .questionnaire-page {
    padding: 20px;
  }

  h1 {
    font-size: 2rem;
  }

  .question-list {
    width: 100%;
  }
}

/* 添加删除表单按钮的样式 */
.delete-form-button {
  margin-top: 20px; /* 与题目展示内容保持间距 */
  background-color: #ff4d4f; /* 红色背景 */
  border-color: #ff4d4f; /* 红色边框 */
  color: white; /* 白色文字 */
}

.delete-form-button:hover {
  background-color: #e03131; /* 悬停时更深的红色 */
  border-color: #e03131; /* 悬停时更深的红色边框 */
}

</style>