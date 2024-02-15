<template>
    <div class="container">
    <div class="bg-white shadow rounded-lg p-4 sm:p-6 xl:p-8">
                     <div class="mb-4 flex items-center justify-between">
                        <div>
                           <h3 class="text-xl font-bold text-gray-900 mb-2">판매도서 목록</h3>
                           <span class="text-base font-normal text-gray-500">판매할 도서를 선택하여 추가해주세요</span>
                        </div>
                        <div class="flex-shrink-0">
                           <button v-if="selectedBooks.length > 0" class="text-sm font-medium text-cyan-600 hover:bg-gray-100 rounded-lg p-2" @click="toggleModal">제거</button>
                            <button v-else class="text-sm font-medium text-cyan-600 hover:bg-gray-100 rounded-lg p-2" @click="toggleModal">추가</button>
                          </div>
                     </div>
                     <div class="flex flex-col mt-8">
                        <div class="overflow-x-auto rounded-lg">
                           <div class="align-middle inline-block min-w-full">
                              <div class="shadow overflow-y-auto h-96 sm:rounded-lg">
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
                                          <th scope="col" class="p-4 whitespace-nowrap text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                             판매가격
                                          </th>
                                       </tr>
                                    </thead>
                                    <tbody class="bg-white">
                                       <tr v-for="(book, index) in GetchatRoomTradeBooks" :key="book.id" :class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
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
                                    <tbody class="bg-white">
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
                                    </tbody>
                                 </table>
                              </div>
                           </div>
                        </div>
                     </div>
                     <div class="flex justify-between">
                      <div class="text-lg font-semibold mt-6">총 판매금액 : {{ totalAmount }}원</div>
                      <button @click="completeSell(chatroomId)" class="w-2/5 mt-6 bg-maintheme1 rounded-lg px-4 py-2 text-lg text-white tracking-wide font-semibold font-sans">판매완료</button>
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
            <div class="relative bg-white rounded-lg overflow-hidden shadow-xl max-w-screen-md w-full m-4" x-transition:enter="transition ease-out duration-300 transform opacity-0 scale-95" x-transition:enter-start="opacity-0 scale-95" x-transition:enter-end="opacity-100 scale-100" x-transition:leave="transition ease-in duration-200 transform opacity-100 scale-100" x-transition:leave-start="opacity-100 scale-100" x-transition:leave-end="opacity-0 scale-95" x-cloak>
              <!-- Modal panel -->
              <div class="flex justify-between px-6 py-4">
                <h3 class="text-xl font-semibold text-gray-900 mb-2"> 판매도서 선택 </h3>
                <button @click="toggleModal" class="text-sm font-medium text-cyan-600 hover:bg-gray-100 rounded-lg p-2" >닫기</button>
              </div>
              <div class="prose max-w-screen-md p-6 overflow-y-auto" style="max-height: 70vh; background-color: #fff; border: 1px solid #e2e8f0; border-radius: 0.375rem; box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);">

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
            <th scope="col" class="p-4 whitespace-nowrap text-left text-m font-medium text-gray-500">판매가격</th>
            <th scope="col" class="p-4 whitespace-nowrap text-center text-m font-medium text-gray-500">도서선택</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(book, index) in sellingData.books" :key="book.id" :class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
            <td class="p-4 text-sm font-normal text-gray-900">{{ book.title }}</td>
            <td class="p-4 text-sm font-normal text-gray-900">{{ book.author }}</td>
            <td class="p-4 text-sm font-normal text-gray-900">{{ book.bookIsbn }}</td>
            <td class="p-4 text-sm font-normal text-gray-900">{{ book.userBookPrice }}원</td>
            <td class="p-4 text-sm font-normal text-gray-500">
              <div class="flex justify-center items-center h-full">
              <input type="checkbox" id="myCheckbox" value="" v-model="isChecked[book.id]" @click="toggleCheckbox(book.id)" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              </div></td>
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
                <button @click="sendSelectedBooks" class="w-2/5 bg-maintheme1 rounded-lg px-4 py-2 text-lg text-white tracking-wide font-semibold font-sans">담기완료</button>
              <!-- 모달에서 담기완료를 눌렀을때 선택된 도서들이 리스트로 보여져야한다. -->
              </div>
            </div>
          </div>
        </div>
  </div>


</template>

<script setup lang="ts">
import { onMounted, ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useRoute } from 'vue-router';
import axios from 'axios';
import axiosInstance from '@/services/axios';



const modalOpen = ref(false);

const toggleModal = () => {
  modalOpen.value = !modalOpen.value;
};


const router = useRouter();

const route = useRoute();
const chatroomId = Number(route.params.chatroomId); 

const completeSell = async (chatroomId: number) => {
  try {
      const response = await axiosInstance.value.patch(`${import.meta.env.VITE_API_KEY}/trade/${chatroomId}`);
  
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
      console.log(response, '도서선택완료');
    } else {
      console.error('API 요청 실패:', response.status);
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
};

// 판매금액의 총합
const totalAmount = computed(() => {
  return selectedBooks.value.reduce((sum, book) => sum + book.userBookPrice, 0);
});


const sellingData = ref<ResponseData | null>(null);
const token = ref(localStorage.getItem('user_token'))
onMounted(async () => {
  try {
        const response = await axiosInstance.value.get<ApiResponse>(`${import.meta.env.VITE_API_KEY}/trade/purchase_books`, {
      headers: {
        'Authorization': token.value?.replace("\"", "")
        // 'Content-Type': 'application/json'
      }
    });
    
    if (response.status === 200) {
      sellingData.value = response.data.data;
      // originalBooks.value = [...sellingData.value.books];  // 도서 목록 복사
      console.log(response)
      console.log(sellingData.value);
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


// 판매자한테도 기존에 채팅방에서 선택된 도서 정보를 알려줘야함

interface ChatRoomTradeBooks {
  userId: number;
  id: number;
  title: string;
  author: string;
  status: string;
  price: number | null;
  seriesId: number | null;
}

interface ChatRoomTradeBooksResponse {
  status: number;
  message: string;
  data: {chatRoomTradeBooks : ChatRoomTradeBooks[];}
}  
const GetchatRoomTradeBooks = ref<ChatRoomTradeBooks[]>([]);
//선택된 거래 도서 정보 요청 api
onMounted(async () => {
    try {
      const token = ref(localStorage.getItem('user_token'))
      const response = await axiosInstance.value.get<ChatRoomTradeBooksResponse>(`${import.meta.env.VITE_API_KEY}/chatroom/book_list/${chatroomId}`, {
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

</script>


<style scoped>

.container {
  width: 650px;
  height: 800px;
}


</style>