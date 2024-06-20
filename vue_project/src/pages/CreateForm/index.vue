<template>
  <div class="main-content">
      <div class="card">
      <div style="margin-bottom: 20px;">
      <el-input
          placeholder="请输入问卷名称"
          v-model="surveyName"
          style="width: 500px;height:50px;"
      ></el-input>
      </div>
      <!-- 添加问题的按钮 -->
      <div style="margin-bottom: 20px; display: flex; gap: 10px; align-items: center;">
          <el-button type="primary" @click="addQuestion(0)">新建单选题</el-button>
          <el-button type="warning" @click="addQuestion(1)">新建多选题</el-button>
          <el-button type="success" @click="addQuestion(2)">新建填空题</el-button>
          <el-button type="info" @click="addfixeQuestion">添加固定题目</el-button>
  
          <el-select
            v-model="selectedOption"
            placeholder="请选择一个问题"
          >
          <el-option
            v-for="question in fixedQuestions"
            :key="question"
            :label="question"
            :value="question"
          ></el-option>
          </el-select>

      </div>
  
      <!-- 题目列表 -->
      <div v-for="(item, index) in questionList" :key="item.id" class="question-item" style="margin-bottom: 20px;">
          <div>
          <span style="font-weight: bold; margin-right: 5px;">题目{{ index + 1 }}</span>
          <el-input
          v-model="item.questionTitle"
          style="width: 70%; margin-right: 5px;"
          placeholder="请输入题目名称"
          @update:modelValue="updateQuestionName(index, $event)"
          ></el-input>
          <el-tag :style="{'background-color': typeColors[item.type], 'color': 'white'}" disable-transitions>{{ getQuestionType(item.type) }}</el-tag>
          <el-button type="danger" plain @click="delQuestion(index)">删除</el-button>
          </div>
  
          <!-- 选项列表 -->
          <div v-if="item.type !== 2">
          <div
              v-for="(option, optionIndex) in item.questionContent"
              :key="option.id"
              class="option-item"
              style="margin-top: 10px; display: flex; align-items: center;"
          >
              <span style="margin-right: 5px;">选项{{ optionIndex + 1 }}</span>
              <el-input
              v-model="item.questionContent[optionIndex]"
              style="width: 50%; margin-right: 5px;"
              placeholder="请输入选项内容"
              @input="updateOptionContent(index, optionIndex, $event.target.value)"
              ></el-input>
              <el-button type="danger" plain @click="delOption(index, optionIndex)">删除</el-button>
          </div>
          <div style="margin-top: 10px; display: flex; align-items: center;">
              <el-input
              v-model="item.newOptionContent"
              style="width: 50%; margin-right: 5px;"
              placeholder="新选项内容"
              ></el-input>
              <el-button type="primary" @click="addOption(index)">添加选项</el-button>
          </div>
          </div>
      </div>
      
      </div>
      <div style="margin-top: 30px; text-align: right;">
      <el-button @click="cancelCreate">取消创建</el-button>
      <el-button type="primary" @click="submitSurvey">提交并发布问卷</el-button>
      </div>
  </div>
  </template>
  
  <script>
  import { test2,createNewForm } from '../../api';
  import { toRaw } from 'vue';
  export default {
  name: 'QuestionnaireDesign',
  data() {
      return {
        surveyName: '', // 绑定问卷名称输入框的数据
        questionList: [],
        typeColors: {
            0: '#2a60c9',
            1: 'orange',
            2: 'seagreen'
        },

        // 选中的选项
        fixedQuestions: [
        '您的姓名是什么',
        '您的年龄是多少',
        '您的性别是什么',
        '您的联系方式是多少',
        ],
        selectedOption: null
      };
  },
  created() {
    let serializedGroups = this.$route.query.selectedGroups;
    if (serializedGroups) {
      this.selectedGroups = JSON.parse(serializedGroups);
    } else {
      this.selectedGroups = [];
    }
  },
  methods: {
      getQuestionType(type) {
        switch (type) {
          case 0:
            return '单选题';
          case 1:
            return '多选题';
          case 2:
            return '填空题';
          default:
            return '未知类型'; // 如果 type 不是 0, 1, 或 2，可以返回 '未知类型'
        }
      },
      addQuestion(typeNumber) {
          const newQuestion = {
              questionTitle: '',
              type: typeNumber,
              questionContent: typeNumber !== 2 ? [] : [],
              newOptionContent: ''
          };
          this.questionList.push(newQuestion);
      },
      addOption(index) {
      const question = this.questionList[index];
      if (question.newOptionContent.trim()) {
          question.questionContent.push(question.newOptionContent.trim());
          
          question.newOptionContent = ''; // 清空新选项输入框
      } else {
          this.$message.error('选项内容不能为空');
      }
      },
      delQuestion(index) {
      this.questionList.splice(index, 1);
      },
      delOption(questionIndex, optionIndex) {
      this.questionList[questionIndex].questionContent.splice(optionIndex, 1);
      },
      updateQuestionName(index, newName) {
      this.questionList[index].questionTitle = newName;
      },
      updateOptionContent(questionIndex, optionIndex, newContent) {
      this.questionList[questionIndex].questionContent[optionIndex].content = newContent;
      },


      addfixeQuestion(){
        
        const newQuestion = {
              questionTitle: this.selectedOption,
              type: 2,
              questionContent: [],
              newOptionContent: ''
        };
        this.questionList.push(newQuestion);
      },



      // 取消创建问卷
      cancelCreate() {
        // 清空问卷名称和题目列表
        this.surveyName = '';
        this.questionList = [];
        this.$router.push({ name: "Group" });
      },
      submitSurvey() {
        console.log(this.questionList);
        createNewForm(this.questionList,this.surveyName,toRaw(this.selectedGroups));
        this.$router.push({ name: "Group" });
      },

    
  }
  };
  </script>
  
  <style scoped>
  .main-content {
  max-width: 800px;
  margin: 30px auto;
  padding: 20px;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  .card {
  padding: 20px;
  border-radius: 4px;
  background-color: #f9f9f9;
  }
  .question-item {
  border-bottom: 1px solid #e8e8e8;
  padding-bottom: 20px;
  margin-bottom: 20px;
  }
  .option-item {
  display: flex;
  align-items: center;
  margin-right: 10px;
  }
  </style>