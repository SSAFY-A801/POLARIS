<template>
  <div>
    <div class="flex justify-between m-2">
      <div v-if="mybookList.length == 0" class="text-center text-xl mt-5 ml-40">
        <div>현재 서재에 도서가 존재하지 않습니다. 원하는 도서를 내 서재에 담아보세요.</div>
      </div>
      <div v-if="isMe">
        <div>
          <button
            @click="clickbutton"
            class="text-white w- py-2 px-4 m-2 bg-maintheme1 hover:bg-gray-500 rounded-lg"
          >
            <div v-if="deleteState == false">
              <font-awesome-icon icon="fa-solid fa-trash-can" style="color: #ffffff" />
              삭제
            </div>
            <div v-else>
              <font-awesome-icon icon="fa-solid fa-trash-can" style="color: #ffffff" />
              삭제완료
            </div>
          </button>
          <!-- <button v-if="deleteState"
            @click="allCheck"
            class="text-white w- py-2 px-4 m-2 bg-maintheme1 hover:bg-gray-500 rounded-lg">
            전체선택
          </button> -->
          <button v-if="deleteState" id="cancel-button" @click="cancelDelete" class="">취소</button>
        </div>
      </div>
    </div>
    <div class="relative m-2 flex justify-end">
      <div v-if="!deleteState" class="flex items-center">
        <!-- 만약 1과 2를 섞는다면 filterResult 내에서
        키워드에 해당하는 도서들만 나오게 하면 복합 검색이 가능함 -->
        <!-- 1. 필터링 -->
        <div class="relative inline-block mr-2">
          <select
            v-model="selectValue"
            id="filter"
            class="border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          >
            <option>전체도서</option>
            <option>공개</option>
            <option>비공개</option>
            <option>보유</option>
            <option>미보유</option>
            <option>판매가능</option>
            <option>교환가능</option>
          </select>
        </div>
        <!-- 2. 키워드 검색 -->
        <div @keyup.enter="keywordSearch(keyword)" action="">
          <input
            v-model="keyword"
            type="text"
            id="rounded-book"
            class="w-64 rounded-lg appearance-none border border-gray-500 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-gray-600 focus:border-transparent"
            placeholder="도서 검색"
          />
          <button
            type="button"
            @click="keywordSearch(keyword)"
            class="text-white w-16 py-2 px-4 m-2 bg-maintheme1 hover:bg-gray-500 rounded-lg"
          >
            <font-awesome-icon icon="fa-solid fa-magnifying-glass" size="xl" />
          </button>
        </div>
      </div>
    </div>
    <div class="grid grid-cols-2 gap-4 sm:grid-cols-4">
      <MyLibraryList
        :mybookList="selectValue === '전체도서' && !booksearch ? mybookList : filterMybook"
        :isMe="isMe"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import MyLibraryList from '@/components/profile/mylibrary/MyLibraryList.vue'
import { ref, watch, onMounted, computed } from 'vue'
import axios from 'axios'
import { profileCounterStore } from '@/stores/profilecounter'
import type { Book } from '@/stores/profilecounter'
import { useRoute } from 'vue-router'
import router from '@/router'
import Swal from 'sweetalert2'

const route = useRoute()
const store = profileCounterStore()
const selectValue = ref('전체도서')
const keyword = ref('')
const booksearch = ref(false)
const profileUser = computed(() => {
  return store.profileUser
})
const userInfoString = ref<string>(localStorage.getItem('user_info') ?? '')
const loginUser = JSON.parse(userInfoString.value)
const isMe = computed(() => {
  return profileUser.value?.id == loginUser.id
})

const mybookList = computed(() => {
  return store.mybookLists
})

const filterMybook = ref<Book[]>([])

// mybookList가 변경될 때마다 filterMybook 업데이트
watch(mybookList, (newValue) => {
  // 키워드 검색 로직에 따라서 filterMybook 업데이트
  if (!booksearch.value) {
    filterMybook.value = newValue
  } else {
    // 예시로 키워드 검색이 들어갈 로직을 추가하세요
    // filterMybook.value = ...
  }
})

