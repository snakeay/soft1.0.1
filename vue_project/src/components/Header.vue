<template>
  <div class="headerContainer">
    <!-- 头部左侧区域 -->
    <div class="left">
      <ul>
        <!--
        <li @click="HighlightHandler(index,)"  v-for="(item,index) in findAllTypeList" :key="item.tid">
          <a :class="{ active: item.isHighlight }" href="javascript:;">{{item.tname}}</a>
        </li>
        -->
        <!--
        <li @click="toAllForm" >
          <a :class="{ active: isHighlightAll }" href="javascript:;">全部表单</a>
        </li>
        -->
        <li @click="toAllFormCreate" >
          <a href="javascript:;">创建的表</a>
        </li>
        <li @click="toAllFormSubmit">
          <a href="javascript:;">已提交</a>
        </li>
        <li @click="toAllFormunSubmit">
          <a href="javascript:;">未提交</a>
        </li>
        <li @click="toGroup">
          <a href="javascript:;">用户组</a>
        </li>
        <!--
        <li @click="toAllFormCreate">
          <a href="javascript:;">test</a>
        </li>
        <li @click="totestpage">
          <a href="javascript:;">测试界面</a>
        </li>
        -->
      </ul>
    </div>
      <div class="right">
     
      <div style="display: flex; justify-content: center; align-items: center;">
             <el-dropdown>
          <el-button type="primary">
          更多功能<el-icon class="el-icon--right"><arrow-down /></el-icon>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <!--
              <el-dropdown-item>个人中心</el-dropdown-item>
              -->
              <el-dropdown-item @click="toCreateForm">创建表单</el-dropdown-item>
              <el-dropdown-item @click="Logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      </div>


  
 </div>
</template>

<script>
import { defineComponent } from 'vue'
export default defineComponent({
  name: 'Header'
})
</script>

<script setup>
import { getfindAllTypes, isUserOverdue } from '../api/index'
import { ref, onMounted , getCurrentInstance ,watch, onUpdated} from "vue"
import { useRouter } from 'vue-router'
import { ArrowDown } from '@element-plus/icons-vue'
import { removeToken } from '../utils/token-utils' 
import pinia from '../stores/index';
import { useUserInfoStore } from '../stores/userInfo'
const userInfoStore = useUserInfoStore(pinia)
const nickName = ref("")
// 获取到 全局事件总线
const { Bus } = getCurrentInstance().appContext.config.globalProperties
const router = useRouter()
const keywords = ref("") // 收集搜索最新头条参数
const isHighlightAll = true;
const isHighlightCreate = false;
const isHighlightGroup = false;
//监视搜索参数的变化 ,当搜索参数变化的时候给HeadlineNews组件传递数据
watch(keywords, (newVal) => {
  Bus.emit('keyword', newVal)
})
const findAllTypeList = ref([])//所有头条分类
const testdata=['item1','item2','item3']

//点击去用户组界面
const toGroup = () => {
  router.push({ name: "Group" });
}

//点击去已创建的页
const toAllFormCreate = () => {
  router.push({ name: "AllFormCreate" });
}


//点击去已提交的页
const toAllFormSubmit = () => {
  router.push({ name: "AllFormSubmit" });
}

//点击去已提交的页
const toAllFormunSubmit = () => {
  router.push({ name: "AllFormunSubmit" });
}



//点击去发布表单界面
const toCreateForm = () => {
  router.push({ name: "SelectGroup" });
}


//点击切换高亮的回调(排他思想)
const HighlightHandler = (index) => {
  findAllTypeList.value.forEach((item) => {
    item.isHighlight = false
  })
  // 切换高亮的时候把tid传给HeadlineNews组件
  findAllTypeList.value[index].isHighlight = true
  Bus.emit('tid', findAllTypeList.value[index].tid)
}


//点击退出登录直接退回登陆界面
const Logout = () => {
  removeToken()
  userInfoStore.initUserInfo()
  nickName.value = ""
  router.push({ name: "Login" });
}

//点击发布新闻的回调
const handlerNews = async () => {
  //发送请求判断用户是否token过期
  await isUserOverdue()
  router.push({ name: "addOrModifyNews" });
}

</script>

<style>
.hidden{
  display: none;
}
.el-dropdown {
  vertical-align: top;
  width: 100px;
}

.el-dropdown+.el-dropdown {
  margin-left: 15px;
}

.el-icon-arrow-down {
  font-size: 12px;
}
</style>

<style lang="less" scoped>
.headerContainer {
  position: relative; /* 确保元素有定位上下文 */
  z-index: 1000; /* 一个较高的z-index值 */
  width: 100%;
  height: 60px;
  background: #212529;
  display: flex;
  justify-content: space-around;
  .left {
    ul {
      display: flex;
      li {
        list-style: none;
        margin-left: 20px;
        a:-webkit-any-link {
          text-decoration: none;
          color: #59646b;
          &.active {
            color: #c0adab;
          }
        }
      }
    }
  }
  .right {

    /*
    .containerButton {
      display: flex;
      align-items: center;
    }
    */

    line-height: 60px;
    display: flex;
    flex-wrap: nowrap;
    .rightInput {
      display: flex;
       align-items: center;
      :deep(.el-input__inner) {
        height: 30px;
        width: 150px;
      }
    }
    .btn-dropdown{
      display: flex;
      align-items: center;
    }
    :deep(.el-button) {
      margin: 0 0 0 10px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}


.example-showcase .el-dropdown + .el-dropdown {
  margin-left: 15px;
}
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>


















