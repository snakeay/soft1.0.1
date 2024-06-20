<template>
  <div >
    <Header v-show="isHeader"></Header>
    <router-view></router-view>
  </div>
</template>


<script >
import Header from './components/Header.vue'
 import { defineComponent } from 'vue'
  export default  defineComponent({
    name:'App'
  })
</script>
<script  setup>
import { computed } from "vue"
import { useRoute } from 'vue-router'
const route = useRoute() // 路由信息对象
// 判断是否显示header组件
 const isHeader =  computed(() => {
    return route.name !== "Login" && route.name !== "Register" && route.name !== "addNews"&& route.name !== "Administrator"&& route.name !== "UserManagement"&& route.name !== "GroupManagement"&& route.name !== "GroupMembersManagement"
    && route.name !== "SurveyManagement"&& route.name !== "AdminCheckAnswers"&& route.name !== "AdminCheckQuestions"&& route.name !== "UserAnswers"&& route.name !== "AdminLogin";
})
  
</script>

<style lang="less" scoped>
  
</style>