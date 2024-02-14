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
              <div class="bg-gray-50 shadow rounded-lg mb-8 mr-4 ml-4 mt-4 overflow-auto" ref="chatArea"
                style="width: 400px; height: 500px;">
                <div class="flex flex-col">
                  <div v-for="(message, idx) in chatMessageList" :key="idx">
                    <div v-if="message.userId === Number(userId.id)"
                      class="my-message max-w-[300px] rounded-lg bg-yellow-50">{{ message.message }}</div>
                    <div v-else class="other-message max-w-[300px] rounded-lg bg-green-50">{{ message.message }}</div>
                  </div>
                  <div v-for="(item, idx) in recvList" :key="idx">
                    <div v-if="(item.userId === Number(userId.id))"
                      class="my-message max-w-[300px] rounded-lg bg-yellow-50">
                      <div>{{ item.message }}</div>
                    </div>
                    <div v-else class="other-message max-w-[300px] rounded-lg bg-green-50">
                      {{ item.message }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="flex justify-center">
              <!-- <button class="inline-block rounded bg-maintheme1 h-8 px-2 py-1 text-sm font-medium text-white mr-3">img</button> -->
              <!-- <textarea class="border" style="width: 300px;"></textarea> -->
              <input class="border w-[330px] h-[50px]" v-model="newmessage" type="text" @keyup.enter="sendMessage" />
              <button @click="sendMessage"
                class="inline-block rounded bg-maintheme1 h-8 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2 ml-3 mt-2">전송</button>
            </div>
          </div>
        </div>
        <div class="ml-10">
          <div v-if="ChatParticipant?.tradeReceiverId === Number(userId.id)">
            <div>
              <div class="container">
                <div class="bg-white shadow rounded-lg p-4 sm:p-6 xl:p-8">
                  <div class="mb-4 flex items-center justify-between">
                    <div>
                      <h3 class="text-xl font-bold text-gray-900 mb-2">판매도서 목록</h3>
                      <span class="text-base font-normal text-gray-500">판매할 도서를 선택하여 추가해주세요</span>
                    </div>
                    <div class="flex-shrink-0">
                      <button v-if="selectedBooks.length > 0"
                        class="text-sm font-medium text-cyan-600 hover:bg-gray-100 rounded-lg p-2"
                        @click="toggleModal">수정</button>
                      <button v-else class="text-sm font-medium text-cyan-600 hover:bg-gray-100 rounded-lg p-2"
                        @click="toggleModal">추가</button>
                    </div>
                  </div>
                  <div class="flex flex-col mt-8">
                    <div class="overflow-x-auto rounded-lg">
                      <div class="align-middle inline-block min-w-full">
                        <div class="shadow overflow-y-auto h-96 sm:rounded-lg">
                          <table class="min-w-full divide-y divide-gray-200">
                            <thead class="bg-gray-50">
                              <tr>
                                <th scope="col"
                                  class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                  도서 이름
                                </th>
                                <th scope="col"
                                  class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                  저자
                                </th>
                                <th scope="col"
                                  class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                  ISBN
                                </th>
                                <th scope="col"
                                  class="p-4 whitespace-nowrap text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                  판매가격
                                </th>
                              </tr>
                            </thead>
                            <!-- <tbody class="bg-white">
                                       <tr v-for="(book, index) in GetchatRoomTradeBooks" :key="index" :class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
                                          <td class="p-4 w-auto text-sm font-normal text-gray-900">
                                            {{ book.title }}
                                          </td>
                                          <td class="p-4 w-auto text-sm font-normal text-gray-500">
                                            {{ book.author }}
                                          </td>
                                          <td class="p-4 whitespace-nowrap text-sm font-noraml text-gray-900">
                                          </td>
                                          <td class="p-4 whitespace-nowrap text-sm font-normal text-gray-500">
                                            {{ book.price }}원
                                          </td>
                                       </tr>
                                    </tbody> -->
                            <tbody class="bg-white">
                              <tr v-for="(book, index) in chatRoomTradeBooks" :key="index"
                                :class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
                                <td class="p-4 w-auto text-sm font-normal text-gray-900">
                                  {{ book.title }}
                                </td>
                                <td class="p-4 w-auto text-sm font-normal text-gray-500">
                                  {{ book.author }}
                                </td>
                                <td class="p-4 whitespace-nowrap text-sm font-noraml text-gray-900">
                                </td>
                                <td class="p-4 whitespace-nowrap text-sm font-normal text-gray-500">
                                  {{ book.price }}원
                                </td>
                              </tr>
                            </tbody>
                            <!-- <tbody v-else class="bg-white">
                                       <tr v-for="(book, index) in selectedBooks" :key="book.id" :class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
                                          <td class="p-4 w-auto text-sm font-normal text-gray-900">
                                            {{ book.title }}
                                          </td>
                                          <td class="p-4 w-auto text-sm font-normal text-gray-500">
                                            {{ book.author }}
                                          </td>
                                          <td class="p-4 whitespace-nowrap text-sm font-noraml text-gray-900">
                                            {{ book.bookIsbn }}
                                          </td>
                                          <td class="p-4 whitespace-nowrap text-sm font-normal text-gray-500">
                                            {{ book.userBookPrice }}원
                                          </td>
                                       </tr>
                                    </tbody> -->
                          </table>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="flex justify-between">
                    <div class="text-lg font-semibold mt-6">총 판매금액 : {{ totalAmount }}원</div>
                    <button @click="completeSell(chatroomId)"
                      class="w-2/5 mt-6 bg-maintheme1 rounded-lg px-4 py-2 text-lg text-white tracking-wide font-semibold font-sans">판매완료</button>
                  </div>
                </div>
                <div>
                </div>
              </div>
              <div v-if="modalOpen">

                <!-- component -->
                <div class="font-sans bg-gray-100 flex items-center justify-center h-screen">
                  <div class="fixed z-10 inset-0 flex items-center justify-center">
                    <div class="absolute inset-0 bg-gray-400 opacity-75"></div>
                    <div class="relative bg-white rounded-lg overflow-hidden shadow-xl max-w-screen-md w-full m-4"
                      x-transition:enter="transition ease-out duration-300 transform opacity-0 scale-95"
                      x-transition:enter-start="opacity-0 scale-95" x-transition:enter-end="opacity-100 scale-100"
                      x-transition:leave="transition ease-in duration-200 transform opacity-100 scale-100"
                      x-transition:leave-start="opacity-100 scale-100" x-transition:leave-end="opacity-0 scale-95"
                      x-cloak>
                      <!-- Modal panel -->
                      <div class="flex justify-between px-6 py-4">
                        <h3 class="text-xl font-semibold text-gray-900 mb-2"> 판매도서 선택 </h3>
                        <button @click="toggleModal"
                          class="text-sm font-medium text-cyan-600 hover:bg-gray-100 rounded-lg p-2">닫기</button>
                      </div>
                      <div class="prose max-w-screen-md p-6 overflow-y-auto"
                        style="max-height: 70vh; background-color: #fff; border: 1px solid #e2e8f0; border-radius: 0.375rem; box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);">

                        <div>
                          <!-- <div>판매도서 선택 모달</div> -->
                          <!-- <div>판매할 도서를 목록에서 선택하면 sellingbook 컴포넌트로 선택된 도서 데이터를 보내주어야 함</div> -->
                          <div class="flex justify-center items-start mb-4 overflow-y-auto h-96">
                            <!-- <input type="checkbox" id="myCheckbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"> -->
                            <table v-if="sellingData && sellingData.books && sellingData.books.length > 0">
                              <thead>
                                <tr class="bg-gray-50 ">
                                  <th scope="col" class="p-4 text-left text-m font-medium text-gray-500">도서 제목</th>
                                  <th scope="col" class="p-4 text-left text-m font-medium text-gray-500">저자</th>
                                  <th scope="col" class="p-4 text-left text-m font-medium text-gray-500">ISBN</th>
                                  <th scope="col"
                                    class="p-4 whitespace-nowrap text-left text-m font-medium text-gray-500">판매가격</th>
                                  <th scope="col"
                                    class="p-4 whitespace-nowrap text-center text-m font-medium text-gray-500">도서선택</th>
                                </tr>
                              </thead>
                              <tbody>
                                <tr v-for="(book, index) in sellingData.books" :key="book.id"
                                  :class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
                                  <td class="p-4 text-sm font-normal text-gray-900">{{ book.title }}</td>
                                  <td class="p-4 text-sm font-normal text-gray-900">{{ book.author }}</td>
                                  <td class="p-4 text-sm font-normal text-gray-900">{{ book.bookIsbn }}</td>
                                  <td class="p-4 text-sm font-normal text-gray-900">{{ book.userBookPrice }}원</td>
                                  <td class="p-4 text-sm font-normal text-gray-500">
                                    <div class="flex justify-center items-center h-full">
                                      <input type="checkbox" id="myCheckbox" value="" v-model="isChecked[book.id]"
                                        @click="toggleCheckbox(book.id)"
                                        class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                                    </div>
                                  </td>
                                </tr>
                              </tbody>
                            </table>
                            <div v-else class="text-center py-4">
                              <div class="text-xl font-semibold">판매 가능한 도서가 없습니다.</div>
                              <div class="text-lg mt-5">나의 프로필에서 판매할 도서를 선택하여 도서의 상태를 '판매'로 변경해주세요!</div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="bg-gray-50 px-4 py-3 sm:px-6 flex align-items justify-center p-4 gap-4 flex-row">
                        <button @click="sendSelectedBooks"
                          class="w-2/5 bg-maintheme1 rounded-lg px-4 py-2 text-lg text-white tracking-wide font-semibold font-sans">담기완료</button>
                        <!-- 모달에서 담기완료를 눌렀을때 선택된 도서들이 리스트로 보여져야한다. -->
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- <MySellingBook/> -->
          </div>
          <div v-else>
            <!-- <MyBuyingBook :chatRoomTradeBooks="chatRoomTradeBooks"/> -->
            <div class="container">
              <div class="bg-white shadow rounded-lg p-4 sm:p-6 xl:p-8">
                <div class="mb-4 flex items-center justify-between">
                  <div>
                    <h3 class="text-xl font-bold text-gray-900 mb-2">구매도서 목록</h3>
                    <span class="text-base font-normal text-gray-500">구매할 도서를 확인해보세요</span>
                  </div>
                </div>
                <div class="flex flex-col mt-8">
                  <div class="overflow-x-auto w-[500px] rounded-lg">
                    <div class="align-middle inline-block min-w-full">
                      <div class="shadow overflow-y-auto h-96 sm:rounded-lg">
                        <table class="min-w-full divide-y divide-gray-200">
                          <thead class="bg-gray-50">
                            <tr>
                              <th scope="col"
                                class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                도서 이름
                              </th>
                              <th scope="col"
                                class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                저자
                              </th>
                              <!-- <th scope="col" class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                      ISBN
                                    </th> -->
                              <th scope="col"
                                class="p-4 whitespace-nowrap text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                판매가격
                              </th>
                            </tr>
                          </thead>
                          <tbody class="bg-white">
                            <tr v-for="(book, index) in chatRoomTradeBooks" :key="index"
                              class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
                              <td class="p-4 w-auto text-sm font-normal text-gray-900">
                                {{ book.title }}
                              </td>
                              <td class="p-4 w-auto text-sm font-normal text-gray-500">
                                {{ book.author }}</td>
                              <!-- <td class="p-4 whitespace-nowrap text-sm font-noraml text-gray-900">
                                    {{ }}</td> -->
                              <td class="p-4 whitespace-nowrap text-sm font-normal text-gray-500">
                                {{ book.price }}</td>
                            </tr>
                          </tbody>

                          <!-- <tbody v-else class="bg-white">
                                <tr v-for="(book, index) in GetchatRoomTradeBooks" :key="index" class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
                                    <td class="p-4 w-auto text-sm font-normal text-gray-900">
                                  {{ book.title }}  
                                  </td>
                                    <td class="p-4 w-auto text-sm font-normal text-gray-500">
                                    {{ book.author }}</td>
                                    <td class="p-4 whitespace-nowrap text-sm font-normal text-gray-500">
                                    {{ book.price }}</td>
                                </tr>
                              </tbody> -->
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="flex justify-between">
                  <div class="text-lg font-semibold mt-6">총 구매금액 : {{ totalAmount }} 원</div>
                </div>
              </div>
              <div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, defineExpose, nextTick, computed, watch, reactive, onUpdated } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import axiosInstance from '@/services/axios';
