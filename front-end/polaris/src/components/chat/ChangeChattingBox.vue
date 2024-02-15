<template>
  <Navvar></Navvar>
  <div class="first-container mt-40">
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
              <div class="bg-gray-50 shadow rounded-lg mb-8 mr-4 ml-4 mt-4 overflow-y-auto" ref="chatArea"
                style="width: 400px; height: 500px; overflow-x: hidden">
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
              <input class="border w-[330px] h-[50px]" v-model="newmessage" type="text" @keyup.enter="sendMessage" />
              <button @click="sendMessage"
                class="inline-block rounded bg-maintheme1 h-8 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2 ml-3 mt-2">전송</button>
            </div>
          </div>
        </div>
        <div class="list-container ml-10 max-w-[500px]">
          <div class="container">
            <div class="book-list bg-white shadow rounded-lg p-4 sm:p-6 xl:p-8">
              <div class="book-list-header mb-4 flex items-center justify-between">
                <div>
                  <h3 class="text-xl font-bold text-gray-900 mb-2">교환받을 도서목록</h3>
                  <span class="text-base font-normal text-gray-500">상대에게 받을 도서의 목록입니다.</span>
                </div>
              </div>
              <div class="book-list-content flex flex-col mt-8">
                <div class="overflow-x-auto rounded-lg">
                  <div class="book-table align-middle inline-block min-w-full">
                    <div class="shadow overflow-y-auto h-72 sm:rounded-lg">
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
                          </tr>
                        </thead>
                        <!-- <div>Getchat이 아니라 이벤트로 불러온애들을 보여줘야함.</div> -->
                        <!-- 지금 로그인된 사람한테는 이게 보이면 안됨 -->
                        <tbody class="bg-white">
                          <tr v-for="(book, index) in yourBooks" :key="index"
                            class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
                            <td
                              class="p-4 w-auto text-sm font-normal text-gray-900">
                              {{ book.title }}
                            </td>
                            <td
                              class="p-4 w-auto text-sm font-normal text-gray-500">
                              {{ book.author }}</td>
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
            <!-- <MyChangingBook/> -->
            <div>
              <div class="container">
                <div class="bg-white shadow rounded-lg p-4 sm:p-6 xl:p-8">
                  <div class="mb-4 flex items-center justify-between">
                    <div>
                      <h3 class="text-xl font-bold text-gray-900 mb-2">교환할 도서 목록</h3>
                      <span class="text-base font-normal text-gray-500">상대방에게 줄 도서를 선택하여 추가해주세요</span>
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
                        <div class="shadow overflow-y-hidden h-96 sm:rounded-lg">
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

                              </tr>
                            </thead>
                            <!-- 여기에 조건을 추가. 내것만 보여야 함 -->
                            <tbody class="bg-white">
                              <tr v-for="(book, index) in myBooks" :key="book.id"
                                :class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
                                <td class="p-4 w-auto text-sm font-normal text-gray-900">
                                  {{ book.title }}
                                </td>
                                <td class="p-4 w-auto text-sm font-normal text-gray-500">
                                  {{ book.author }}
                                </td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="change-condition">
                    <div class="flex">
                      <div>
                        <div v-if="light === 'red'" class="bg-red-700 w-4 h-4 rounded-full text-white">.</div>
                        <div v-if="light === 'green'" class="bg-green-500 w-4 h-4 rounded-full text-white">.</div>
                      </div>
                      <button v-if="tradeStatus === 'default' || tradeStatus === 'editing'" @click="confirmClick"
                        class="inline-block rounded bg-maintheme1 px-4 py-2 ml-2 text-xs font-medium text-white hover:bg-maintheme2">거래확인</button>
                      <button v-if="tradeStatus === 'confirmed'" @click="editClick"
                        class="inline-block rounded bg-maintheme1 px-4 py-2 ml-2 mr-2 text-xs font-medium text-white hover:bg-maintheme2">거래수정</button>
                      <button v-if="tradeStatus === 'confirmed'" @click="completeClick"
                        class="inline-block rounded bg-maintheme1 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2">거래완료</button>
                    </div>
                  </div>
                </div>
                <div>
                </div>
              </div>
              <div v-if="modalOpen">
                <!-- component -->
                <div class="font-sans bg-gray-100 flex items-center justify-center h-screen">
                  <div class="fixed z-10 inset-0 flex items-center justify-center">
                    <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
                    <div class="relative bg-white rounded-lg overflow-hidden shadow-xl max-w-screen-md w-full m-4"
                      x-transition:enter="transition ease-out duration-300 transform opacity-0 scale-95"
                      x-transition:enter-start="opacity-0 scale-95" x-transition:enter-end="opacity-100 scale-100"
                      x-transition:leave="transition ease-in duration-200 transform opacity-100 scale-100"
                      x-transition:leave-start="opacity-100 scale-100" x-transition:leave-end="opacity-0 scale-95"
                      x-cloak>
                      <!-- Modal panel -->
                      <div class="flex justify-between px-6 py-4">
                        <h3 class="text-lg leading-6 font-medium text-gray-900"> 교환도서 선택 </h3>
                        <button @click="toggleModal" class="text-blue-500">닫기</button>
                      </div>
                      <div class="prose max-w-screen-md p-6 overflow-y-auto"
                        style="max-height: 70vh; background-color: #fff; border: 1px solid #e2e8f0; border-radius: 0.375rem; box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);">

                        <div>
                          <!-- <div>판매도서 선택 모달</div> -->
                          <!-- <div>판매할 도서를 목록에서 선택하면 sellingbook 컴포넌트로 선택된 도서 데이터를 보내주어야 함</div> -->
                          <div class="flex justify-center items-center mb-4">
                            <!-- <input type="checkbox" id="myCheckbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"> -->
                            <table v-if="changingData && changingData.books && changingData.books.length > 0">
                              <thead>
                                <tr>
                                  <th class="p-4 border border-gray-300">도서 제목</th>
                                  <th class="p-4 border border-gray-300">저자</th>
                                  <th class="p-4 border border-gray-300">ISBN</th>
                                  <th class="p-4 border border-gray-300">도서선택</th>
                                </tr>
                              </thead>
                              <tbody>
                                <SecondModalItem v-for="(book) in changingData.books" 
                                :key="book.id" 
                                :secondbook="book" 
                                @emit="ControllTradeBookList" />
                                <!-- <tr v-for="book in changingData.books" :key="book.id">
                                  <td class="pr-4 pl-4 border border-gray-300 text-right">{{ book.title }}</td>
                                  <td class="pr-4 pl-4 border border-gray-300 text-right">{{ book.author }}</td>
                                  <td class="pr-4 pl-4 border border-gray-300 text-right">{{ book.bookIsbn }}</td>

                                  <td class="pr-4 pl-4 border border-gray-300 text-right">
                                    <div class="flex justify-center items-center h-full">
                                      <input type="checkbox" id="myCheckbox" value="" v-model="isChecked[book.id]"
                                        @click="toggleCheckbox(book.id)"
                                        class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                                    </div>
                                  </td>
                                </tr> -->
                              </tbody>
                            </table>
                            <div v-else class="text-center py-4">
                              <div class="text-xl font-semibold">교환 가능한 도서가 없습니다.</div>
                              <div class="text-lg mt-5">나의 프로필에서 교환할 도서를 선택하여 도서의 상태를 '교환'으로 변경해주세요!</div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="bg-gray-50 px-4 py-3 sm:px-6 flex align-items justify-end p-4 gap-4 flex-row">
                        <button @click="submit"
                          class="inline-block rounded bg-maintheme1 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2">담기완료</button>
                        <!-- 모달에서 담기완료를 눌렀을때 선택된 도서들이 리스트로 보여져야한다. -->
                      </div>
                    </div>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>
  
