<template>
  <div class="container mx-auto mt-24 max-w-6xl min-w-[700px] bg-backgroundgray p-4">
    <h1 class="text-2xl font-bold">도서 상세보기</h1>
    <div class="flex justify-end">
      <div v-if="!isMe">
        <button id="move-library" type="submit"  class="hover:bg-gray-500" >내 서재로 옮기기</button>
      </div>
      <div v-else>
        <div v-if="updateBook">
          <button @click="saveBookinfo" id="save-update" type="submit"  class="hover:bg-gray-500" >저장</button>
        </div>
        <div v-else>
          <button @click="updateBookinfo" id="update-info" type="submit"  class="hover:bg-gray-500" >수정</button>
        </div>
      </div>
    </div>
      <div class="container grid grid-cols-12 gap-8">
        <!-- 도서 상세 좌측 -->
        <div class="col-span-4 grid items-center">
          <div>
            <img :src="bookDetail.cover" alt="" id="book-image" class="p-2 shadow-md shadow-gray-500  border-gray-500">
          </div>
          <!-- 사용자 정보 -->
          <div class="grid grid-cols-3 justify-center p-4 m-2 shadow-md rounded-xl">
            <div id="userid" class="col-span-2 font-bold flex items-center justify-center m-2">
                환불받으러옴
                (userid를 내놓으시죠 제발)
              </div>
            <button @click="gotoProfile">
              <img class="col-span-1" id="profile-image" src="@\assets\following-user.jpg" alt="profile-image">
            </button>
          </div>
          <div class="p-4" :class="{'pointer-events-none cursor-not-allowed': !isMe || !updateBook, 'shadow-lg border rounded-2xl bg-indigo-50': isMe && updateBook }">
            <div v-if="updateBook">
                <div class="text-rose-400 text-sm font-bold pb-1">
                  원하는 거래옵션으로 변경하세요. 
                </div>
            </div>
            <!-- 보유/공개 여부 표시 -->
            <div class="flex justify-around mb-2 font-bold">
              <div>보유</div>
              <div>공개</div>
            </div>
            <div class="flex justify-around">
              <label class="relative inline-flex items-center cursor-pointer">
                <input @click="toggleOwned" type="checkbox" v-model="bookDetail.isOwned" class="sr-only peer">
                <div class="after:bg-white  after:border after:rounded-full w-11 h-6 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:h-5 after:w-5 after:transition-all dark:border-gray-600" :class="{ 'bg-gray-200 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full': !bookDetail.isOwned, 'bg-blue-600 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:bg-blue-600':bookDetail.isOwned}"></div>  
              </label>           
              <label class="relative inline-flex items-center cursor-pointer">
                  <input @click="toggleOpened" type="checkbox" v-model="bookDetail.isOpened" class="sr-only peer">
                  <div class="after:bg-white  after:border after:rounded-full w-11 h-6 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:h-5 after:w-5 after:transition-all dark:border-gray-600" :class="{ 'bg-gray-200 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full': !bookDetail.isOpened, 'bg-blue-600 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:bg-blue-600':bookDetail.isOpened}"></div>  
              </label>           
            </div>
            <div v-if="bookDetail.isOwned && bookDetail.isOpened">
              <!-- 거래상태 표시 -->
              <div class="m-4">
                <span v-if="bookDetail.userBookTradeType=='EXCHANGE'" class="bg-yellow-500 py-1 px-2 text-white w-auto text-center border rounded-lg">
                  교환가능
                </span>
                <span v-else-if="bookDetail.userBookTradeType=='TRADE'" class="bg-red-600 py-1 px-2 text-white w-auto text-center border rounded-lg">
                  판매가능
                </span>
                <span v-else class="bg-gray-600 py-1 px-2 text-white w-auto text-center border rounded-lg">
                  미거래
                </span>
              </div>
              <!-- 거래상태 수정 -->
              <div v-if="updateBook"  class="p-4">
                <button id="trade" class="bg-red-600 hover:bg-red-400 ">
                  <input class="sr-only" type="radio" id="one" value="TRADE" v-model="bookDetail.userBookTradeType"/>
                  <label for="one">판매</label>
                </button>  
                <button id="exchange" class="bg-yellow-500 hover:bg-yellow-400">
                  <input class="sr-only" type="radio" id="two" value="EXCHANGE" v-model="bookDetail.userBookTradeType" />
                  <label for="two">교환</label>
                </button>
                <button id="undefined" class="bg-gray-600 hover:bg-gray-400">
                  <input class="sr-only" type="radio" id="three" value="UNDEFINED" v-model="bookDetail.userBookTradeType" />
                  <label for="three">미거래</label>
                </button>
              </div>
            </div>
          </div>
        </div>
        <!-- 도서 상세 우측 -->
        <div class="col-span-8 m-2">
          <div id="name" class="container grid grid-cols-6 flex">
            <div class="text-maintheme1 m-2 font-bold col-span-1">
              <div class="mb-2">도서명</div>
            </div>
            <div class="col-span-4 text-maintheme1 m-2">
              <div class="mb-2">{{ bookDetail.title }}</div>
            </div>
          </div>
          <div id="author" class="container grid grid-cols-6 flex">
            <div class="text-maintheme1 m-2 font-bold col-span-1">
              <div class="mb-2">저자</div>
            </div>
            <div class="col-span-4 text-maintheme1 m-2">
              <div class="mb-2">{{ bookDetail.author }}</div>
            </div>
          </div>
          <div id="publisher" class="container grid grid-cols-6 flex">
            <div class="text-maintheme1 m-2 font-bold col-span-1">
              <div class="mb-2">출판사</div>
            </div>
            <div class="col-span-4 text-maintheme1 m-2">
              <div class="mb-2">{{ bookDetail.publisher}}</div>
            </div>
          </div>
          <div id="publish-date" class="container grid grid-cols-6 flex">
            <div class="text-maintheme1 m-2 font-bold col-span-1">
              <div class="mb-2">출간일</div>
            </div>
            <div class="col-span-4 text-maintheme1 m-2">
              <div class="mb-2">{{ bookDetail.pubDate.toString().split('T')[0] }}</div>
            </div>
          </div>
          <div id="price" class="container grid grid-cols-6">
            <div class="text-maintheme1 m-2 font-bold col-span-1">
              <div class="mb-2">정가</div>
            </div>
            <div class="col-span-4 text-maintheme1 m-2">
              <div class="mb-2">{{ bookDetail.priceStandard }}</div>
            </div>
          </div>
          <div v-if="bookDetail.userBookTradeType=='TRADE'" id="sell-price" class="container grid grid-cols-6">
            <div class="text-maintheme1 m-2 font-bold col-span-1">
              <div class="mb-2">판매가</div>
            </div>
            <div class="col-span-4 text-maintheme1 m-2">
                <div v-if="updateBook">
                <input
                type="selling-price"
                id="selling-price"
                placeholder=" 12,000"
                  class="rounded-md border h-8"/>
              </div>
              <div v-else  class="col-span-4 text-maintheme1 m-2">
                <div class="mb-2">12,000</div>
              </div>
            </div>
          </div>
          <div id="ISBN" class="container grid grid-cols-6 flex">
            <div class="text-maintheme1 m-2 font-bold col-span-1">
              <div class="mb-2">ISBN</div>
            </div>
            <div class="col-span-4 text-maintheme1 m-2">
              <div class="mb-2">{{ bookDetail.isbn }}</div>
            </div>
          </div>
          <div id="book-description" class="container border-b-2 grid grid-cols-6 flex mb-4">
            <div class="text-maintheme1 m-2 font-bold col-span-1">
              <div>도서 설명</div>
            </div>
            <div class="col-span-4 text-maintheme1 m-2">
              {{ bookDetail.bookDescription }}
            </div>
          </div>
          <div id="user-book-description" class="container  grid grid-cols-6 flex mb-4">
            <div class="text-maintheme1 m-2 font-bold col-span-1">
              <div>비고</div>
            </div>
            <div v-if="updateBook"  class="col-span-4 text-maintheme1 m-2">
              <textarea
              id="OrderNotes"
              class="mt-2 mb-4 w-full resize-none sm:text-sm"
              rows="5"
              :placeholder="bookDetail.userBookDescription"
              ></textarea>
            </div>
            <div v-else class="col-span-4 text-maintheme1 m-2">
              {{ bookDetail.userBookDescription }}
            </div>
          </div>
          <!-- 하단 버튼 -->
          <div class="flex justify-end">
            <div v-if="!isMe">
              <button id="trade-chat">
                구매 채팅
              </button>
              <button id="exchange-chat">
                교환 채팅
              </button>
            </div>
            <div v-if="existEssay">
              <button id="read-essay">
                독후감 보기
              </button>
            </div>
            <div v-if="isMe && !existEssay">
              <button id="write-essay">
                독후감 쓰기
              </button>
            </div>  
            </div>
        </div>
    </div>
  </div>