import MySellingBook from './MySellingBook.vue';
import MyBuyingBook from './MyBuyingBook.vue';
import type { Ref } from 'vue';

import { useRouter } from 'vue-router';

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
  chatRoomId: number;
  userId: number;
  nickname: string;
  message: string;
}
// const route = useRoute();

const chatRoomId = ref<number | null>(null);


// const chatData = ref(chatStore.$state.chatData);
// const chatRoomId = ref<number | null>(null);
// const chatRoomId = Number(ref(route.params.chatroomId))
const newmessage = ref('');
const recvList = ref<SimpleMessage[]>([]);
// watch나 computed를 사용해서 바뀌는게 적용되도록
const chatRoomTradeBooks = ref<ChatRoomTradeBooks[]>([]);

const chatArea: Ref<HTMLDivElement | null> = ref(null);


// 채팅방 입장 요청
const joinChatRoom = () => {
  const eventSource = new EventSource(`https://i10a801.p.ssafy.io:8082/chat/connect/${chatRoomId.value}`);

  eventSource.addEventListener('message', (event) => {
    const data: SimpleMessage = JSON.parse(event.data);
    recvList.value.push(data);
    console.log(data)

    nextTick(() => {
      if (chatArea.value) {
        chatArea.value.scrollTop = chatArea.value.scrollHeight;
      }
    });
  });

  // 거래 도서에 대한 정보 이벤트
  // 
  eventSource.addEventListener('change_trade_book_list', (event) => {
    const data: { chatRoomTradeBooks: ChatRoomTradeBooks[] } = JSON.parse(event.data);
    chatRoomTradeBooks.value = [...data.chatRoomTradeBooks];  // Spread 연산자를 사용하여 두 배열을 병합
    console.log(data);
  });


  // 거래 도서에 대한 정보 이벤트
  // eventSource.addEventListener('change_trade_book_list', (event) => {
  //   const data: ChatRoomTradeBooks = JSON.parse(event.data)
  //   chatRoomTradeBooks.value.push(data)
  //   console.log(data)
  // })
};

