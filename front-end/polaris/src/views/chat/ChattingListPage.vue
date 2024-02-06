<template>
  <div class="first-container">
    <div class="content">
  <div class="text-2xl m-3 font-semibold">나의 채팅방 목록</div>
<div>
<div class="container">
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
                <div class="profileimage">
                  <img :src="data.profileUrl">
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
              <button v-if="data.tradeStatus === 'INPROGRESS'" @click="cancelButtonClick" class="inline-block rounded bg-maintheme1 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2">거래취소</button>
              <button v-else @click="leaveButtonClick" class="inline-block rounded bg-maintheme1 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2">나가기</button>
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
import { useRouter } from 'vue-router';
import { onMounted, ref, computed } from 'vue';
import axios from 'axios';
import { useChatStore } from '@/stores/chatcounter';

const router = useRouter();
const chatStore = useChatStore();

onMounted(async () => {
  await chatStore.fetchChatInfo();
  chatData.value = chatStore.$state.chatData;
  console.log(chatStore.$state.chatData);
});


const chatData = ref(chatStore.$state.chatData);
// const chatData = chatStore.$state.chatData;
// const chatData = computed(() => chatStore.$state.chatData);
// const chatData = computed(() => chatStore.chatData);

// 채팅방 생성 시 POST 요청(상대방 프로필에서 교환채팅/구매채팅 클릭 시의 api 요청)
const createChatRoom = async (senderId: number, receiverId: number, tradeType: string) => {
  try {
    const response = await axios.post<CreateChatroomResponse>('/api/chat', {
      senderId,
      receiverId,
      tradeType,
    }, {
      headers: {
        'Authorization': 'Bearer ',
      }
    });

    if (response.status === 201) {
      console.log('채팅방 생성:', response.data);
      return response.data.data; // 채팅방 정보 반환
    } else {
      console.error('API 요청 실패:', response.status);
      return null;
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    return null;
  }
};

// 채팅방 생성 인터패이스

interface CreateChatroom {
  id: number;
  senderId: number;
  receiverId: number;
  tradeType: string;
}

interface CreateChatroomResponse {
  status: number;
  message: string;
  data: CreateChatroom;
}

// 채팅방 목록

// const chatData = ref<ResponseData | null>(null);

// onMounted(async () => {
//   try {
//         const response = await axios.get<ApiResponse>(`http://i10a801.p.ssafy.io:8082/chat`, {
//       headers: {
//         'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoamhAZ21haWwuY29tIiwiYXV0aCI6IkFVVEhPUklUWSIsImlkIjo4LCJlbWFpbCI6ImhqaEBnbWFpbC5jb20iLCJuaWNrbmFtZSI6Iu2CueynhO2VmCIsImV4cCI6MTcyNDY4Nzg5NH0.RGSg_mX4rSNrHAIIBkfHg1AowDKwyAmzhnk2b7X8xaE',
//       }
//     });
//     if (response.status === 200) {
//       chatData.value = response.data.data;
//       console.log(chatData.value);
//     } else {
//       console.error('API 요청 실패:', response.status);
//     }
//   } catch (error) {
//     console.error('API 요청 중 오류 발생:', error);
//   }
// });



// 채팅방 목록 인터페이스

// interface Chatroom {
//   chatRoomId: number;
//   receiverId: number;
//   nickname: string;
//   profileUrl: string;
//   tradeStatus: string;
//   tradeType: string;
// }

// interface ResponseData {
//   userId: number;
//   chatRoomList: Chatroom[];
// }

// interface ApiResponse {
//   status: number;
//   message: string;
//   data: ResponseData;
// }



const goToProfile = (userId: number) => {
       router.push({ name: 'ProfilePage', params: {id: userId} });
     };

const startSellChat = (chatRoomId: number) => {
  router.push(`/chat/${chatRoomId}/sell`);
};

// const startSellChat = () => {
//   router.push(`chat/sell`);
// };


const startChangeChat = (chatRoomId: number) => {
  router.push(`/chat/${chatRoomId}/change`);
};

const leaveButtonClick = () => {
  const confirmation = confirm('정말 나가시겠습니까? 체팅내역이 사라집니다.')
  if (confirmation) {
    alert('채팅을 삭제합니다')
  }
}

const cancelButtonClick = () => {
  const confirmation = confirm('정말 거래를 취소하시겠습니까? 채팅내역이 사라집니다.')
  if (confirmation) {
    alert('거래가 취소되었습니다.')
  }
}
// 나가는 작업 후 관련 데이터를 백엔드에 알려주어야 함.

</script>

<style scoped>

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
    margin-bottom: 5px;
    
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
