<template>
  <div>
    <h1>{{ title }}</h1>
  </div>
  <div class="seeDetails">
    <!-- 根据题型展示不同的问题 -->
    <div v-for="(question,index) in detailList" :key="index">
      <div>
        <h2>题目{{ index+1 }}:{{ question.questionTitle }} ({{ getQuestionTypeDescription(question.type) }})</h2>
      </div>
      <!--单选题-->
      <div v-if="question.type === 0">
        <div v-for="(option, optionIndex) in question.questionContent" :key="optionIndex">
          <label>
            <input type="radio" :name="'question' + index" :value="optionIndex" v-model="question.selectedOption">
            {{ option }}
          </label>
        </div>
      </div>
      <!--多选题-->
      <div v-if="question.type == 1">
        <div v-for="option in question.questionContent" :key="option">
          <label>
            <input type="checkbox" :value="option" v-model="question.selectedOptions">
            {{ option }}
          </label>
        </div>
      </div>
      <!--填空题-->
      <div v-if="question.type == 2">
        <input type="text"  placeholder="请输入答案">
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { adminGetQuestionnaireDetails } from '../../api/index';
import { useRoute } from 'vue-router';
const route = useRoute();
const id = route.query.questionnaireCoreId;
const title = route.query.formtitle;
//const detailList = [];
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
  const result = await adminGetQuestionnaireDetails(id);
  for (const item of result.data) {
    // 直接修改响应式数组
    detailList.value.push({
      type: item.type,
      questionTitle: item.questionTitle,
      questionContent: item.questionContent,
      selectedOptions: [],
    });
  }
  console.log(detailList.value);
};

// 页面初始化钩子
onMounted(() => {
  getDetail();
});
</script>

<style lang="less" scoped>
.seeDetails {
  width: 90%;
  max-width: 1200px;
  margin: 40px auto;
  padding: 40px;
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  font-family: 'Segoe UI', 'Helvetica Neue', Helvetica, Arial, sans-serif;

  > div {
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 1px solid #eaeaea;
  }

  h2 {
    font-size: 2rem;
    color: #333333;
    margin-bottom: 20px;
    text-transform: capitalize;
  }

  label {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
    font-size: 1rem;
    color: #666666;
    cursor: pointer;
  }

  input[type="radio"],
  input[type="checkbox"] {
    margin-right: 10px;
  }

  input[type="radio"] {
    border-radius: 50%;
    width: 20px;
    height: 20px;
    border: 2px solid #c0c0c0;
    position: relative;
    top: 4px;

    &:checked::before {
      content: '';
      display: block;
      width: 12px;
      height: 12px;
      margin: 3px;
      background-color: #007bff;
      border-radius: 50%;
    }
  }

  input[type="checkbox"] {
    border: 2px solid #c0c0c0;
    width: 20px;
    height: 20px;
    position: relative;
    top: 4px;

    &:checked {
      background-color: #007bff;
      border-color: #007bff;

      &::before {
        content: '✔';
        color: #ffffff;
        font-size: 1.2rem;
        line-height: 1.2;
        text-align: center;
        position: absolute;
        top: -1px;
        left: 3px;
      }
    }
  }

  input[type="text"] {
    width: 100%;
    padding: 15px;
    margin-top: 10px;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;

    &:focus {
      outline: none;
      border-color: #007bff;
      box-shadow: 0 0 8px rgba(0, 123, 255, 0.2);
    }
  }

  // 添加按钮和卡片的渐变背景
  .el-button {
    background-image: linear-gradient(to right, #6a82fb, #fc5c7d);
    color: white;
    border-color: transparent;
    transition: box-shadow 0.3s ease;

    &:hover {
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
    }
  }

  // 为标题添加渐变文本效果
  h1 {
    background: -webkit-linear-gradient(45deg, #6a82fb, #fc5c7d);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    font-size: 2.5rem;
    text-align: center;
    margin-bottom: 30px;
  }

  // 响应式设计调整
  @media (max-width: 768px) {
    width: 95%;
    padding: 20px;

    h1 {
      font-size: 2rem;
    }

    h2 {
      font-size: 1.5rem;
    }

    label {
      font-size: 0.9rem;
    }
  }
}
</style>