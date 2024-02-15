<template>
  <Navvar></Navvar>
  <div class="first-container mt-40 font-[gowun-dodum]">
    <div class="content">
  <div class="text-2xl m-3 font-semibold">나의 채팅방 목록</div>
<div>
<div class="container font-[gowun-dodum]">
  <table class="min-w-full divide-y-2 divide-gray-200 bg-white text-sm">
    <thead class="ltr:text-left rtl:text-right">
      <tr>
        <th>대화상대</th>
        <th>거래분류</th>
        <th>채팅방 설정</th>
      </tr>
    </thead>
    <tbody class="divide-y divide-gray-200">
      <template v-if="chatData && chatData.chatRoomList && chatData.chatRoomList.length > 0">
      <tr v-for="(data, index) in chatData.chatRoomList" :key="index">
            <td>
              <div @click="goToProfile(data.receiverId)" class="profilecontainer">
              <div class="image-container">
                <div>
                  <img class="profileimage" :src="data.profileUrl">
                  </div>
              </div>
              <div>{{ data.nickname }}</div>
              </div>
            </td>
            <td v-if="data.tradeType==='PURCHASE'">판매/구매</td>
            <td v-else>교환</td>
            <td>
              <button v-if="data.tradeType === 'EXCHANGE'" @click="startChangeChat(data.chatRoomId)" class="inline-block rounded bg-maintheme1 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2">채팅하기</button>
              <button v-else @click="startSellChat(data.chatRoomId)" class="inline-block rounded bg-maintheme1 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2">채팅하기</button>
              <button v-if="data.tradeStatus === 'INPROGRESS'" @click="cancelButtonClick(data.chatRoomId)" class="inline-block rounded bg-maintheme1 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2">거래취소</button>
              <button v-else @click="leaveButtonClick(data.chatRoomId)" class="inline-block rounded bg-maintheme1 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2">나가기</button>
            </td>
          </tr></template>
    </tbody>
  </table>
</div>
</div>
</div>
<div>

</div></div>
</template>


<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { useRouter } from 'vue-router';
import { onMounted, ref, computed } from 'vue';
import axios from 'axios';
import axiosInstance from '@/services/axios';
import { useChatStore } from '@/stores/chatcounter';

const router = useRouter();
const chatStore = useChatStore();

onMounted(async () => {
  await chatStore.fetchChatInfo();
  chatData.value = chatStore.$state.chatData;
  console.log(chatStore.$state.chatData);
});


const chatData = ref(chatStore.$state.chatData);

const goToProfile = (userId: number) => {
       router.push({ name: 'ProfilePage', params: {id: userId} });
     };


const startSellChat = (chatroomId: number) => {
  router.push({
    name: 'sellchattingbox',
    params: {
      chatroomId: chatroomId, 
      
    },
  });
};


const startChangeChat = (chatroomId: number) => {
  router.push(`/chat/${chatroomId}/change`);
};

const cancelButtonClick = async (chatroomId: number) => {
  const confirmation = confirm('정말 취소하시겠습니까? 체팅내역이 사라집니다.')
  if (confirmation) {
    alert('거래를 취소합니다');

    try {
      const response = await axiosInstance.value.delete(`${import.meta.env.VITE_API_KEY}/trade/${chatroomId}`);
  
      if (response.status === 200) {
        console.log('채팅방 삭제 성공:', response.data);
        await chatStore.fetchChatInfo();
        chatData.value = chatStore.$state.chatData;
      } else {
        console.error('API 요청 실패:', response.status);
      }
    } catch (error) {
      console.error('API 요청 중 오류 발생:', error);
    }
    
  }
}

const leaveButtonClick = async (chatroomId: number) => {
  const confirmation = confirm('정말 나가시겠습니까? 체팅내역이 사라집니다.')
  if (confirmation) {
    alert('채팅방을 나갑니다');

    try {
      const response = await axiosInstance.value.delete(`${import.meta.env.VITE_API_KEY}/trade/${chatroomId}`);
  
      if (response.status === 200) {
        console.log('채팅방 삭제 성공:', response.data);
        await chatStore.fetchChatInfo();
        chatData.value = chatStore.$state.chatData;
      } else {
        console.error('API 요청 실패:', response.status);
      }
    } catch (error) {
      console.error('API 요청 중 오류 발생:', error);
    }
    
  }
  }

</script>

<style scoped>
  @font-face {
  font-family: 'gowun-dodum';
  src: url('../../../../public/GowunDodum-Regular.ttf');
}


  th, td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: center;
  }

  button {
    margin: 5px;
  }

  .profilecontainer {
    display: flex;
    flex-direction: column;

  }
  .profileimage {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    object-fit: cover;
    border: solid black 1px;
    margin-bottom: 10px;
    
  }

  .image-container {
    display: flex;
    justify-content: center;
  }

  .first-container {
    padding-top: 50px;
    display: grid;
    grid-template-columns: 1fr auto 1fr;
    /* height: 100vh; */
    align-items: start;
    /* align-items: center; */
  }
  .content {
    grid-column: 2;
    min-width: 800px;
  }
</style>
