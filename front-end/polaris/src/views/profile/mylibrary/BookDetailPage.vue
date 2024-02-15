<template>
  <Navvar></Navvar>
  <div class="container mt-60 mx-auto mt-24 max-w-6xl min-w-[700px] p-4 font-[gowun-dodum]">
    <h1 class="text-3xl font-bold ml-10">도서 상세보기</h1>
    <div class="flex justify-end">
      <div>
        <button @click="goback()" id="save-update" type="button" class="hover:bg-gray-500">
          BACK
        </button>
      </div>
      <div v-if="!isMybook">
        <button
          v-if="!getMybook"
          @click="moveToMyLibrary"
          id="move-library"
          type="button"
          class="hover:bg-gray-500"
        >
          내 서재로 옮기기
        </button>
      </div>
      <div v-else>
        <div v-if="updateBook">
          <button @click="saveBookinfo" id="save-update" type="button" class="hover:bg-gray-500">
            <font-awesome-icon icon="fa-solid fa-floppy-disk" />
            저장
          </button>
        </div>
        <div v-else>
          <button @click="updateBookinfo" id="update-info" type="button" class="hover:bg-gray-500">
            <font-awesome-icon icon="pen-to-square" />
            수정
          </button>
        </div>
      </div>
    </div>
    <div class="container grid grid-cols-12 gap-8">
      <!-- 도서 상세 좌측 -->
      <div class="col-span-4">
        <div class="flex justify-center">
          <img
            :src="bookDetail.cover"
            alt=""
            id="book-image"
            class="shadow-md shadow-gray-500 border-gray-500"
          />
        </div>
        <!-- 사용자 정보 -->
        <div class="grid grid-cols-4 justify-center mt-4 p-4">
          <div id="userid" class="col-span-3 font-bold flex items-center justify-center">
            <!-- 나중에는 이 정보로 닉네임 불러오기 -->
            {{ bookDetail.nickname }}
          </div>
          <button @click="gotoProfile">
            <img
              v-if="bookDetail.profileUrl"
              class="col-span-1 object-cover"
              id="profile-image"
              :src="bookDetail.profileUrl"
              alt=""
            />
            <img
              v-else
              class="col-span-1 object-cover"
              id="profile-image"
              src="@\assets\profile-man.jpg"
              alt=""
            />
          </button>
        </div>
        <div
          class="p-4"
          :class="{
            'pointer-events-none cursor-not-allowed': !isMybook || !updateBook,
            'shadow-md bg-gray-50': isMybook && updateBook
          }"
        >
          <div v-if="updateBook">
            <div class="text-rose-400 text-base font-bold pb-2 text-center">
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
              <input
                @click="toggleOwned"
                type="checkbox"
                v-model="bookDetail.isOwned"
                class="sr-only peer"
              />
              <div
                class="after:bg-white after:border after:rounded-full w-11 h-6 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:h-5 after:w-5 after:transition-all dark:border-gray-600"
                :class="{
                  'bg-gray-200 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full':
                    !bookDetail.isOwned,
                  'bg-blue-600 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:bg-blue-600':
                    bookDetail.isOwned
                }"
              ></div>
            </label>
            <label class="relative inline-flex items-center cursor-pointer">
              <input
                @click="toggleOpened"
                type="checkbox"
                v-model="bookDetail.isOpened"
                class="sr-only peer"
              />
              <div
                class="after:bg-white after:border after:rounded-full w-11 h-6 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:h-5 after:w-5 after:transition-all dark:border-gray-600"
                :class="{
                  'bg-gray-200 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full':
                    !bookDetail.isOpened,
                  'bg-blue-600 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:bg-blue-600':
                    bookDetail.isOpened
                }"
              ></div>
            </label>
          </div>
          <div v-if="bookDetail.isOwned && bookDetail.isOpened" class="m-6 text-center">
            <!-- 거래상태 표시 -->
            <div>
              <span
                v-if="bookDetail.userBookTradeType == 'EXCHANGE'"
                class="bg-yellow-500 py-1 px-2 text-white w-auto text-center border rounded-lg"
              >
                교환가능
              </span>
              <span
                v-else-if="bookDetail.userBookTradeType == 'PURCHASE'"
                class="bg-red-600 py-1 px-2 text-white w-auto text-center border rounded-lg"
              >
                판매가능
              </span>
              <span
                v-else
                class="bg-gray-600 py-1 px-2 text-white w-auto text-center border rounded-lg"
              >
                미거래
              </span>
            </div>
            <!-- 거래상태 수정 -->
            <div v-if="updateBook" class="m-4 p-4">
              <button id="purchase" class="bg-red-600 hover:bg-red-400">
                <input
                  class="sr-only"
                  type="radio"
                  id="one"
                  value="PURCHASE"
                  v-model="bookDetail.userBookTradeType"
                />
                <label for="one">판매</label>
              </button>
              <button id="exchange" class="bg-yellow-500 hover:bg-yellow-400">
                <input
                  class="sr-only"
                  type="radio"
                  id="two"
                  value="EXCHANGE"
                  v-model="bookDetail.userBookTradeType"
                />
                <label for="two">교환</label>
              </button>
              <button id="undefined" class="bg-gray-600 hover:bg-gray-400">
                <input
                  class="sr-only"
                  type="radio"
                  id="three"
                  value="UNDEFINED"
                  v-model="bookDetail.userBookTradeType"
                />
                <label for="three">미거래</label>
              </button>
            </div>
          </div>
        </div>
      </div>
      <!-- 도서 상세 우측 -->
      <div class="col-span-8 m-2">
        <div id="name" class="container grid grid-cols-6">
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
            <div class="mb-2">{{ bookDetail.publisher }}</div>
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
            <div class="mb-2">{{ bookDetail.priceStandard.toLocaleString() }} 원</div>
          </div>
        </div>
        <div
          v-if="bookDetail.userBookTradeType == 'PURCHASE'"
          id="sell-price"
          class="container grid grid-cols-6"
        >
          <div class="text-maintheme1 m-2 font-bold col-span-1">
            <div class="mb-2">판매가</div>
          </div>
          <div class="col-span-4 text-maintheme1 m-2">
            <div v-if="updateBook">
              <input
                type="number"
                id="selling-price"
                v-model.number="bookDetail.userBookPrice"
                placeholder="판매가를 설정해 주세요."
                class="rounded-md border h-8 w-56"
              />
            </div>
            <div v-else class="col-span-4 text-maintheme1 m-2">
              <div class="mb-2">{{ bookDetail.userBookPrice?.toLocaleString() }} 원</div>
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
            <div v-if="bookDetail.bookDescription">
              {{ bookDetail.bookDescription }}
            </div>
            <div v-else>없음.</div>
          </div>
        </div>
        <div id="user-book-description" class="container grid grid-cols-6 flex mb-4">
          <div class="text-maintheme1 m-2 font-bold col-span-1">
            <div>비고</div>
          </div>
          <div v-if="updateBook" class="col-span-4 text-maintheme1 m-2">
            <textarea
              id="OrderNotes"
              class="mt-2 mb-4 w-full resize-none sm:text-sm"
              rows="5"
              v-model="bookDetail.userBookDescription"
              :placeholder="bookDetail.userBookDescription"
            ></textarea>
          </div>
          <div v-else class="col-span-4 text-maintheme1 m-2">
            {{ bookDetail.userBookDescription }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { onMounted, ref, computed, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import type { Book, Searchbook } from '@/stores/profilecounter'
import { profileCounterStore } from '@/stores/profilecounter'
import axiosInstance from '@/services/axios'
import Swal from 'sweetalert2'
// 이후에는 store.ts로 옮겨서 서버에서 데이터를 받아올 예정
const bookDetail = ref<Book>({
  id: 9090,
  nickname: 'no name',
  userId: 190909090909090,
  isbn: '0000000000000',
  title: '무제',
  bookDescription: null,
  pubDate: new Date('2099-12-31'),
  cover: '@/assets/book-image.jpg',
  publisher: '',
  author: '',
  priceStandard: 0,
  userBookDescription: '',
  userBookPrice: 0,
  isOpened: false,
  isOwned: false,
  profileUrl: ''
})

const store = profileCounterStore()
const router = useRouter()
const userInfoString = ref<string>(localStorage.getItem('user_info') ?? '')
const loginUser = JSON.parse(userInfoString.value)
const isMybook = computed(() => {
  return bookDetail.value.userId == Number(loginUser.id)
})

const mybookList = computed(() => {
  return store.mybookLists
})

const getMybook = ref(false)
const updateBook = ref<boolean>(false)
const route = useRoute()
const BACK_API_URL = store.BACK_API_URL

// 뒤로 가기
const goback = () => {
  window.history.back()
}
// 수정정보 저장
const saveBookinfo = () => {
  // DB에 저장하는 구문 추가
  if (!(bookDetail.value.isOwned && bookDetail.value.isOpened)) {
    bookDetail.value.userBookTradeType = 'UNDEFINED'
  }
  // console.log(bookDetail.value.userBookTradeType)
  axiosInstance
    .value({
      headers: {
        Authorization: `${store.token}`,
        'Content-Type': 'application/json'
      },
      method: 'put',
      url: `${BACK_API_URL}/book/${route.params.id}/library`,
      data: {
        isbn: bookDetail.value.isbn,
        userBookDescription: bookDetail.value.userBookDescription,
        userBookPrice: bookDetail.value.userBookPrice,
        isOpened: Number(bookDetail.value.isOpened),
        isOwned: Number(bookDetail.value.isOwned),
        userBookTradeType: bookDetail.value.userBookTradeType
      }
    })
    .then((response) => {
      // console.log(response.data)
      Swal.fire({
        title: '수정이 완료되었습니다.',
        icon: 'success'
      })
    })
    .catch((error) => {
      console.error(error)
    })
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
  router.push({ name: 'ProfilePage', params: { id: bookDetail.value.userId } })
}

// 내 서재로 옮기기
const moveToMyLibrary = () => {
  const userbook: Searchbook = {
    isbn: bookDetail.value.isbn,
    title: bookDetail.value.title,
    bookDescription: bookDetail.value.bookDescription,
    pubDate: bookDetail.value.pubDate,
    cover: bookDetail.value.cover,
    publisher: bookDetail.value.publisher,
    author: bookDetail.value.author,
    priceStandard: bookDetail.value.priceStandard,
    isOpened: false,
    isOwned: false,
    userBookTradeType: 'UNDEFINED'
  }
  if (bookDetail.value.isbn == '' && bookDetail.value.seriesId) {
    userbook.isbn = String(bookDetail.value.seriesId)
  }

  if (bookDetail.value.seriesId) {
    userbook.seriesId = bookDetail.value.seriesId
    userbook.seriesName = bookDetail.value.seriesName
  }
  // 서재에 추가 요청
  axiosInstance
    .value({
      headers: {
        Authorization: `${store.token}`,
        'Content-Type': 'application/json'
      },
      method: 'post',
      url: `${BACK_API_URL}/book/${loginUser.id}/library`,
      data: {
        books: [userbook]
      }
    })
    .then((response) => {
      // console.log(response.data)
      getMybook.value = true
      Swal.fire({
        title: '해당 도서를 내 서재로 옮겼습니다.',
        icon: 'success'
      })
    })
    .catch((error) => {
      console.error(error)
    })
}

onMounted(() => {
  store.getMybookList(loginUser.id)
  // 상세 페이지 책 정보 요청
  axiosInstance
    .value({
      headers: {
        Authorization: `${store.token}`,
        'Content-Type': 'application/json'
      },
      method: 'get',
      url: `${BACK_API_URL}/book/${route.params.id}/library/${route.params.isbn}`
    })
    .then((response) => {
      // console.log(response.data)
      const bookinfo = response.data['data']
      bookDetail.value = bookinfo
      if (mybookList.value.some((mybook) => mybook.isbn == bookDetail.value.isbn)) {
        getMybook.value = true
      }
    })
    .catch((error) => {
      console.error('요청실패: ', error)
    })
})
</script>

<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../public/GowunDodum-Regular.ttf');
}

#profile-image {
  @apply w-[70px] h-[70px] rounded-full shadow-gray-600 shadow-md;
}

#book-image {
  @apply w-[240px] h-[320px] ml-10;
}

#purchase,
#exchange,
#undefined {
  @apply w-auto text-white m-[5px] px-2.5 py-[5px] rounded-[10px];
}
#update-info,
#save-update,
#move-library,
#purchase-chat,
#exchange-chat,
#read-essay,
#write-essay {
  @apply w-auto hover:bg-gray-500 bg-[#323F59] text-white m-[5px] px-3 py-[5px] rounded-[10px];
}
</style>