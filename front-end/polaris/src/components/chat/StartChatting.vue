<template>
  <div>
    <div>
    <div>
    <div>{{chatRoomId}}번 채팅방</div>
    <div>
      <input v-model="newmessage" type="text" @keyup.enter="sendMessage" />
      <div v-for="(item, idx) in recvList" :key="idx">
        <!-- <h4>{{item.nickname}}</h4> -->
        <div> 나의 메시지 </div>
        <p class="my-message">{{ item.message }}</p>
      </div>
    </div>
  </div>
  </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, watchEffect } from 'vue';
import { useChatStore } from '@/stores/chatcounter';
import { useRoute } from 'vue-router';
import axios from 'axios';

const chatStore = useChatStore();

// 채팅방 참여자 조회 get 요청
// 참여자 조회 인터페이스
interface ChatParticipantData {
  chatRoomId: number;
  senderId: number;
  receiverId: number;
  receiverNickname: string;
  receiverProfileUrl: string;
}

interface ChatParticipantResponse {
  status: number;
  message: string;
  data: ChatParticipantData[];
}

// 채팅방 아이디 불러오기
onMounted(() => {
  console.log(route.params.chatroomId);
  chatRoomId.value = Number(route.params.chatroomId);

});

// 채팅방 참여자 조회
onMounted(async () => {
    try {
      const token = ref(localStorage.getItem('user_token'))
      const ChatParticipant = ref<ChatParticipantData[] | null>(null);
      const response = await axios.get<ChatParticipantResponse>(`${import.meta.env.VITE_API_KEY}/chatroom/${chatRoomId.value}`, {
        headers: {
          'Authorization': token.value?.replace("\"", "")
        }
      });
      if (response.status === 200) {
        ChatParticipant.value = response.data.data;
        console.log(response.data.data)
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


// 채팅방 입장 요청
const joinChatRoom = () => {
  const eventSource = new EventSource(`https://i10a801.p.ssafy.io:8082/chat/connect/${chatRoomId.value}`);

  eventSource.addEventListener('message', (event) => {
    const data: SimpleMessage = JSON.parse(event.data);
    recvList.value.push(data);
  });
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
      userId: 1, 
      nickname: '닉네임', 
      message: newmessage.value.trim() };

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

// const sendMessage = () => {
//   if (newmessage.value.trim() !== '') {
//     const message: SimpleMessage = { type: "TEXT", chatRoomId: Number(chatRoomId.value), userId: 1, nickname:"juhyun", message: newmessage.value.trim() };
//     fetch('https://i10a801.p.ssafy.io:8082/send-message', {
//       method: 'POST',
//       headers: {
//         'Content-Type': 'application/json',
//       },
//       body: JSON.stringify(message),
//     });
//     newmessage.value = '';
//   }
// };
</script>

<style scoped>
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
  background-color: #aaffaa;
}

.other-message {
  float: left;
  clear: both;
  background-color: #ffaaaa;
}
</style>