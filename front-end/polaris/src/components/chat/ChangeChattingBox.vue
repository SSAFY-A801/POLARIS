<template>
  <div class="first-container">
    <div class="content">
  <div class="text-2xl m-3 font-semibold">도서교환을 위한 채팅공간</div>  
  <div class="chatcontainer">
  <div class="flex">
  <div class="bg-white shadow rounded-lg p-4 ml-4" style="height: 700px;">
  <div>
    <h3 class="text-xl font-bold text-gray-900 mb-2">Chat Room</h3>
    <span class="text-base font-normal text-gray-500">상대방과 이야기하여 거래도서를 선택해보세요</span>
  </div>
  <div>
    <div class="bg-gray-50 shadow rounded-lg mb-8 mr-4 ml-4 mt-4 overflow-auto" style="width: 400px; height: 500px;">
      <div v-for="(message, idx) in chatMessageList" :key="idx">
      <div>{{ message.message }}</div>
      </div>
      <div v-for="(item, idx) in recvList" :key="idx">
        <!-- <h4>{{item.nickname}}</h4> -->
        <div v-if="item.userId === Number(userId.id)" class="my-message max-w-[300px] rounded-lg bg-yellow-50">
         <div>{{ item.message }}</div>
        </div>
        <!-- <div v-else class="other-message max-w-[300px] rounded-lg bg-green-50">
          <div v-for="participant in ChatParticipant" :key="participant.chatRoomId">
          <div>여기에 닉네임이랑 프로필</div>
          </div>
          <div>{{ item.message }}</div>
        </div> -->
        <div v-else class="other-message max-w-[300px] rounded-lg bg-green-50">
  <div v-for="participant in ChatParticipant" :key="participant.chatRoomId">
    <!-- <div v-if="participant.receiverId !== item.userId">{{ participant.receiverNickname }}</div> -->
    <!-- <img v-if="participant.receiverId !== item.userId" :src="participant.receiverProfileUrl" alt="Profile" /> -->
  </div>
  <div>{{ item.message }}</div>
</div>

      </div>
      <!-- <ConnectSocket/> -->
     <!-- <StartChatting/> -->
     <!-- <StartChat/> -->
    </div>
  </div>
  <!-- <div>
    <div class="bg-gray-50 shadow rounded-lg mb-8 mr-4 ml-4 mt-4" style="width: 400px; height: 500px;">
      <div>대화를 나눠보자</div>
      
    </div>
  </div> -->
  <div class="flex justify-center">
      <input class="border w-[330px] h-[50px]" v-model="newmessage" type="text" @keyup.enter="sendMessage" />
      <button @click="sendMessage" class="inline-block rounded bg-maintheme1 h-8 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2 ml-3 mt-2">전송</button>
    </div>
  </div>
</div>
<div class="list-container ml-10 max-w-[500px]">
  <div class="container">
    <div class="book-list bg-white shadow rounded-lg p-4 sm:p-6 xl:p-8">
      <div class="book-list-header mb-4 flex items-center justify-between">
        <div>
          <h3 class="text-xl font-bold text-gray-900 mb-2">교환받을 도서목록</h3>
          <span class="text-base font-normal text-gray-500">내가 빌릴 도서의 목록입니다.</span>
        </div>
      </div>
      <div class="book-list-content flex flex-col mt-8">
        <div class="overflow-x-auto rounded-lg">
          <div class="book-table align-middle inline-block min-w-full">
            <div class="shadow overflow-y-auto h-72 sm:rounded-lg">
              <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th scope="col" class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                      도서 이름
                    </th>
                    <th scope="col" class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                      저자
                    </th>
                    <th scope="col" class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                      ISBN
                    </th>
                  </tr>
                </thead>
                <tbody class="bg-white">
                  <tr>
                    <td class="p-4 w-auto text-sm font-normal text-gray-900"></td>
                    <td class="p-4 w-auto text-sm font-normal text-gray-500"></td>
                    <td class="p-4 whitespace-nowrap text-sm font-normal text-gray-900"></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="my-changing-book-container">
    <MyChangingBook/>
  </div>
