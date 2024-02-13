<template>
  <div class="first-container">
    <div class="content">
<div class="text-2xl m-3 font-semibold">도서 거래를 위한 채팅공간</div>
<div class="flex">
<div class="flex">
  <div class="bg-white shadow rounded-lg p-4 ml-4" style="height: 700px;">
  <div>
    <h3 class="text-xl font-bold text-gray-900 mb-2">Chat Room</h3>
    <span class="text-base font-normal text-gray-500">상대방과 이야기하여 거래도서를 선택해보세요</span>
  </div>
  <div>
    <div class="bg-gray-50 shadow rounded-lg mb-8 mr-4 ml-4 mt-4 overflow-auto" style="width: 400px; height: 500px;">
      <div class="flex flex-col">
      <div v-for="(message, idx) in chatMessageList" :key="idx">
      <div v-if="message.userId===Number(userId.id)" class="my-message max-w-[300px] rounded-lg bg-yellow-50">{{ message.message }}</div>
      <div v-else class="other-message max-w-[300px] rounded-lg bg-green-50">{{ message.message }}</div>
    </div>

    <!-- <div v-for="(message, idx) in chatMessageList" :key="idx">{{ message.message }}</div> -->
      <div v-for="(item, idx) in recvList" :key="idx">
        <div v-if="(item.userId ===Number(userId.id))" class="my-message max-w-[300px] rounded-lg bg-yellow-50">
        <div>{{ item.message }}</div>
      </div>
      <div v-else class="other-message max-w-[300px] rounded-lg bg-green-50">
        {{ item.message }}
      </div>
        <!-- <h4>{{item.nickname}}</h4> -->
        <!-- <div v-if="item.userId === Number(userId.id)" class="my-message max-w-[300px] rounded-lg bg-yellow-50">
         <div>{{ item.message }}</div>
        </div> -->
        <!-- <div v-else class="other-message max-w-[300px] rounded-lg bg-green-50">
          <div v-for="participant in ChatParticipant" :key="participant.chatRoomId">
          <div>여기에 닉네임이랑 프로필</div>
          </div>
          <div>{{ item.message }}</div>
        </div> -->
        <!-- <div v-else class="other-message max-w-[300px] rounded-lg bg-green-50"> -->
  <!-- <div v-for="participant in ChatParticipant" :key="participant.chatRoomId"> -->
    <!-- <div v-if="participant.receiverId !== item.userId">{{ participant.receiverNickname }}</div> -->
    <!-- <img v-if="participant.receiverId !== item.userId" :src="participant.receiverProfileUrl" alt="Profile" /> -->
    <!-- <div>{{ item.message }}</div> -->
  <!-- </div> -->
  
<!-- </div> -->

      </div>
      <!-- <ConnectSocket/> -->
     <!-- <StartChatting/> -->
     <!-- <StartChat/> -->
    </div>
  </div>
  </div>
  <div class="flex justify-center">
      <!-- <button class="inline-block rounded bg-maintheme1 h-8 px-2 py-1 text-sm font-medium text-white mr-3">img</button> -->
      <!-- <textarea class="border" style="width: 300px;"></textarea> -->
      <input class="border w-[330px] h-[50px]" v-model="newmessage" type="text" @keyup.enter="sendMessage" />
      <button @click="sendMessage" class="inline-block rounded bg-maintheme1 h-8 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2 ml-3 mt-2">전송</button>
    </div>
  </div>
</div>
  <div class="ml-10">
    <div v-if="ChatParticipant?.tradeReceiverId === Number(userId.id)">
      <MySellingBook/>
    </div>
    <div v-else>
    <MyBuyingBook :books="chatRoomTradeBooks"/>
    </div>
  </div>
</div>
</div>  
</div>
</template>

<script setup lang="ts">
import { ref, onMounted, defineExpose, computed, watch, reactive } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import axiosInstance from '@/services/axios';
import MySellingBook from './MySellingBook.vue';
import MyBuyingBook from './MyBuyingBook.vue';
// import ConnectSocket from './ConnectSocket.vue'
// import StartChatting from './StartChatting.vue';
// import StartChat from './StartChat.vue';

