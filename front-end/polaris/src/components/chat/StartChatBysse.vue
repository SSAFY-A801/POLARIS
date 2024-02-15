<template>
  <div>

  </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { ref } from 'vue';
import { useRoute } from 'vue-router';


const route = useRoute();
const chatroomId = Number(route.params.chatroomId); 


interface enterChatResponse {
  status: number;
  message: string;
  data: object;
}
const token = ref(localStorage.getItem('user_token'))
// 채팅방 입장 get요청
const enterChat = async () => {
    try {
      const response = await axios.get<enterChatResponse>(`${import.meta.env.VITE_API_KEY}/chat/connect/${chatroomId}`, {
        headers: {
          'Authorization': token.value?.replace("\"", "")

        }
      });
      if (response.status === 200) {
        console.log('입장완료')
      } else {
        console.error('API 요청 실패:', response.status);
      }
    } catch (error) {
      console.error('API 요청 중 오류 발생:', error);
    }
  };

</script>

<style scoped>

</style>