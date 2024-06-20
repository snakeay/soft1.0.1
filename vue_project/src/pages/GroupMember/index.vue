<template>
  <div class="member-list-container">
    <h1 class="list-title">{{ groupName }}({{ members.length }})</h1>

    <!-- 单独展示第一个成员 -->
    <div v-if="members.length > 0" class="first-member">
      <h3>创建者</h3>
      <p>成员昵称：{{ members[0].nickname }}</p>
      <p>成员用户名：{{ members[0].account }}</p>
      <div v-if="isCreatedGroup">
        <button class="tilted-button" @click="deletegroup">解散用户组</button>
      </div>
    </div>

    <!-- 展示剩余成员列表 -->
    <ul class="member-list" v-if="members.length > 1">
      <h3>其他成员</h3>
      <li v-for="member in members.slice(1)" :key="member.id" class="member-item-wrapper">
        <a class="member-item">成员昵称：{{ member.nickname }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;成员用户名：{{ member.account }}</a>
        <div v-if="isCreatedGroup">
          <button class="tilted-button" @click="deletegroupmember(member.id)">-</button>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { showGroupMember, deleteMember,deleteGroup } from '../../api/index';
import { ref, computed } from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { ElMessageBox, ElMessage } from 'element-plus';

export default {
  name: 'GroupMember',
  setup() {
    const route = useRoute();
    const router = useRouter();
    const members = ref([]);

    const groupName = computed(() => route.query.groupName);
    const groupType = computed(() => route.query.groupType);
    const isCreatedGroup = computed(() => groupType.value === 'created');

    async function fetchshowGroupMembers() {
      const result = await showGroupMember(route.query.groupId);
      console.log(result);
      members.value = result.data.membersOfGroup.map(item => ({
        nickname: item.nickname,
        id: item.id,
        account: item.account
      }));
    }

    async function deletegroupmember(memberid) {
      await deleteMember(route.query.groupId, memberid);
      members.value = await fetchshowGroupMembers();
    }

    async function deletegroup() {
      // 使用Element Plus的弹窗确认
      const confirmDelete = await ElMessageBox.confirm(
        '此操作将解散用户组, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).catch(() => false); // 如果用户取消，则返回false

      if (confirmDelete) {
        try {
          // 调用解散用户组的API
          await deleteGroup(route.query.groupId);
          ElMessage.success('用户组已解散');
          members.value = []; // 清空成员列表
          router.push({ name: "Group"});
        } catch (error) {
          // 处理错误情况
          ElMessage.error('解散用户组失败');
          console.error('Error when trying to delete group:', error);
        }
      }
    }

    // 组件创建时立即检查路由查询参数
    fetchshowGroupMembers();

    return {
      members,
      groupName,
      groupType,
      isCreatedGroup,
      fetchshowGroupMembers,
      deletegroupmember,
      deletegroup
    };
  }
};
</script>
  
<style scoped>
/* 通用按钮样式 */
.tilted-button {
  background-color: #5c67f0; /* 优雅的深蓝色背景 */
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 30px; /* 更圆润的边缘 */
  transition: all 0.3s ease;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
  outline: none;
  cursor: pointer;
  margin: 8px 0;
  text-transform: uppercase; /* 按钮文本大写 */
  letter-spacing: 1px; /* 字母间距 */
}

.tilted-button:hover {
  background-color: #4a4fd3; /* 鼠标悬停时颜色变深 */
  transform: translateY(-3px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.3);
}

/* 容器样式 */
.member-list-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 90%;
  max-width: 1200px; /* 限制最大宽度 */
  margin: 80px auto 40px; /* 顶部更大的边距 */
  padding: 40px;
  background: white;
  border-radius: 25px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
  text-align: center; /* 使标题居中 */
  
}

/* 标题样式 */
.list-title {
  font-size: 2.8rem; /* 44.8px */
  color: #333;
  margin-bottom: 50px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
  border-radius: 30px; /* 圆角边框 */
  padding: 10px 20px;
  background: linear-gradient(90deg, rgba(124,58,237,1) 0%, rgba(104,138,245,1) 100%);
  color: white;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.list-title span {
  font-size: 1.2rem; /* 19.2px */
  background: transparent;
  color: #fff;
  padding: 0;
  border-radius: 0;
  margin-left: 0;
}

/* 成员列表样式 */
.member-list {
  width: 100%;
  list-style-type: none;
  padding: 0;
  margin-top: 30px;
}

/* 单个成员样式 */
.member-item-wrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 15px 0;
  border-bottom: 1px solid #e1e5ee;
}

.member-item-wrapper:last-child {
  border-bottom: none;
}

.member-item {
  max-width: 80%;
  font-size: 1rem;
  color: #333;
  transition: color 0.3s;
}

.member-item:hover {
  color: #5c67f0;
}

.first-member {
  width: 100%;
  background: #c47fcd;
  padding: 40px;
  margin-bottom: 30px;
  border-radius: 15px;
  text-align: center;
}

.first-member h3 {
  font-size: 2rem;
  color: #333;
  margin-bottom: 20px;
}

.first-member p {
  font-size: 1.2rem;
  color: #4d4d4d;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .member-list-container {
    width: 95%;
    margin: 20px auto;
    padding: 20px;
  }

  .list-title {
    font-size: 2.2rem;
    margin-bottom: 30px;
  }

  .first-member {
    padding: 30px 20px;
  }

  .first-member h3 {
    font-size: 1.8rem;
  }

  .member-item-wrapper {
    flex-direction: column;
    align-items: start;
  }

  .member-item {
    margin-bottom: 10px;
  }
}
</style>