// 채팅방 참여자 조회 get 요청
// 참여자 조회 인터페이스
interface ChatParticipantData {
  chatRoomId: number;
  tradeSenderId: number;
  tradeReceiverId: number;
  senderId: number;
  receiverId: number;
  receiverNickname: string;
  receiverProfileUrl: string;
}

interface ChatParticipantResponse {
  status: number;
  message: string;
  data: ChatParticipantData;
}

// 채팅방 아이디 불러오기
// onMounted(() => {
//   console.log(route.params.chatroomId);
//   chatRoomId.value = Number(route.params.chatroomId);

// });

const ChatParticipant = ref<ChatParticipantData | null>(null);
// const senderId = ref<number|null>(null);

// 채팅방 참여자 조회
onMounted(async () => {
    try {
      console.log(route.params.chatroomId);
      chatRoomId.value = Number(route.params.chatroomId);

      const token = ref(localStorage.getItem('user_token'))
      const response = await axiosInstance.value.get<ChatParticipantResponse>(`https://i10a801.p.ssafy.io:8082/chatroom/${chatRoomId.value}`, {
        headers: {
          'Authorization': token.value?.replace("\"", "")
        }
      });
      if (response.status === 200) {
        ChatParticipant.value = response.data.data;
        console.log(response.data.data)
        // console.log(ChatParticipant.value)

      } else {
        console.error('API 요청 실패:', response.status);
      }
    } catch (error) {
      console.error('API 요청 중 오류 발생:', error);
    }
  });

// 유저아이디 조회
const userIdString = ref<string>(localStorage.getItem('user_info') ?? "");
  // 사용자 정보를 나타내는 인터페이스 정의
  interface UserInfo {
    id?: string | null;
  }

let userId: UserInfo;
try {
    userId = JSON.parse(userIdString.value) || {}; // 빈 객체로 기본값 설정
  } catch (error) {
    console.error("Error parsing user_info:", error);
    userId = {}; // JSON 파싱에 실패한 경우 빈 객체로 기본값 설정
  }


// SSE 관련 인터페이스
interface SimpleMessage {
  type: "TEXT";
  chatRoomId : number;
  userId : number;
  nickname: string;
  message: string;
}
const route = useRoute();

const chatRoomId = ref<number | null>(null);


// const chatData = ref(chatStore.$state.chatData);
// const chatRoomId = ref<number | null>(null);
// const chatRoomId = Number(ref(route.params.chatroomId))
const newmessage = ref('');
const recvList = ref<SimpleMessage[]>([]);
const chatRoomTradeBooks = ref<ChatRoomTradeBooks[]>([]);


// 채팅방 입장 요청
const joinChatRoom = () => {
  const eventSource = new EventSource(`https://i10a801.p.ssafy.io:8082/chat/connect/${chatRoomId.value}`);

  eventSource.addEventListener('message', (event) => {
    const data: SimpleMessage = JSON.parse(event.data);
    recvList.value.push(data);
    console.log(data)
  });

  eventSource.addEventListener('change_trade_book_list', (event) => {
    const data: ChatRoomTradeBooks = JSON.parse(event.data)
    chatRoomTradeBooks.value.push(data)
    console.log(data)
  })
};

onMounted(() => {
  joinChatRoom();
});

// fetchChatInfo(모든 정보가 다 옴)가 아니라 채팅방 생성 시 반환되는 정보를 가져와야 할듯? 나의 닉네임, 상대방의 닉네임 정보가 추가로 필요함

const sendMessage = () => {
  if (newmessage.value.trim() !== '' && chatRoomId.value !== null) {
    const message: SimpleMessage = { 
      type: "TEXT", 
      chatRoomId: Number(chatRoomId.value), 
      userId: Number(userId.id), 
      nickname: 'nickname',  
      message: newmessage.value.trim(),
    };

    fetch('https://i10a801.p.ssafy.io:8082/chat/send_message', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(message),
    })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.text().then((text) => text ? JSON.parse(text) : {});
    })
    .catch((error) => {
      console.error('There has been a problem with your fetch operation:', error);
    });
    newmessage.value = '';
  }
};


