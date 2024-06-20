<template>
  <div class="questionnaire-page">
    <!-- 标题区域 -->
    <header class="page-header">
      <h1 class="title">{{ title }}</h1>
    </header>

    <!-- 问题展示区域 -->
    <div class="questionnaire-questions">
      <div v-for="(question, index) in detailList" :key="index" class="question-item">
        <div class="question-header">
          <h2 class="question-title">题目{{ index + 1 }}: {{ question.questionTitle }} ({{ getQuestionTypeDescription(question.type) }})</h2>
        </div>
        <div class="question-body">
          <!-- 单选题 -->
          <div v-if="question.type === 0">
            <div v-for="option in question.questionContent" :key="option">
              <label class="option-label">
                <input type="radio" :value="option" v-model="question.selectedOptions">
                {{ option }}
              </label>
            </div>
          </div>
          <!-- 多选题 -->
          <div v-else-if="question.type === 1">
            <div v-for="option in question.questionContent" :key="option">
              <label class="option-label">
                <input type="checkbox" :value="option" v-model="question.selectedOptions">
                {{ option }}
              </label>
            </div>
          </div>
          <!-- 填空题 -->
          <div v-else-if="question.type === 2">
            <input type="text" class="text-input" v-model="question.selectedOptions" placeholder="请输入答案">
          </div>
        </div>
      </div>
    </div>

    <!-- 提交按钮区域 -->
    <div class="submit-button-container">
      <el-button class="submit-button" type="primary" @click="Submit">
        提交
      </el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getForm,SubmitForm } from '../../api/index';
import { useRoute,useRouter } from 'vue-router';
const route = useRoute();
const router = useRouter();
const id = route.query.formid;
const title = route.query.formtitle;
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
      return '未知类型'; // 如果type不是预期的值，返回'未知类型'
  }
};
// 获取详情初始化数据
const getDetail = async () => {
  const result = await getForm(id);
  console.log(result.data);
  for (const item of result.data) {
    // 直接修改响应式数组
    detailList.value.push({
      type: item.type,
      questionTitle: item.questionTitle,
      questionContent: item.questionContent,
      selectedOptions: [item.answer],
      useranswer: item.answer,
    });
  }
  console.log(detailList.value);
};

const Submit = () => {

  const answers = detailList.value.map(question => {

    return Array.isArray(question.selectedOptions) ? 
           question.selectedOptions : 
           [question.selectedOptions];
  });


  const updatedAnswers = answers.map(answerObj => {
    return {
      answer: Object.assign([], answerObj)
    };
  });

  console.log(updatedAnswers);

  SubmitForm(updatedAnswers, id);

  router.push({name:"Group"});
};



const getanswer = (index) => {
  return detailList.value[index].useranswer;
}

// 页面初始化钩子
onMounted(() => {
  getDetail();
});
</script>

<style scoped>
.questionnaire-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #fff;
}

.page-header {
  width: 100%;
  text-align: center;
  padding: 20px 0;
}

.title {
  font-size: 2.5rem;
  color: #333;
}

.questionnaire-questions {
  width: 90%;
  max-width: 1200px;
  padding: 40px;
  text-align: left;
  margin-bottom: 40px;
}

.question-item {
  margin-bottom: 20px;
}

.question-title {
  font-size: 1.5rem;
  margin-bottom: 10px;
}

.option-label {
  display: block;
  margin-bottom: 10px;
}

.text-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
}

.submit-button-container {
  width: 100%;
  padding: 20px;
  text-align: center; /* 提交按钮居中 */
}

.submit-button {
  padding: 20px 40px;
  border-radius: 8px;
  background-color: #409eff; /* 蓝色背景 */
  color: white;
  border-color: transparent;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  font-size: 1.2rem; /* 按钮文字大小 */
}

.submit-button:hover {
  transform: scale(1.05); /* 鼠标悬浮时放大 */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

@media (max-width: 768px) {
  .title {
    font-size: 2rem;
  }

  .question-title {
    font-size: 1.2rem;
  }

  .submit-button {
    padding: 15px 30px;
    font-size: 1rem;
  }
}
</style>