const deleteBookList = computed(() => {
  // console.log(deleteBookList.value)
  return store.deleteBookList
})

// 키워드 검색
const keywordSearch = (keyword: string) => {
  booksearch.value = true
  if (keyword == '') {
    Swal.fire({
      title: '검색어를 입력해주세요.',
      icon: 'error'
    })
  } else {
    // 1. 전체목록으로 다시 초기화한 후에 키워드 검색
    filterMybook.value = mybookList.value
    // console.log(filterMybook.value)
    filterMybook.value = filterMybook.value.filter((book) => {
      const titleMatch = book.title.includes(keyword)
      const authorMatch = book.author.includes(keyword)
      return titleMatch || authorMatch
    })
  }
}

// selectValue의 변화를 감지하는 watch 설정
const selectWatch = watch(selectValue, (newValue) => {
  if (newValue != '전체도서') {
    if (newValue == '공개') {
      filterMybook.value = mybookList.value.filter((item) => item.isOpened == true)
    } else if (newValue == '비공개') {
      filterMybook.value = mybookList.value.filter((item) => item.isOpened == false)
    } else if (newValue == '보유') {
      filterMybook.value = mybookList.value.filter((item) => item.isOwned == true)
    } else if (newValue == '미보유') {
      filterMybook.value = mybookList.value.filter((item) => item.isOwned == false)
    } else if (newValue == '판매가능') {
      filterMybook.value = mybookList.value.filter((item) => item.userBookTradeType == 'PURCHASE')
    } else if (newValue == '교환가능')
      filterMybook.value = mybookList.value.filter((item) => item.userBookTradeType == 'EXCHANGE')
  }
  keyword.value = ''
})

// 도서 삭제 요청
const deleteBooks = () => {
  // console.log(deleteBookList.value)
  Swal.fire({
    title: `도서 삭제를 실시합니다.`,
    text: '정말 삭제시겠습니까?',
    icon: 'question',
    showDenyButton: true,
    confirmButtonText: '삭제',
    denyButtonText: `취소`
  }).then((result) => {
    if (result.isConfirmed) {
      axios({
        headers: {
          Authorization: `${store.token}`,
          'Content-Type': 'application/json'
        },
        method: 'delete',
        url: `${store.BACK_API_URL}/book/${route.params.id}/library`,
        data: {
          books: deleteBookList.value
        }
      })
        .then((response) => {
          // console.log(response.data)
          router.go(0)
        })
        .catch((error) => {
          console.error(error)
          Swal.fire(`도서 삭제에 실패했습니다.\n다시 시도해주세요.`, '', 'error')
        })
      store.deleteBookList = []
      selectValue.value = '전체도서'
    }
  })
}

// 도서 선택 후 삭제 버튼 클릭
const clickbutton = () => {
  if (deleteState.value && deleteBookList.value.length == 0) {
    Swal.fire({
      title: '도서목록을 선택하세요.',
      icon: 'error'
    })
  } else if (deleteState.value && deleteBookList.value.length) {
    deleteBooks()
    store.toggledeletebutton()
  } else if (!deleteState.value) {
    store.toggledeletebutton()
  }
}

// 삭제 취소
const cancelDelete = () => {
  store.deleteBookList = []
  store.toggledeletebutton()
}

const deleteState = computed(() => {
  return store.deletebuttonState
})

// 서재 목록 변경 watch
watch(store.mybookLists, (newValue, oldValue) => {
  // console.log('내 서재 목록 변경:', oldValue, '->', newValue);
})

onMounted(async () => {
  store.mybookLists = []
  await store.getMybookList(route.params.id as string)
  selectWatch
  filterMybook.value = mybookList.value
  store.deleteBookList = []
  store.deletebuttonState = false
  // console.log(mybookList.value)
})
</script>

<style scoped>
#cancel-button {
  @apply border border-gray-300 text-maintheme1  hover:bg-slate-200  border-solid py-2 px-4 m-2 rounded-lg;
}
</style>