// 도서목록조회
interface ChatRoomTradeBooks {
  userId: number;
  id: number;
  title: string;
  author: string;
  status: string;
  price: number | null;
  seriesId: number | null;
}

//기존 채팅 메시지 조회
interface ChatMessageListResponse {
  status: number;
  message: string;
  data: {chatMessageList: ChatMessageList[];};
  // data: ChatMessageList;
  // data: ChatMessageList[];
}

interface ChatMessageList{
  type: string;
  chatRoomId: number;
  userId: number;
  nickname: string;
  // createdAt: Date;
  createdAt: string;
  message: string;
}
// const chatMessageList = ref<ChatMessageList | null>(null);
const chatMessageList = ref<ChatMessageList[]>();
  onMounted(async () => {
    try {
      const token = ref(localStorage.getItem('user_token'))
      const response = await axiosInstance.value.get<ChatMessageListResponse>(`https://i10a801.p.ssafy.io:8082/chatroom/message/${chatRoomId.value}`, {
        headers: {
          'Authorization': token.value?.replace("\"", "")
        } 
      });
      if (response.status === 200) {
        // chatMessageList.value = response.data.data.chatMessageList;
        chatMessageList.value = response.data.data.chatMessageList;  
        console.log(chatMessageList.value)
        console.log(response.data.data)
        console.log(response.data)


      } else {
        console.error('API 요청 실패:', response.status);
      }
    } catch (error) {
      console.error('API 요청 중 오류 발생:', error);
    }
  });

// onMounted(async () => {
//     try {
//       const token = ref(localStorage.getItem('user_token'))
//       const response = await axiosInstance.value.get<ChatMessageListResponse>(`https://i10a801.p.ssafy.io:8082/chatroom/message/${chatRoomId.value}`, {
//         headers: {
//           'Authorization': token.value?.replace("\"", "")
//         }
//       });
//       // if (response.status === 200) {
//       //   chatMessageList.value = response.data.data.chatMessageList;
//       //   // console.log(response.data.data)
//       //   console.log('기존 메시지:', chatMessageList.value);
      
//       // if (response.data.data) {
//       //   chatMessageList.value = response.data.data.chatMessageList;
//       //   console.log('기존메시지', chatMessageList.value);
//       // } else {
//       //   console.log('response.data.data is null or undefined');
//       // }
//       if (response.status === 200) {
//         console.log(response.data.data)
//         // chatMessageList.value = response.data.data['chatMessageList'];
//         // chatMessageList.value = (response.data.data as { chatMessageList: ChatMessageList[] })['chatMessageList'];
//         chatMessageList.value = response.data.data.chatMessageList
//         // chatMessageList.value = [...response.data.data];
//         // chatMessageList.value = response.data.data;
//         console.log('기존메시지', chatMessageList.value);

//       } else {
//         console.error('API 요청 실패:', response.status);
//       }
//     } catch (error) {
//       console.error('API 요청 중 오류 발생:', error);
//     }
//   });

//   const receiverId = ref<number | null>(null);
// // console.log('ChatParticipant:', ChatParticipant.value);
//   watch(ChatParticipant, (newValue) => {
//     if (newValue !== null) {
//       receiverId.value = newValue.receiverId;
//       console.log('receiverId:', receiverId.value);

//     }
//   });


</script>

<style scoped>
  .first-container {
    padding-top: 50px;
    display: grid;
    grid-template-columns: 1fr auto 1fr;
    height: 100vh;
    align-items: center;
  }

  .content {
    grid-column: 2;
  }

  .friend-nickname{
    /* 프로필명(이름) 스타일 조정 */
    font-weight: bold;
    font-size: 0.875rem; /* 16 브라우저 기준 14px */
}
.profile-img{
    width: 3.125rem;
}

.chat-content{
    /* 메시지(채팅) 내용 스타일 조정 */
    color: gray;
    font-size: 0.75rem; /* 16 브라우저 기준 12px */
}
.my-message {
  float: right;
  clear: both;
  margin: 5px;
  padding: 5px;
}

.other-message {
  float: left;
  clear: both;
  margin: 5px;
  padding: 5px;
}
</style>
