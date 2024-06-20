<template>
  <div class="question-answers">
    <h1>所有答案概览</h1>
    <div class="answers-container">
      <!-- 遍历detailList -->
      <div class="answer-item" v-for="item in detailList" :key="item.nickname">
        <div class="nickname">{{ item.nickname }}</div>
        <div class="answers">
          <span v-for="(answer, index) in item.answers" :key="index" class="answer">
            {{ answer }}{{ index < item.answers.length - 1 ? '' : '' }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { getQuestionDetail } from '../../api/index';
  import { useRoute } from 'vue-router';
  const route = useRoute();
  const id = route.query.id;
  const questiontitle = route.query.questiontitle;

  const detailList = ref([]);
 
  // 获取详情初始化数据
  
  const getQuestionDetailList = async() => {
    const result = await getQuestionDetail(id,questiontitle);
    const Answers = Object.entries(result.data).map(([key, value]) => {
      return { nickname: key, answers: value };
    });
    detailList.value=Answers;
  }
  
  // 页面初始化钩子
  onMounted(() => {
    getQuestionDetailList();
  });
  </script>
  
  <style scoped>
  .question-answers {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 50px;
    background: linear-gradient(to right, #6a82fb, #fc5c7d);
    color: #fff;
    font-family: 'Segoe UI', 'Helvetica Neue', Helvetica, Arial, sans-serif;
  }
  
  h1 {
    font-size: 2.5rem;
    margin-bottom: 1rem;
    text-align: center;
  }
  
  .answers-container {
    width: 100%;
    max-width: 800px;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 20px;
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.2);
    padding: 30px;
    display: flex;
    flex-direction: column;
    align-items: stretch;
  }
  
  .answer-item {
    background: rgba(255, 255, 255, 0.2);
    border-radius: 10px;
    padding: 20px;
    margin-bottom: 15px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .nickname {
    font-size: 1.2rem;
    font-weight: bold;
    color: #ddd;
  }
  
  .answers {
    display: flex;
    flex-wrap: wrap;
    gap: 5px;
  }
  
  .answer {
    background: rgba(255, 255, 255, 0.8);
    border-radius: 5px;
    padding: 5px 10px;
    color: #333;
    font-size: 1rem;
  }
  
  @media (max-width: 768px) {
    .question-answers {
      padding: 20px;
    }
  
    h1 {
      font-size: 2rem;
    }
  
    .answer-item {
      padding: 15px;
    }
  }
  </style>