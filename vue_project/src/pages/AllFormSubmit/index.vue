<template>
    <div class="container">
      <div class="listItem">
        <!-- 每一项头条列表 -->
        <div class="containerItem" v-for="item in FormData" :key="item.id" >
          <div>
            <div>
              <span class="text">{{ item.title }}</span>
            </div>
          </div>
        </div>    
        
      </div>
    </div>
  </template>
  


<script setup>

import { findAllSubmitForm } from '../../api/index'; 
import { useRouter } from 'vue-router';
import { useRoute } from 'vue-router';
import { ref, onMounted , getCurrentInstance ,watch, onUpdated} from "vue";


const route = useRoute();
const router = useRouter();
const FormData = ref([]); // 创建一个响应式引用来存储FormData

onMounted(() => {
  findAllSubmitFormData();
})

const findAllSubmitFormData = async () => {
  const result = await findAllSubmitForm();
  console.log(result.data);
  const forms = Object.entries(result.data).map(([key, value]) => {
      return { title: key, id: value };
  });
  FormData.value = forms; // 将新数组赋值给 FormData
  console.log(FormData);
}

</script>



<style lang="less" scoped>
.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;

  .listItem {
    width: 100%;
    max-width: 600px;
  }

  .containerItem {
    margin: 20px;
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    background: linear-gradient(to right, #6a82fb, #fc5c7d);
    color: #fff;
    cursor: pointer;
    transition: transform 0.3s, box-shadow 0.3s;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
    }

    .item-content {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      text-align: center;
    }

    .item-title {
      font-size: 1.5rem;
      margin-bottom: 10px;
    }

    .item-detail {
      font-size: 1rem;
      color: #e0e0e0;
    }
  }
}

@media (max-width: 768px) {
  .container {
    padding: 10px;
  }

  .containerItem {
    padding: 20px;
  }

  .item-title {
    font-size: 1.2rem;
  }

  .item-detail {
    font-size: 0.9rem;
  }
}
</style>