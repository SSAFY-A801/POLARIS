<template>
  <div class="container">
    <div class="bg-white shadow rounded-lg p-4 sm:p-6 xl:p-8">
      <div class="mb-4 flex items-center justify-between">
        <div>
          <h3 class="text-xl font-bold text-gray-900 mb-2">교환할 도서 목록</h3>
          <span class="text-base font-normal text-gray-500">상대방에게 빌려줄 도서를 선택하여 추가해주세요</span>
        </div>
        <div class="flex-shrink-0">
          <button v-if="selectedBooks.length > 0"
            class="text-sm font-medium text-cyan-600 hover:bg-gray-100 rounded-lg p-2" @click="toggleModal">제거</button>
          <button v-else class="text-sm font-medium text-cyan-600 hover:bg-gray-100 rounded-lg p-2"
            @click="toggleModal">추가</button>
        </div>
      </div>
      <div class="flex flex-col mt-8">
        <div class="overflow-x-auto rounded-lg">
          <div class="align-middle inline-block min-w-full">
            <div class="shadow overflow-y-auto h-96 sm:rounded-lg max-h-[208px]">
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
                  <tr v-for="(book, index) in selectedBooks" :key="book.id"
                    :class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
                    <td class="p-4 w-auto text-sm font-normal text-gray-900">
                      {{ book.title }}
                    </td>
                    <td class="p-4 w-auto text-sm font-normal text-gray-500">
                      {{ book.author }}
                    </td>
                    <td class="p-4 whitespace-nowrap text-sm font-noraml text-gray-900">
                      {{ book.bookIsbn }}
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
            class="inline-block rounded bg-maintheme1 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2">거래확인</button>
          <button v-if="tradeStatus === 'confirmed'" @click="editClick"
            class="inline-block rounded bg-maintheme1 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2">거래수정</button>
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
          x-transition:leave-start="opacity-100 scale-100" x-transition:leave-end="opacity-0 scale-95" x-cloak>
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
                    <tr v-for="book in changingData.books" :key="book.id">
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
                    </tr>
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
            <button @click="sendSelectedBooks"
              class="inline-block rounded bg-maintheme1 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2">담기완료</button>
            <!-- 모달에서 담기완료를 눌렀을때 선택된 도서들이 리스트로 보여져야한다. -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import axiosInstance from '@/services/axios';
// import type { traceDeprecation } from 'process';



const modalOpen = ref(false);

const toggleModal = () => {
  modalOpen.value = !modalOpen.value;
};

const originalBooks = ref<Book[]>([]);
// 재작성
const selectedBooks = ref<Book[]>([]);
const isChecked = ref<Record<number, boolean>>({});
const toggleCheckbox = (bookId: number) => {
  // const foundBook = sellingData.value?.books.find(book => book.id === bookId) 
  //             || sellingData.value?.seriesBooks.books.find(book => book.id === bookId);
  const foundBook = changingData.value?.books.find(book => book.id === bookId);
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

// const sendSelectedBooks = () => {
//   console.log('Selected Books:', selectedBooks.value);
//   if (changingData?.value) {
//     toggleModal();
//   }
// };
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




const sendSelectedBooks = async () => {
  console.log('Selected Books:', selectedBooks.value);
  if (changingData?.value) {
    toggleModal();
  }

  const addedBooks = selectedBooks.value.filter(book => !originalBooks.value.includes(book));
  const deletedBooks = originalBooks.value.filter(book => !selectedBooks.value.includes(book));

  const data: UpdatedBookData = {
    chatRoomId: chatroomId,  // chatroomId 사용
    userId: Number(changingData.value?.userId),  // userId 사용
    addedBooks: addedBooks.map(book => ({ id: book.id, bookIsbn: Number(book.bookIsbn) })),
    deletedBooks: deletedBooks.map(book => ({ id: book.id, bookIsbn: Number(book.bookIsbn) })),
  };

  await chooseBook(data);
};

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


//api 수정 후
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
const route = useRoute();
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


<style scoped>.button {
  position: absolute;
  right: 0;
  margin-right: 10px;
}</style>