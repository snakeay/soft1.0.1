<template>
    <div class="groups-container">
      <div class="groups-list">
        <div class="column">
          <h3>已创建的用户组</h3>
          <ul class="groups">
            <li v-for="group in createdGroups" :key="group.id" class="group-item">
              <label class="checkbox-wrapper">
                <input type="checkbox" :value="group.id" v-model="selectedGroups">
                <span class="checkmark"></span>
              </label>
              <span class="group-name">{{ group.name }}</span>
              <span class="group-id">#{{ group.id }}</span>
            </li>
          </ul>
        </div>
        <div class="column">
          <h3>已加入的用户组</h3>
          <ul class="groups">
            <li v-for="group in joinedGroups" :key="group.id" class="group-item">
              <label class="checkbox-wrapper">
                <input type="checkbox" :value="group.id" v-model="selectedGroups">
                <span class="checkmark"></span>
              </label>
              <span class="group-name">{{ group.name }}</span>
              <span class="group-id">#{{ group.id }}</span>
            </li>
          </ul>
        </div>
      </div>
      <div class="button-container">
        <button class="button next-button" @click="next">下一步</button>
        <button class="button clear-button" @click="clearSelection">全部取消</button>
      </div>
    </div>
</template>

<script>
import { ref } from 'vue';
import { findAllCreateGroup,findAllJoinGroup } from '../../api/index'; 
import { ElMessage } from 'element-plus';

export default {
    data() {
        
        return {
            joinedGroups: ref([]),
            createdGroups:ref([]), 
            selectedGroups: []
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
        next() {
            if(this.selectedGroups.length===0){
              ElMessage.error("请先选择你要发布到的组");
              return;
            }
            const selectedGroupsId = JSON.stringify(this.selectedGroups);
            this.$router.push({ name: "CreateForm",query: { selectedGroups: selectedGroupsId }});
            console.log(this.selectedGroups);
        },
        clearSelection() {
            // 清空选中的组
            this.selectedGroups = [];
        }        
    }
}
</script>


<style scoped>
/* 全部样式 */

body {
  font-family: 'Arial', sans-serif;
  background: #f7f7f7;
  margin: 0;
  padding: 0;
}

.groups-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 90%;
  max-width: 1200px;
  margin: 20px auto;
}

.groups-list {
  display: flex;
  justify-content: space-between;
  width: 100%;
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

.checkbox-wrapper {
  margin-right: 15px;
  position: relative;
  height: 18px;
  width: 18px;
}

.checkbox-wrapper input[type="checkbox"] {
  opacity: 0;
  position: absolute;
}

.checkbox-wrapper span {
  position: absolute;
  left: 0;
  top: 0;
  width: 18px;
  height: 18px;
  border: 1px solid #ccc;
  border-radius: 3px;
  background: #fff;
}

.checkbox-wrapper input[type="checkbox"]:checked + span {
  background: #6c63ff;
  border: none;
}

.checkbox-wrapper span:after {
  content: '';
  position: absolute;
  display: none;
}

.checkbox-wrapper input[type="checkbox"]:checked + span:after {
  display: block;
}

.checkbox-wrapper span:after {
  left: 5px;
  top: 1px;
  width: 5px;
  height: 10px;
  border: solid white;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
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

.button-container {
  display: flex;
  justify-content: center;
  margin-top: 30px;
  width: 100%;
}

/* 现有的按钮样式保持不变 */
.button {
  padding: 10px 20px;
  margin: 0 10px;
  border: none;
  border-radius: 5px;
  color: white;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

/* 为下一步按钮添加特定的背景颜色 */
.next-button {
  background-color: #4CAF50; /* 绿色 */
}

/* 鼠标悬停时改变下一步按钮的背景颜色 */
.next-button:hover {
  background-color: #45a049; /* 较深的绿色 */
}

/* 为全部取消按钮添加特定的背景颜色 */
.clear-button {
  background-color: #F44336; /* 红色 */
}

/* 鼠标悬停时改变全部取消按钮的背景颜色 */
.clear-button:hover {
  background-color: #e53935; /* 较深的红色 */
}

/* 响应式设计 */
@media (max-width: 768px) {
  .groups-list {
    flex-direction: column;
  }

  .column {
    width: 100%;
  }
}
</style>