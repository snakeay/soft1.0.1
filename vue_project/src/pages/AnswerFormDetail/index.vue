<template>
    <div class="user-answers">
      <div class="answers-list">
        <div v-for="(answer, index) in DetailList" :key="index">
          <h3>题目 {{ index + 1 }} 答案:</h3>
          <p v-for="item in answer" :key="item">
              <p v-for="it in item" :key="it">
            <span class="answer-item"> {{ it }}</span>
            </p>
          </p>
        </div>
      </div>
    </div>
  </template>

<script lang="ts" setup>
import { onMounted, ref } from "vue"
import { useRouter,useRoute } from 'vue-router'
import type { ElForm } from 'element-plus';
import { ElMessage} from 'element-plus';
import { getUserAnswerForm } from "../../api/index"
const router = useRouter()
const route = useRoute()
const DetailList=ref([]);
const formid = route.query.answerformid;

const getanswers=async()=>{
    const result=await getUserAnswerForm(formid);
    console.log(result.data);
    DetailList.value=result.data;
    console.log(DetailList.value);
}
onMounted(() => {
    getanswers();
});

</script>


<style scoped>
.user-answers {
  display: flex;
  flex-direction: column;
  align-items: center;
  background: linear-gradient(to right, #6a82fb, #fc5c7d);
  padding: 50px;
  border-radius: 15px;
  color: #fff;
  font-family: 'Segoe UI', 'Helvetica Neue', Helvetica, Arial, sans-serif;
}

h1 {
  font-size: 2.5rem;
  margin-bottom: 20px;
  text-align: center;
  background: -webkit-linear-gradient(to right, #ffffff, #e6e6e6);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.user-info {
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 30px;
  text-align: center;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.25);
}

.answers-list {
  width: 100%;
  max-width: 800px;
  background-color: rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.25);
  padding: 30px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.answers-list h3 {
  font-size: 1.5rem;
  color: #fff;
  margin-bottom: 10px;
}

.answer-item {
  display: block;
  margin-left: 20px;
  color: #e6e6e6;
  font-size: 1rem;
  margin-bottom: 5px;
}

/* 添加分隔线样式 */
.answers-list .answer-item:not(:last-child) {
  border-bottom: 1px solid rgba(255, 255, 255, 0.5);
  padding-bottom: 10px;
}

@media (max-width: 768px) {
  .user-answers {
    padding: 20px;
  }

  h1 {
    font-size: 2rem;
  }

  .user-info, .answers-list {
    padding: 20px;
  }

  .answers-list h3 {
    font-size: 1.2rem;
  }
}
</style>