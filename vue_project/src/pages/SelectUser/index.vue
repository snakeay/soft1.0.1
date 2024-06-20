<template>  
  <div class="table-container">  
    <table class="user-table">  
      <thead>  
        <tr>  
          <th class="action-column">序号</th>  
          <th>昵称</th>  
          <th class="action-column">操作</th>  
        </tr>  
      </thead>  
      <tbody>  
        <tr v-for="(user, index) in userList" :key="user.answerformid" class="user-item">  
          <td class="action-column">{{ index + 1 }}</td>  
          <td>{{ user.nickname }}</td>  
          <td class="action-column">  
            <button @click="searchAnswerDetail(user.answerformid)" class="btn-detail">查看答案卷</button>  
          </td>  
        </tr>  
      </tbody>  
    </table>  
  </div>  
</template>  

<script lang="ts" setup>
import { onMounted, ref } from "vue"
import { useRouter,useRoute } from 'vue-router'
import type { ElForm } from 'element-plus';
import { ElMessage} from 'element-plus';
import { getAllAnswerForms } from "../../api/index"
const router = useRouter()
const route = useRoute()
const userList=ref([]);
const formid = route.query.id;
const getuser=async()=>{
    const result = await getAllAnswerForms(formid);
    const Answers = Object.entries(result.data).map(([key, value]) => {
        return { nickname: key, answerformid: value };
    });
    userList.value=Answers;
    console.log(userList);
}

const searchAnswerDetail=(answerformid)=>{
  router.push({name:"AnswerFormDetail",query:{answerformid}});
}

onMounted(() => {
 getuser();
});

</script>

<style scoped>  
.table-container {  
  /* 添加渐变背景 */  
  background: linear-gradient(to right, #f8f9fa, #e9ecef);  
  padding: 20px;  
  border-radius: 10px;  
}  
  
.user-table {  
  width: 100%;  
  border-collapse: collapse;  
  margin-bottom: 20px;  
}  
  
.user-table th,  
.user-table td {  
  padding: 10px;  
  border: 1px solid #ddd;  
  text-align: center; /* 居中显示 */  
}  
  
.user-table th {  
  background-color: #f2f2f2;  
}  
  
.user-table .action-column {  
  width: 200px; /* 控制操作列宽度 */  
}  
  
.btn-detail {  
  padding: 5px 10px;  
  background-color: #4CAF50; /* Green */  
  border: none;  
  color: white;  
  text-align: center;  
  text-decoration: none;  
  display: inline-block;  
  font-size: 14px;  
  cursor: pointer;  
  transition: background-color 0.3s;  
}  
  
.btn-detail:hover {  
  background-color: #45a049; /* Green darken */  
}  
  
.user-item:nth-child(even) {  
  background-color: #f9f9f9; /* 偶数行浅灰色背景 */  
}  
</style>