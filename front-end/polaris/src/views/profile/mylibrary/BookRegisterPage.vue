<template>
  <Navvar></Navvar>
  <!-- 모달 제작이 되면 페이지를 없애고 다 모달로 옮길 예정-->
  <div
    id="register-book"
    class="container mt-60 md:mx-auto min-w-[600px] mt-28 max-w-6xl rounded-md h-[600px] font-[gowun-dodum]"
  >
    <!-- header -->
    <div class="flex justify-between p-4 mb-4 border-b-2 border-gray-300">
      <div class="text-3xl font-bold ml-3">도서 등록</div>
      <button @click="cancelRegister" id="cancel-register-book-button">
        뒤로가기
        <font-awesome-icon icon="fa-solid fa-xmark" />
      </button>
    </div>
    <div class="grid grid-cols-12 gap-4 p-3 h-[550px] rounded-b-xl">
      <!-- 도서 검색 -->
      <div id="register-book" class="col-span-6 bg-gray-50 border border-gray-300 rounded-lg p-2">
        <!-- 검색 바 -->
        <div class="flex flex-wrap flex-col md:flex-row justify-end border-b-2">
          <div class="flex items-center mt-4 md:mt-0">
            <div class="relative inline-block">
              <!-- md:hidden: 화면이 중간(medium) 크기 이하일 때(select 엘리먼트가 hidden 됨) -->
              <!-- {{  filter }} -->
              <select
                v-model="filter"
                id="search-filter"
                class="m-2 border border-gray-300 text-gray-900 text-sm rounded-md focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              >
                <option value="" disabled >검색 구분</option>
                <option>도서 제목</option>
                <option>저자</option>
                <option>출판사</option>
              </select>
            </div>
          </div>
          <div>
            <input
              @keyup.enter="searchAPIbook(keyword, filter)"
              v-model="keyword"
              type="text"
              id="book"
              class="w-auto rounded-lg appearance-none border border-gray-500 py-2 px-4 m-2 bg-gray-50 text-maintheme1 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-gray-600 focus:border-transparent"
              placeholder="도서 검색"
            />
            <button
              @click="searchAPIbook(keyword, filter)"
              type="button"
              class="w-16 text-white mx-2 md:mr-2 md:w-16 py-2 px-3 my-2 md:my-0 bg-maintheme1 hover:bg-gray-500 rounded-md"
            >
              <font-awesome-icon icon="fa-solid fa-magnifying-glass" size="xl" />
            </button>
          </div>
        </div>
        <div class="max-h-[450px] overflow-y-auto">
          <!-- 검색 결과 목록 -->
          <BookSearchResultList />
        </div>
      </div>

      <!-- 도서바구니 -->
      <div id="book-cart" class="relative col-span-6 border border-gray-300 rounded-md">
        <!-- 도서바구니 header -->
        <div
          id="cart-header"
          class="flex rounded-t-md border-b-2 text-maintheme1 font-bold p-2 my-2"
        >
          <div class="items-center text-2xl">
            도서 바구니
            <font-awesome-icon icon="fa-solid fa-cart-shopping" />
          </div>
        </div>
        <!-- 도서바구니 목록 -->
        <!-- 도서바구니 BookCartListItem -->
        <div class="max-h-[400px] overflow-y-auto">
          <BookCartList />
        </div>
        <!-- 담기완료 button 행 -->
        <div class="w-full border-t-2 border-gray-200 absolute bottom-0 right-0">
          <div class="flex justify-end m-1">
            <button
              @click="addAPIbook(bookcartList)"
              type="button"
              id="complete-register-book-button"
            >
              등록
              <font-awesome-icon icon="fa-solid fa-circle-check" style="color: white" />
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { onMounted, ref, computed, watch } from 'vue'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'
import BookCartList from '@/components/profile/mylibrary/bookregister/BookCartList.vue'
import BookSearchResultList from '@/components/profile/mylibrary/bookregister/BookSearchResultList.vue'
import { profileCounterStore } from '@/stores/profilecounter'
import type { Searchbook } from '@/stores/profilecounter'
import Swal from 'sweetalert2'

const keyword = ref<string>('')
const filter = ref<string>("")
const store = profileCounterStore()
const router = useRouter()
const route = useRoute()
const BACK_API_URL = store.BACK_API_URL

const searchbookLists = computed(() => {
  return store.searchbookLists
})
const bookcartList = computed(() => {
  return store.bookCartList
})

const searchAPIbook = (keywordsearch: string, filter: string) => {
  store.searchAPIbookList(keywordsearch, filter)
}

const cancelRegister = () => {
  store.searchbookLists = []
  store.bookCartList = []
  keyword.value = ''
  router.push({ name: 'MyLibraryPage' })
}

const mybookCartList = computed(() => {
  return store.bookCartList
})

const addAPIbook = (bookcartList: Searchbook[]) => {
  // console.log('현재 내 장바구니 목록: ', bookcartList)
  if (bookcartList.length) {
    // console.log('장바구니 목록이 존재함.')
    axios({
      headers: {
        Authorization: `${store.token}`,
        'Content-Type': 'application/json'
      },
      method: 'post',
      // 접속자 id
      url: `${BACK_API_URL}/book/${route.params.id}/library`,
      data: {
        books: bookcartList
      }
    })
      .then((response) => {
        // console.log(response.data)
        Swal.fire({
          title: '도서 등록이 완료되었습니다.',
          icon: 'success'
        })
        router.push({ name: 'ProfilePage' })
      })
      .catch((error) => {
        console.error(error)
      })
  } else {
    Swal.fire({
      title: '담긴 도서가 존재하지 않습니다.',
      icon: 'error'
    })
  }
  store.searchbookLists = []
  store.bookCartList = []
  keyword.value = ''
}

onMounted(() => {
  keyword.value = ''
  store.searchbookLists = []
})
</script>


<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../../public/GowunDodum-Regular.ttf');
}
#cart-header {
  height: 50px;
}
img {
  height: 70px;
  width: 60px;
}

#cancel-register-book-button {
  @apply font-bold text-lg text-white bg-maintheme1 rounded-[5px] p-2 border border-gray-400 hover:bg-gray-300;
}

#complete-register-book-button {
  @apply bg-[#323F59] border text-white font-bold m-[2px] px-3 py-2 rounded-md hover:bg-gray-500 text-lg;
}
</style>