</div>

</div>
</div></div>
  </template>
  
  <script setup lang="ts">
  import MyChangingBook from './MyChangingBook.vue';
  import { ref, onMounted, defineExpose, computed } from 'vue';
  import { useRoute } from 'vue-router';
  import axios from 'axios';
  import axiosInstance from '@/services/axios';
  // 상태표시등의 상태와 상태버튼 연동
  // 상태표시등은 서로 배타적(동시에 뜨는 경우 없음)
  // 빨간불일 경우 거래확인 버튼이 보여야 함
  // 파란불일 경우 거래수정과 거래완료 버튼이 보여야 함
  // 거래완료 버튼 클릭시, 담긴 교환도서를 저장

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

const ChatParticipant = ref<ChatParticipantData[] | null>(null);
// 채팅방 참여자 조회
onMounted(async () => {
    try {
      const token = ref(localStorage.getItem('user_token'))
      const response = await axiosInstance.value.get<ChatParticipantResponse>(`https://i10a801.p.ssafy.io:8082/chatroom/${chatRoomId.value}`, {
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

// 수정된 도서 정보를 가져오기??? 
// const getChatRoomTradeBooks = async () => {
//   try {
//     const response = await axiosInstance.value.get('https://i10a801.p.ssafy.io:8082/chat/get_chatRoomTradeBooks', {

//     });

//     if (response.status === 200) {
//       chatRoomTradeBooks.value = response.data.chatRoomTradeBooks;
//       console.log(chatRoomTradeBooks.value)
//     } else {
//       console.error('API 요청 실패:', response.status);
//     }
//   } catch (error) {
//     console.error('API 요청 중 오류 발생:', error);
//   }
// };



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
}

interface ChatMessageList{
  type: string;
  chatRoomId: number;
  userId: number;
  // createAt: Date;
  createAt: string;
  message: string;
}
const chatMessageList = ref<ChatMessageList[] | null>(null);
// const chatMessageList = ref<ChatMessageList[]>([]);

onMounted(async () => {
    try {
      const token = ref(localStorage.getItem('user_token'))
      const response = await axiosInstance.value.get<ChatMessageListResponse>(`https://i10a801.p.ssafy.io:8082/chatroom/message/${chatRoomId.value}`, {
        headers: {
          'Authorization': token.value?.replace("\"", "")
        }
      });
      if (response.status === 200) {
        console.log(response.data.data)
        // chatMessageList.value = response.data.data['chatMessageList'];
        // chatMessageList.value = (response.data.data as { chatMessageList: ChatMessageList[] })['chatMessageList'];
        chatMessageList.value = response.data.data.chatMessageList
        // chatMessageList.value = [...response.data.data];
        // chatMessageList.value = response.data.data;
        console.log('기존메시지', chatMessageList.value);

      } else {
        console.error('API 요청 실패:', response.status);
      }
    } catch (error) {
      console.error('API 요청 중 오류 발생:', error);
    }
  });

  </script>
  
  <style scoped>
  .chatbox {
    width: 600px; 
    height: 600px;
    border: 1px solid lightgrey;
    border-radius: 2%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  .chatheader {
    text-align: center;
  
  }

  .input {
  background-color: rgb(246, 244, 221);
  border-top: 1px solid lightgray;
  display: flex;
  padding: 5px;
  }
  
  .message {
  width: 85%;
  }

  .chatcontainer {
    display: flex;
  }

  /* .listcontainer {
    display: flex;
    flex-direction: column;
  } */


  .box2{
    width: 600px;
    height: auto;
    border: 1px solid lightgrey;

  }


  .select-book{
    position: absolute;
    right: 0;
    margin-right: 10px;
  }
  
  .change-condition {
    background-color: blanchedalmond;
    display: flex;
    position: relative;

  }

  .list{
    border: 1px solid lightgray;
    height: 163px;
  }

  .button-div{
    position: absolute;
    right: 0;
    margin-right: 10px;
  }

  .container {
    max-height: 300px;
    overflow-y: auto;
    padding-bottom: 15px;
  }
  
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