</template>

<script setup lang="ts">
  import { onMounted, ref } from 'vue';
  import { useRouter, useRoute } from 'vue-router';
  import type { Book } from '@/stores/profilecounter';
  import { profileCounterStore } from '@/stores/profilecounter';
  import axios from 'axios';
  const router = useRouter();

  // 이후에는 store.ts로 옮겨서 서버에서 데이터를 받아올 예정 
  const bookDetail = ref<Book>({
    isbn: "0000000000000",
    title: "무제",
    bookDescription: null,
    pubDate : new Date('2099-12-31'),
    cover: "imagecover",
    publisher : "",
    author: "",
    priceStandard: 0,
    userBookDescription : "",
    userBookPrice: 0,
    isOpened: false,
    isOwned: false,
  });

  const isMe = ref<boolean>(true)
  const updateBook = ref<boolean>(false)
  const existEssay = ref<boolean>(false)
  const store = profileCounterStore();
  const route = useRoute();
  const BACK_API_URL = store.BACK_API_URL

  // 수정정보 저장
  const saveBookinfo = () => {
    // DB에 저장하는 구문 추가
    updateBook.value = !updateBook.value

  }

  // 정보수정 버튼
  const updateBookinfo = () => {
    updateBook.value = !updateBook.value
  }

  // 보유/공개 토글
  const toggleOwned = () => {
    bookDetail.value.isOwned = !bookDetail.value.isOwned
  }

  const toggleOpened = () => {
    bookDetail.value.isOpened = !bookDetail.value.isOpened
  }

  // 프로필 이동
  const gotoProfile = () => {
    console.log("사용자의 프로필로 이동합니다.")
    // router.push({name: ProfilePage , params: userid})
  }

  onMounted(()=>{
    axios({
      headers: {
        Authorization: `${store.token}`,
        "Content-Type": 'application/json'
      },
      method: 'get',
      url: `${BACK_API_URL}/book/1/library/${route.params.isbn}`,
      // url: `${BACK_API_URL}/book/${route.params.id}/library/${route.params.isbn}`

    })
    .then((response)=> {
      const bookinfo = response.data['data']
      bookDetail.value = bookinfo
    })
    .catch((error)=> {
      console.error("요청실패: ",error)
    })

  })
</script>

<style scoped>
  #profile-image {
      @apply w-[70px] h-[70px] rounded-full shadow-gray-600 shadow-md 
  }


  #book-image {
    @apply w-[240px] h-[320px] ml-10
  }

  #trade,
  #exchange,
  #undefined {
    @apply w-auto text-white m-[5px] px-2.5 py-[5px] rounded-[10px];
  }
  #update-info,
  #save-update,
  #move-library,
  #trade-chat,
  #exchange-chat,
  #read-essay,
  #write-essay {
      @apply w-auto hover:bg-gray-500 bg-[#323F59] text-white m-[5px] px-2 py-[5px] rounded-[10px];
  }
</style>