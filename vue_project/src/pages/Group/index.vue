<template>
    <div>
        <div>
            <button class="create-group-button" @click="createGroup">
               <i class="fa fa-plus"></i> 创建用户组
            </button>
        </div>
        <div v-if="showcreateInputForm" class="input-container-create">
            <input type="text" placeholder="输入用户组名称" v-model="groupName">
            <div class="button-container">
                <button @click="submitcreateGroup">创建</button>
                <button @click="cancel">取消</button>
            </div>          
        </div>
        
        <div :class="{ 'shift-exit-button': showInputForm}" >
            <button class="exit-group-button" @click="exitGroup">
                <i class="fa fa-times"></i> 退出用户组
            </button>  
        </div>
        <div>
            <button class="join-group-button" @click="joinGroup">
                <i class="fa fa-times"></i> 加入用户组
            </button>  
        </div>
        <div v-if="showjoinInputForm" class="input-container-join">
            <input type="text" placeholder="输入用户组ID" v-model="groupId">
            <div class="button-container">
                <button @click="submitjoinGroup">加入</button>
                <button @click="cancel">取消</button>
            </div>          
        </div>

    </div>
    <!-- 左侧列表 -->
    <div class="groups-list">
        <div class="column">
          <h3>已创建的用户组</h3>
          <ul class="groups">
            <li v-for="group in createdGroups" :key="group.id" class="group-item" @click="toGroupMember(group.name,'created',group.id)">
              <span class="group-name">{{ group.name }}</span>
              <span class="group-id">#{{ group.id }}</span>
            </li>
          </ul>
        </div>
        <div class="column">
          <h3>已加入的用户组</h3>
          <ul class="groups">
            <li v-for="group in joinedGroups" :key="group.id" class="group-item" @click="toGroupMember(group.name,'joined',group.id)">
              <span class="group-name">{{ group.name }}</span>
              <span class="group-id">#{{ group.id }}</span>
              <div v-if="isDelete">
                   <button class="tilted-button" @click.stop="deletegroup(group.name, group.id)">-</button>
              </div>
            </li>
          </ul>
        </div>
      </div>
</template>


<script>
import { ref } from 'vue';
import { createGroup,joinGroup,findAllCreateGroup,findAllJoinGroup,exitJoinedGroup } from '../../api/index'; 

export default {
    data() {
        
        return {
            showcreateInputForm: false, // 控制创建输入框显示的变量
            showjoinInputForm:false, // 控制加入输入框显示的变量
            isDelete:false, //控制退出按钮的变量
            joinedGroups: ref([]),
            createdGroups:ref([]),
            
        };
    },
    created() {
        this.fetchCreatedGroups();
        this.fetchJoinedGroups();
    },
    methods: {
        async fetchCreatedGroups(){
            const result = await findAllCreateGroup();
            this.createdGroups = result.data.groupCreated.map(item => ({
            name: item.groupName, // 映射 groupName 属性
            id: item.id // 映射 id 属性
            }));
        },
        async fetchJoinedGroups(){
            const result = await findAllJoinGroup();
            this.joinedGroups = result.data.groupJoined.map(item => ({
            name: item.groupName, 
            id: item.id 
            }));
            console.log(result);
        },

        createGroup() {
            //显示输入框
            this.showcreateInputForm = true;
            this.showjoinInputForm = false;
            this.isDelete = false;
        },
        
        async submitcreateGroup() {
            if (this.groupName.trim() !== '') { // 检查组名是否为空
                const result=await createGroup(this.groupName);
                this.groupName = ''; // 清空输入框
                this.showcreateInputForm = false; // 隐藏输入表单
                alert('您已成功创建用户组，用户组id为'+result.data.groupId);
                this.fetchCreatedGroups();
            } else {
                alert('请输入用户组名称');
            }
        },

        joinGroup() {
            //显示输入框
            this.showjoinInputForm = true;
            this.showcreateInputForm = false;
            this.isDelete = false;
        },
        
        async submitjoinGroup() {
            if (this.groupId.trim() !== '') { // 检查组名是否为空
                const result = await joinGroup(this.groupId);
                this.groupId = ''; // 清空输入框
                this.showjoinInputForm = false; // 隐藏输入表单
                alert("已成功加入组" + result.data.groupName);
                this.fetchJoinedGroups();
            } else {
                alert('请输入用户组名称');
            }
        },
        
        cancel() {
            this.groupName = ''; // 清空创建用户组输入框内容
            this.groupId = ''; //清空加入用户组输入框内容
            this.showcreateInputForm = false; // 取消后隐藏输入框
            this.showjoinInputForm = false; // 取消后隐藏输入框
        },

        exitGroup() {
            this.isDelete = !this.isDelete;
            // 退出用户组的逻辑
        },
        
        async deletegroup(name,id) {
            await exitJoinedGroup(id);
            this.isDelete = false;
            alert("你已经成功退出了"+name+"用户组");
            this.fetchJoinedGroups();

        },

        //查看用户组内成员，进入用户组详细成员名单
        toGroupMember(groupName, groupType, groupId) {
            this.$router.push({ name: 'GroupMember',query: { groupName, groupType,groupId }});
        },
       
    }
}
</script>


