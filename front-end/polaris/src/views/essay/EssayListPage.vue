<template>
  <Navvar></Navvar>
  <div class="mt-40 flex justify-center font-[gowun-dodum]">
    <div class="container w-full max-w-6xl p-2">
      <!-- 최상단 -->
      <div class="grid grid-cols-5 items-center mb-4 shadow-md">
        <img class="col-span-3 w-full h-[350px] object-cover" src="@\assets\night-sky.jpg" alt="" />
        <div class="col-span-2 justify-items-center p-4">
          <div class="text-xl font-bold my-4 text-center">
            <p>마음을 담아, 책 속 세계를 함께 느껴봐요!</p>
          </div>
          <div class="text-center">
            <button @click="writeEssay" id="write-essay">독후감 작성하러 가기</button>
          </div>
        </div>
      </div>
      <!-- 검색바 -->
      <div class="flex flex-wrap flex-col md:flex-row justify-end border-b-2 pb-4 mb-8">
        <div class="relative inline-block">
          <!-- md:hidden: 화면이 중간(medium) 크기 이하일 때(select 엘리먼트가 hidden 됨) -->
          <!-- {{  filter }} -->
          <select
            v-model="filter"
            id="search-filter"
            class="m-2 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          >
            <option selected>도서 제목</option>
            <option>독후감 제목</option>
            <option>작성자</option>
          </select>
        </div>
        <div>
          <input
            @keyup.enter="essaySearch(keyword, filter)"
            v-model="keyword"
            type="text"
            id="book"
            class="w-96 rounded-lg appearance-none border border-maintheme1 shadow-md py-2 px-4 my-2 bg-gray-50 text-maintheme1 placeholder-gray-400 text-base focus:outline-none focus:ring-2 focus:ring-gray-600 focus:border-transparent"
            placeholder="독후감 검색"
          />
          <button
            @click="essaySearch(keyword, filter)"
            type="button"
            class="w-16 text-white mx-2 md:mr-2 md:w-16 p-3 my-2 md:my-0 bg-maintheme1 hover:bg-gray-500 rounded-lg"
          >
            <font-awesome-icon icon="fa-solid fa-magnifying-glass" size="xl" />
          </button>
        </div>
      </div>
      <div class="grid grid-cols-2 gap-8 sm:grid-cols-4">
        <EssayList :essayList="showingList" />
      </div>
      <ul class="pagination"></ul>
      <div
        v-if="essayList.length"
        class="flex items-center justify-between border-t border-gray-200 bg-white px-4 py-3 sm:px-6"
      >
        <div class="flex flex-1 justify-between sm:hidden">
          <li
            class="relative inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
            :class="{ disabled: isBtnPrev }"
          >
            <a class="page-link" href="#" @click.prevent="pageArrow('prev')">Previous</a>
          </li>
          <li
            class="relative ml-3 inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
            :class="{ disabled: isBtnNext }"
          >
            <a class="page-link" href="#" @click.prevent="pageArrow('next')">Next</a>
          </li>
        </div>
        <div class="hidden sm:flex sm:flex-1 justify-center">
          <div>
            <nav
              class="isolate inline-flex -space-x-px rounded-md shadow-sm"
              aria-label="Pagination"
            >
              <li
                class="relative inline-flex items-center rounded-s-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
                :class="{ disabled: isBtnFirst }"
              >
                <a class="page-link" href="#" @click.prevent="pageArrow('first')">First</a>
              </li>
              <li
                class="relative inline-flex items-center border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
                :class="{ disabled: isBtnPrev }"
              >
                <a class="page-link" href="#" @click.prevent="pageArrow('prev')">Previous</a>
              </li>
              <template v-for="(item, index) in pageList" :key="`list-${index}`">
                <li
                  class="relative inline-flex items-center border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700"
                  :class="{ active: item == currentPage }"
                >
                  <a class="page-link" href="#" @click.prevent="page(item)">{{ item + 1 }}</a>
                </li>
              </template>
              <li
                class="relative inline-flex items-center border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
                :class="{ disabled: isBtnNext }"
              >
                <a class="page-link" href="#" @click.prevent="pageArrow('next')">Next</a>
              </li>
              <li
                class="relative inline-flex items-center rounded-e-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
                :class="{ disabled: isBtnLast }"
              >
                <a class="page-link" href="#" @click.prevent="pageArrow('last')">Last</a>
              </li>
            </nav>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { computed, onMounted, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import EssayList from '../../components/essay/EssayList.vue'
import axiosInstance from '@/services/axios'
import { essayStore } from '@/stores/essaycounter'
import type { Essay } from '@/stores/essaycounter'
import Swal from 'sweetalert2'

const userToken = localStorage.getItem('user_token')
const router = useRouter()
const filter = ref('')
const keyword = ref('')
const store = essayStore()
// 보여지는 리스트
const showingList = ref<Essay[]>([])
const essayList = ref<Essay[]>([])

// 페이지 당 보여줄 게시글 개수
const listCnt = ref(20)
let currentPage = ref(0) // 현재 페이지
let pageNum = 10 // 페이지 개수
const pageList = ref<number[]>([]) // 보여지는 페이지 리스트
let totalPage = ref(0) // 페이지 숫자

let isBtnFirst = ref(true)
let isBtnPrev = ref(true)
let isBtnNext = ref(true)
let isBtnLast = ref(true)

const page = (e: number) => {
  currentPage.value = e
  essaySearch(keyword.value, filter.value)
}

watch(listCnt, (after, before) => {
  currentPage.value = 0
  essaySearch(keyword.value, filter.value)
})

const pageBtnCheck = () => {
  isBtnFirst.value = currentPage.value == 0 ? true : false
  isBtnPrev.value = currentPage.value == 0 ? true : false

  isBtnNext.value = currentPage.value == totalPage.value ? true : false
  isBtnLast.value = currentPage.value == totalPage.value ? true : false
}

watch(currentPage, (after, before) => {
  pageBtnCheck()
})

const pageArrow = (e: string) => {
  let movePage = currentPage.value
  if (e == 'first') {
    movePage = 0 // 처음으로
  } else if (e == 'last') {
    //마지막
    movePage = totalPage.value
  } else if (e == 'prev') {
    //이전
    movePage = currentPageListStart() - 1
    movePage < 0 ? (movePage = 0) : ''
  } else {
    //다음
    movePage = currentPageListStart() + 10
    movePage >= totalPage.value ? (movePage = totalPage.value) : ''
  }
  page(movePage)
}

const currentPageListStart = () => {
  return Math.floor(currentPage.value / pageNum) * pageNum
}

// 페이징
const paging = () => {
  pageList.value = []

  if (essayList.value.length % listCnt.value == 0) {
    totalPage.value = essayList.value.length / listCnt.value - 1
  } else {
    totalPage.value = Math.ceil(essayList.value.length / listCnt.value) - 1
  }

  // 현재 페이지 기준으로 페이징 숫자를 넣는다.
  let pageListStart: number = currentPageListStart()
  for (let i = 0; i < pageNum; i++) {
    if (totalPage.value >= pageListStart) {
      pageList.value.push(pageListStart)
      pageListStart++
    }
  }
}

router.beforeEach((to, from, next) => {
  if (to.name === 'essaycreate' && !userToken) {
    // 'essaycreate' 라우트이면서 userToken이 없는 경우
    Swal.fire({
      title: '로그인이 필요한 작업입니다.',
      icon: 'error'
    }).then(() => {
      next({ path: '/login' })
    })
  } else {
    // 'essaycreate' 이외의 다른 라우트 또는 'essaycreate'이지만 userToken이 있는 경우
    next()
  }
})

const writeEssay = () => {
  router.push({ name: 'essaycreate' })
}

const essaySearch = (keyword: string, filter: string) => {
  const key = ref<string>('')
  if (filter != '') {
    const searchCondition = {
      '도서 제목': 'bookTitle',
      '독후감 제목': 'title',
      작성자: 'user'
    } as { [key: string]: string }
    key.value = searchCondition[filter]
  }
  axiosInstance
    .value({
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'get',
      url: `${store.BACK_API_URL}/essay`,
      params: {
        pgno: 1,
        spp: 20,
        key: key.value,
        word: keyword
      }
    })
    .then((response) => {
      // console.log(response.data)
      const res = response.data.data
      essayList.value = res
      showingList.value = []
      let listIdx = listCnt.value * currentPage.value // 보여질 게시물 index
      for (let i = 0; i < listCnt.value; i++) {
        if (essayList.value.length > listIdx) {
          showingList.value.push(essayList.value[listIdx])
          listIdx++
        }
      }
      // console.log("현재 페이지:", showingList.value)
      paging()
      pageBtnCheck()
    })
    .catch((error) => {
      console.error(error)
    })
}

onMounted(() => {
  essaySearch('', '')
})
</script>


<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../../public/GowunDodum-Regular.ttf');
}

.active {
  @apply relative z-10 bg-blue-800 text-white focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-900;
}
#write-essay {
  @apply bg-[#323F59] border text-white font-bold m-[5px] px-5 py-[10px] rounded-[10px]  hover:bg-gray-500;
}
</style>