<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import MyChangingBook from './MyChangingBook.vue';
import { watch, ref, onMounted, defineExpose, computed, nextTick } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import axiosInstance from '@/services/axios';
import type { Ref } from 'vue';
import { useRouter } from 'vue-router';
import SecondModalItem from './SecondModalItem.vue';

// 상태표시등의 상태와 상태버튼 연동
// 상태표시등은 서로 배타적(동시에 뜨는 경우 없음)
// 빨간불일 경우 거래확인 버튼이 보여야 함
// 파란불일 경우 거래수정과 거래완료 버튼이 보여야 함
// 거래완료 버튼 클릭시, 담긴 교환도서를 저장
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


const ChatParticipant = ref<ChatParticipantData | null>(null);
// const senderId = ref<number|null>(null);

// 채팅방 참여자 조회
onMounted(async () => {
  try {
    console.log(route.params.chatroomId);
    chatRoomId.value = Number(route.params.chatroomId);

    const token = ref(localStorage.getItem('user_token'))
    const response = await axiosInstance.value.get<ChatParticipantResponse>(`${import.meta.env.VITE_API_KEY}/chatroom/${chatRoomId.value}`, {
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

const route = useRoute();
const chatRoomId = ref<number | null>(null);
const newmessage = ref('');
const recvList = ref<SimpleMessage[]>([]);
const chatRoomTradeBooks = ref<ChatRoomTradeBooks[]>([]);
const chatArea: Ref<HTMLDivElement | null> = ref(null);

//이벤트 관련 변수
const checkEvent = ref<boolean>(false);

// 채팅방 입장 요청
const joinChatRoom = () => {
  const eventSource = new EventSource(`${import.meta.env.VITE_API_KEY}/chat/connect/${chatRoomId.value}`);

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

  eventSource.addEventListener('change_trade_book_list', (event) => {
    const data: { chatRoomTradeBooks: ChatRoomTradeBooks[] } = JSON.parse(event.data);
    chatRoomTradeBooks.value = [...data.chatRoomTradeBooks];  // Spread 연산자를 사용하여 두 배열을 병합
    realtimeBooks.value = chatRoomTradeBooks.value
    const mine =  ref<ChatRoomTradeBooks[]>([]);
    const yours = ref<ChatRoomTradeBooks[]>([]);
    realtimeBooks.value.forEach((book)=> {
        if(book.userId == Number(userId.id)){
          mine.value.push(book)
        } else {
          yours.value.push(book)
        }
      })
      myBooks.value = mine.value
      yourBooks.value = yours.value
    console.log(data);
    checkEvent.value = true;
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
      userId: Number(userId.id),
      nickname: 'nickname',
      message: newmessage.value.trim(),
    };

    fetch(`${import.meta.env.VITE_API_KEY}/chat/send_message`, {
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
    const response = await axiosInstance.value.get<ChatMessageListResponse>(`${import.meta.env.VITE_API_KEY}/chatroom/message/${chatRoomId.value}`, {
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

//추가되는 부분
const myBooks = ref<ChatRoomTradeBooks[]>([]);
const yourBooks = ref<ChatRoomTradeBooks[]>([]);


const getTradeBookData = async () => {
  try {
    const token = ref(localStorage.getItem('user_token'))
    const response = await axiosInstance.value.get<ChatRoomTradeBooksResponse>(`${import.meta.env.VITE_API_KEY}/chatroom/book_list/${chatRoomId.value}`, {
      headers: {
        'Authorization': token.value?.replace("\"", "")
      }
    });
    if (response.status === 200) {
      GetchatRoomTradeBooks.value = response.data.data.chatRoomTradeBooks;
      // og에 get 데이터를 저장
      originalBooks.value = GetchatRoomTradeBooks.value
      myBooks.value = []
      yourBooks.value = []
      originalBooks.value.forEach((book)=> {
        if(book.userId == Number(userId.id)){
          myBooks.value.push(book)
        } else {
          yourBooks.value.push(book)
        }
      })

      console.log('get', GetchatRoomTradeBooks.value)
      console.log('내책:',myBooks.value)
      console.log('너책:',yourBooks.value)
    } else {
      console.error('API 요청 실패:', response.status);
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
}



// 거래도서목록 불러오기
interface ChatRoomTradeBooksResponse {
  status: number;
  message: string;
  data: { chatRoomTradeBooks: ChatRoomTradeBooks[]; }
}

const originalBooks = ref<ChatRoomTradeBooks[]>([]);
const realtimeBooks = ref<ChatRoomTradeBooks[]>([]);
const GetchatRoomTradeBooks = ref<ChatRoomTradeBooks[]>([]);


//mychangingbook 컴포넌트 스크립트
const modalOpen = ref(false);

const toggleModal = () => {
  modalOpen.value = !modalOpen.value;
  TradeBookList.value = []
};

interface NetBookData {
  id: number;
  bookIsbn: string;
}

const selectedBooks = ref<Book[]>([]);
const TradeBookList = ref<NetBookData[]>([]);

const ControllTradeBookList = (id: number, isbn: string, isTraded: boolean) => {
  console.log(isTraded)
  const newData = {
    id: id,
    bookIsbn: isbn
  }
  if (isTraded == true) {
      TradeBookList.value.push(newData)
      console.log('담겨라!',TradeBookList.value)
  }
  else {
      TradeBookList.value = TradeBookList.value.filter((book) => book['id'] !== id)
  }
  console.log('현재 담긴 도서:',TradeBookList.value)
}


export interface UpdatedBookData {
  chatRoomId: number;
  userId: number;
  addedBooks: NetBookData[];
  deletedBooks: NetBookData[];
}

export interface UpdatedBookDataResponse {
  status: number;
  message: string;
  data: UpdatedBookData;
}


const addedBooks = ref<NetBookData[]>([]);
const deletedBooks = ref<NetBookData[]>([]);

const submit = async () => {
  console.log('채팅방:',myBooks.value)
  console.log('모달:', TradeBookList.value)
  try {
    addedBooks.value = [];
    deletedBooks.value = [];
    console.log(myBooks.value);
    // 1. original(채팅목록)을 foreach를 돌려서 TradeBookLIst(모달) 목록에 없으면, deleted
    myBooks.value.forEach((book) => {
      const isIncluded = ref(false)
      TradeBookList.value.forEach((tradebook) => {
        if (tradebook.id === book.id) {
          isIncluded.value = true
        }
      })
      if (!isIncluded.value) {
        deletedBooks.value.push({ id: book.id, bookIsbn: "" })
      }
    })
    // 2. selected(모달) foreach 돌려서 original에 없으면, added
    TradeBookList.value.forEach((book) => {
      const isIncluded = ref(false)
      myBooks.value.forEach((tradebook) => {
        if (tradebook.id === book.id) {
          isIncluded.value = true
        }
      })
      if (!isIncluded.value) {
        addedBooks.value.push({ id: book.id, bookIsbn: book.bookIsbn })
      }
    })

    const data: UpdatedBookData = {
      chatRoomId: chatroomId,  // chatroomId 사용
      userId: Number(changingData.value?.userId),  // userId 사용
      addedBooks: addedBooks.value,
      deletedBooks: deletedBooks.value,
    };
    console.log(data)
    const response = await axiosInstance.value.post<UpdatedBookDataResponse>(
      `${import.meta.env.VITE_API_KEY}/trade`,
      data,
      {
        headers: {
          'Authorization': token.value?.replace("\"", "")
        }
      }
    );

    if (response.status === 200) {
      console.log(data)
      console.log(response, '도서선택완료')
      toggleModal();
      getTradeBookData();
    } else {
      console.error('API 요청 실패:', response.status);
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
};


const changingData = ref<ResponseData | null>(null);
const token = ref(localStorage.getItem('user_token'))

onMounted(async () => {
  try {
    const response = await axiosInstance.value.get<ApiResponse>(`${import.meta.env.VITE_API_KEY}/trade/exchange_books`, {
      headers: {
        'Authorization': token.value?.replace("\"", ""),
        // 'Content-Type': 'application/json'
      }
    });
    if (response.status === 200) {
      changingData.value = response.data.data;
      console.log(response)
      console.log(changingData.value);
    } else {
      console.error('API 요청 실패:', response.status);
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
});

onMounted(() => {
  getTradeBookData();
})

//api 수정 후
export interface Book {
  id: number;
  bookIsbn: string;
  title: string;
  author: string;
  isOpened: number;
  isOwned: number;
  userBookTradeType: string;
  seriesId: number | null;
  seriesName: string | null;
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


// 거래상태 표시등 관련 코드
const tradeStatus = ref('default');
const light = ref('defalut')

const confirmClick = () => {
  tradeStatus.value = 'confirmed';
  light.value = 'green'
}
const editClick = () => {
  tradeStatus.value = 'editing';
  light.value = 'red'
}
const completeClick = () => {
  tradeStatus.value = 'completed';
  light.value = 'defalut'
  completeChange(chatroomId);

}
// const route = useRoute();
const chatroomId = Number(route.params.chatroomId);

const completeChange = async (chatroomId: number) => {
  try {
    const response = await axiosInstance.value.patch(`${import.meta.env.VITE_API_KEY}/trade/${chatroomId}`);
    if (response.status === 200) {
      console.log('거래가 완료되었습니다:', response.data);
      alert('교환이 완료되었습니다. \n프로필에서 교환내역을 확인할 수 있습니다.')
    } else {
      console.error('API 요청 실패:', response.status);
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    console.log(chatroomId)
    console.log(route.params.chatroomId);

  }

};

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


.box2 {
  width: 600px;
  height: auto;
  border: 1px solid lightgrey;

}


.select-book {
  position: absolute;
  right: 0;
  margin-right: 10px;
}

.change-condition {
  display: flex;
  position: relative;

}

.list {
  border: 1px solid lightgray;
  height: 163px;
}

.button-div {
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
}</style>