<style>



.create-group-button {
    position: fixed;
    right: 20px;
    top: 100px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    width: 200px;
}

.create-group-button:hover {
background-color: #45a049;
}

.input-container-create {
    display: flex;
    flex-direction: column; /* 垂直排列子元素 */
    align-items: flex-start; /* 子元素向容器的起始边对齐 */
    position: fixed; /* 固定定位，相对于视口定位 */
    right: 20px; /* 距离视口右侧20px */
    top: 150px; /* 距离视口顶部150px，确保在按钮下方 */
    padding: 10px;
    background: #f9f9f9; /* 浅灰色背景 */
    border-radius: 5px;
    box-shadow: 0 4px 6px rgba(0,0,0,0.1); /* 添加阴影 */
}

.input-container-create input {
    margin-bottom: 10px; /* 输入框和按钮之间的间距 */
    padding: 5px;
    border: 1px solid #ddd; /* 边框 */
    border-radius: 4px;
}

.input-container-join {
    display: flex;
    flex-direction: column; /* 垂直排列子元素 */
    align-items: flex-start; /* 子元素向容器的起始边对齐 */
    position: fixed; /* 固定定位，相对于视口定位 */
    right: 20px; /* 距离视口右侧20px */
    top: 500px; /* 距离视口顶部150px，确保在按钮下方 */
    padding: 10px;
    background: #f9f9f9; /* 浅灰色背景 */
    border-radius: 5px;
    box-shadow: 0 4px 6px rgba(0,0,0,0.1); /* 添加阴影 */
}

.input-container-join input {
    margin-bottom: 10px; /* 输入框和按钮之间的间距 */
    padding: 5px;
    border: 1px solid #ddd; /* 边框 */
    border-radius: 4px;
}

.button-container {
    display: flex;
    justify-content: flex-start; /* 按钮水平排列并靠左对齐 */
    margin-top: 10px; /* 与输入框的间距 */
}

.button-container button {
    margin-right: 10px; /* 按钮之间的间距 */
    padding: 5px 15px;
    border: none;
    border-radius: 4px;
    background-color: #5cb85c;
    color: white;
    cursor: pointer;
}

.button-container button:hover {
    background-color: #4cae4c;
}

.button-container button:last-child {
    background-color: #f44336; /* 取消按钮使用不同的颜色 */
}

.button-container button:last-child:hover {
    background-color: #e53935;
}




.exit-group-button {
    position: fixed; /* 固定定位，使其始终在视口的某个位置 */
    right: 20px; /* 距离右侧20px */
    top: 250px; /* 距离视口顶部150px，位于创建按钮下方 */
    background-color: #2196F3; /* 蓝色背景 */
    color: white; /* 白色文字 */
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    margin-top: 10px; /* 与上方按钮保持一定间距 */
    width: 200px;
}

.exit-group-button:hover {
    background-color: #1a78c2; /* 鼠标悬停时的深蓝色 */
}

.join-group-button {
    position: fixed;
    right: 20px; /* 假设加入按钮在左侧 */
    top: 450px;
    background-color: #795548; /* 棕色背景 */
    color: white; /* 白色文字 */
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    width: 200px;
    text-align: center; /* 确保文本在按钮内居中 */
}

.join-group-button:hover {
    background-color: #5d4037; /* 更深的棕色 */
}

.fa-plus {
margin-right: 5px; /* 与文本内容保持一定间距 */
}

body {
  font-family: 'Arial', sans-serif;
  background: #f7f7f7;
  margin: 0;
  padding: 0;
}

.groups-list {
  display: flex;
  justify-content: space-between;
  width: 80%;
}

.column {
  flex: 1;
  padding: 0 20px;
}

h3 {
  color: #333;
  margin-bottom: 20px;
}

.groups {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.group-item {
  display: flex;
  align-items: center;
  background: #fff;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px;
}

.group-details {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-left: 10px;
}

.group-name {
  font-weight: bold;
}

.group-id {
  font-size: 0.9em;
  color: #888;
}



.tilted-button {
    background-color: #ff386a; /* 按钮背景色 */
    color: white;
    border: none;
    padding: 5px 10px;
    border-radius: 5px;
    cursor: pointer;
    transition: transform 0.2s ease; /* 倾斜效果的过渡 */
}

.tilted-button:hover {
    transform: scale(1.1); /* 鼠标悬浮时放大到原来的110% */
}

</style>