onMounted(() => {
  joinChatRoom();
});

// get배열을 초기화하기 위한 코드
// watch(chatRoomTradeBooks, (newVal, oldVal) => {
//  // console.log("event not change");

//   if ( newVal.length > 0) { 
//     console.log("event change");

//     GetchatRoomTradeBooks.value = []; 
//     console.log("get 초기화");
//     console.log(GetchatRoomTradeBooks.value.length);


//   }
// }, { deep: true });

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
  data: { chatMessageList: ChatMessageList[]; };
  // data: ChatMessageList;
  // data: ChatMessageList[];
}

interface ChatMessageList {
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


//   interface ChatRoomTradeBooksResponse {
//     status: number;
//     message: string;
//     data: {
//     chatRoomTradeBooks: ChatRoomTradeBooks[];
//   };}
interface ChatRoomTradeBooksResponse {
  status: number;
  message: string;
  data: { chatRoomTradeBooks: ChatRoomTradeBooks[]; }
}
const GetchatRoomTradeBooks = ref<ChatRoomTradeBooks[]>([]);
//선택된 거래 도서 정보 요청 api
onMounted(async () => {
  try {
    const token = ref(localStorage.getItem('user_token'))
    const response = await axiosInstance.value.get<ChatRoomTradeBooksResponse>(`https://i10a801.p.ssafy.io:8082/chatroom/book_list/${chatRoomId.value}`, {
      headers: {
        'Authorization': token.value?.replace("\"", "")
      }
    });
    if (response.status === 200) {
      GetchatRoomTradeBooks.value = response.data.data.chatRoomTradeBooks;
      console.log('get', GetchatRoomTradeBooks.value)
    } else {
      console.error('API 요청 실패:', response.status);
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
});

const totalAmount = computed(() => {
  return chatRoomTradeBooks.value.reduce((sum, book) => sum + (book.price || 0), 0);
});

// mySellingBook 컴포넌트의 스크립트
const modalOpen = ref(false);

const toggleModal = () => {
  modalOpen.value = !modalOpen.value;
};


const router = useRouter();

const route = useRoute();
const chatroomId = Number(route.params.chatroomId);

const completeSell = async (chatroomId: number) => {
  try {
    const response = await axiosInstance.value.patch(`https://i10a801.p.ssafy.io:8082/trade/${chatRoomId.value}`);

    if (response.status === 200) {
      console.log('거래가 완료되었습니다:', response.data);
      alert('판매가 완료되었습니다. \n프로필에서 판매내역을 확인할 수 있습니다.')
    } else {
      console.error('API 요청 실패:', response.status);
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    console.log(chatroomId)
    console.log(route.params.chatroomId);

  }

};

const originalBooks = ref<Book[]>([]);
// 재작성
const selectedBooks = ref<Book[]>([]);
const isChecked = ref<Record<number, boolean>>({});
const toggleCheckbox = (bookId: number) => {
  // const foundBook = sellingData.value?.books.find(book => book.id === bookId) 
  //             || sellingData.value?.seriesBooks.books.find(book => book.id === bookId);
  const foundBook = sellingData.value?.books.find(book => book.id === bookId);
  if (foundBook) {
    originalBooks.value = [...selectedBooks.value];
    isChecked.value[bookId] = !isChecked.value[bookId];

    if (isChecked.value[bookId]) {
      // 체크된 경우에는 배열에 추가
      if (!selectedBooks.value.includes(foundBook)) {
        selectedBooks.value.push(foundBook);
      }
    } else {
      // 체크가 해제된 경우에는 배열에서 제거
      selectedBooks.value = selectedBooks.value.filter(book => book.id !== bookId);
    }
  }
};


const sendSelectedBooks = async () => {
  console.log('Selected Books:', selectedBooks.value);
  if (sellingData?.value) {
    toggleModal();
  }

  const addedBooks = selectedBooks.value.filter(book => !originalBooks.value.includes(book));
  const deletedBooks = originalBooks.value.filter(book => !selectedBooks.value.includes(book));

  const data: UpdatedBookData = {
    chatRoomId: chatroomId,  // chatroomId 사용
    userId: Number(sellingData.value?.userId),  // userId 사용
    addedBooks: addedBooks.map(book => ({ id: book.id, bookIsbn: Number(book.bookIsbn) })),
    deletedBooks: deletedBooks.map(book => ({ id: book.id, bookIsbn: Number(book.bookIsbn) })),
  };

  await chooseBook(data);
};

// 선택된 도서의 목록에 대한 POST 요청(선택된 도서들의 id)
export interface UpdatedBookData {
  chatRoomId: number;
  userId: number;
  addedBooks: AddedBooks[];
  deletedBooks: Deletedbooks[];
}

export interface AddedBooks {
  id: number;
  bookIsbn: number;
}

export interface Deletedbooks {
  id: number;
  bookIsbn: number;
}

export interface UpdatedBookDataResponse {
  status: number;
  message: string;
  data: UpdatedBookData;
}


const chooseBook = async (data: UpdatedBookData) => {
  try {
    const response = await axiosInstance.value.post<UpdatedBookDataResponse>(
      'https://i10a801.p.ssafy.io:8082/trade',
      data,
      {
        headers: {
          'Authorization': token.value?.replace("\"", "")
        }
      }
    );

    if (response.status === 200) {
      console.log(data)
      console.log(response, '도서선택완료');
    } else {
      console.error('API 요청 실패:', response.status);
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
};

// 판매금액의 총합
// const totalAmount = computed(() => {
//   return selectedBooks.value.reduce((sum, book) => sum + book.userBookPrice, 0);
// });


const sellingData = ref<ResponseData | null>(null);
const token = ref(localStorage.getItem('user_token'))
onMounted(async () => {
  try {
    const response = await axiosInstance.value.get<ApiResponse>(`https://i10a801.p.ssafy.io:8082/trade/purchase_books`, {
      headers: {
        'Authorization': token.value?.replace("\"", "")
        // 'Content-Type': 'application/json'
      }
    });

    if (response.status === 200) {
      sellingData.value = response.data.data;
      // originalBooks.value = [...sellingData.value.books];  // 도서 목록 복사
      console.log(response)
      console.log('sellingData', sellingData.value);
    } else {
      console.error('API 요청 실패:', response.status);
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
});


//판매가능사용자도서목록 api
interface Book {
  id: number;
  bookIsbn: string;
  title: string;
  author: string;
  isOpened: number;
  isOwned: number;
  userBookTradeType: string;
  seriesId: number | null;
  seriesName: string | null;
  userBookPrice: number;
}

interface ResponseData {
  userId: number;
  books: Book[];
}

interface ApiResponse {
  status: number;
  messsage: string;
  data: ResponseData;
}


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

.friend-nickname {
  /* 프로필명(이름) 스타일 조정 */
  font-weight: bold;
  font-size: 0.875rem;
  /* 16 브라우저 기준 14px */
}

.profile-img {
  width: 3.125rem;
}

.chat-content {
  /* 메시지(채팅) 내용 스타일 조정 */
  color: gray;
  font-size: 0.75rem;
  /* 16 브라우저 기준 12px */
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
}</style>
