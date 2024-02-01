<template>
  <div>
    <div class="relative m-2 flex justify-between">
      <div>
        <div>
          <button @click="clickbutton" 
          class="text-white w- py-2 px-4 m-2 bg-maintheme1 hover:bg-gray-500 rounded-lg">
            <div v-if="deleteState == false">
              <font-awesome-icon icon="fa-solid fa-trash-can" style="color: #ffffff;"/>
              삭제
            </div>
            <div v-else @click="deleteBooks">
              <font-awesome-icon icon="fa-solid fa-trash-can" style="color: #ffffff;"/>
              삭제완료
            </div>
          </button>
          <!-- <button v-if="deleteState"
            @click="allCheck"
            class="text-white w- py-2 px-4 m-2 bg-maintheme1 hover:bg-gray-500 rounded-lg">
            전체선택
          </button> -->
          <button v-if="deleteState"
            id="cancel-button"
            @click="cancelDelete"
            class="">
            취소
          </button>
        </div>
      </div>
      <div v-if="!deleteState" class="flex items-center">
        <!-- 만약 1과 2를 섞는다면 filterResult 내에서
        키워드에 해당하는 도서들만 나오게 하면 복합 검색이 가능함 -->
        <!-- 1. 필터링 -->
        <div class="relative inline-block mr-2">
          <select v-model="selectValue" id="filter" class="border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
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
          <input v-model="keyword" type="text" id="rounded-book" class="w-64 rounded-lg appearance-none border border-gray-500 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-gray-600 focus:border-transparent" placeholder="내 도서 검색"/>
          <button type="button" @click="keywordSearch(keyword)" class="text-white w-16 py-2 px-4 m-2 bg-maintheme1 hover:bg-gray-500 rounded-lg ">
            <font-awesome-icon icon="fa-solid fa-magnifying-glass" size="xl"/>
          </button>
        </div>
      </div>
    </div>
    <div class="grid grid-cols-2 gap-4 sm:grid-cols-4">
      <MyLibraryList :mybookList="selectValue === '전체도서' && !booksearch ? mybookList : filterMybook" />
    </div>
  </div>

</template>

<script setup lang="ts">
import MyLibraryList from '@/components/profile/mylibrary/MyLibraryList.vue';
import { ref, watch, onMounted, computed } from 'vue';
import { profileCounterStore } from '@/stores/profilecounter';
import { useRouter } from 'vue-router';
import type { Book } from '@/stores/profilecounter';

const store = profileCounterStore();
const selectValue = ref("전체도서");
const router = useRouter();
const keyword = ref("")
const booksearch = ref(false)

const mybookLists = ref<Book[]>(store.mybookLists)
const mybookList = computed(()=> {
  console.log(mybookLists.value)
  return mybookLists.value
})

const deleteBookList = computed(()=> {
  return store.deleteBookList
})

// const allCheck = () => {

// }

const keywordSearch = (keyword:string) => {
  booksearch.value = true
  if(keyword == ""){
    alert("검색어를 입력해 주세요.")
  } else {
    // 고민된다..... 만약 검색 후 
    // 다시 또 검색을 하면 검색대상을 어떻게 해야 할지..
    // 1. 전체목록으로 다시 초기화한 후에 키워드 검색
    filterMybook.value = mybookList.value
    // console.log(filterMybook.value)
    filterMybook.value = filterMybook.value.filter(book =>{
     const titleMatch = book.title.includes(keyword);
     const authorMatch = book.author.includes(keyword);
      return titleMatch || authorMatch
    });
  }
};


const filterMybook = ref<Book[]>(mybookLists.value)

// // selectValue의 변화를 감지하는 watch 설정
const selectWatch = watch(selectValue, (newValue) => {
  if (newValue != "전체도서"){
    if (newValue == '공개'){
      filterMybook.value = mybookList.value.filter(item => item.isOpened == 1)
    } else if (newValue == '비공개'){
      filterMybook.value = mybookList.value.filter(item => item.isOpened == 0)
    } else if (newValue == '보유'){
      filterMybook.value = mybookList.value.filter(item => item.isOwned == 1)
    } else if (newValue == '미보유'){
      filterMybook.value = mybookList.value.filter(item => item.isOwned == 0)
    } else if (newValue == '판매가능'){
      filterMybook.value = mybookList.value.filter(item => item.userBooktradeType == "PURCHASE")
    } else if (newValue == '교환가능')
      filterMybook.value = mybookList.value.filter(item => item.userBooktradeType == "EXCHANGE")
    }
  keyword.value=""
});



const deleteBooks = () => {
  const newbookList = ref<Book[]>([])
  mybookList.value.forEach((book)=> {
    if(!deleteBookList.value.includes(book)){
      newbookList.value.push(book)
    }
  })
  store.mybookLists = newbookList.value
  console.log(store.mybookLists)
  store.deleteBookList = []
  selectValue.value = "전체도서"
  // 꼼수
  alert("도서 삭제를 완료하였습니다.")
  router.go(0);
}

const clickbutton = () => { 
  store.toggledeletebutton();
}

const cancelDelete = () => {
  store.deleteBookList = []
  store.toggledeletebutton();
}

const deleteState = computed(() => {
  return store.deletebuttonState
})



onMounted(()=> {
  selectWatch;
  store.getMybookList();
})
</script>

<style scoped>
  #cancel-button {
  @apply border border-gray-300 text-maintheme1  hover:bg-slate-200  border-solid py-2 px-4 m-2 rounded-lg
}
</style>