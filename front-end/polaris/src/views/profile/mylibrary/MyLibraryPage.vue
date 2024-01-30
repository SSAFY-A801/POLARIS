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
          <button v-if="deleteState == true"
            @click="cancelDelete"
            class="text-white w- py-2 px-4 m-2 bg-maintheme1 hover:bg-gray-500 rounded-lg">
            취소
          </button>
        </div>
      </div>
      <div class="flex items-center">
        <!-- 1. 키워드 검색 -->
        <form action="">
          <input type="text" id="rounded-book" class="w-64 rounded-lg appearance-none border border-gray-500 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-gray-600 focus:border-transparent" placeholder="내 도서 검색"/>
          <button type="submit" class="text-white w-16 py-2 px-4 m-2 bg-maintheme1 hover:bg-gray-500 rounded-lg ">
            <font-awesome-icon icon="fa-solid fa-magnifying-glass" size="xl"/>
          </button>
        </form>
        <!-- 2. 필터링 -->
        <div class="relative inline-block">
          <!-- {{ selectValue }} -->
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
      </div>
    </div>
    <div class="grid grid-cols-2 gap-4 sm:grid-cols-4">
        <MyLibraryList :mybookList="filterResult"/>
    </div>
  </div>

</template>

<script setup lang="ts">
import MyLibraryList from '@/components/profile/mylibrary/MyLibraryList.vue';
import { ref, watch, onMounted, computed } from 'vue';
import { profileCounterStore } from '@/stores/profilecounter';
import type { Book } from '@/stores/profilecounter';

const store = profileCounterStore();
const selectValue = ref("전체도서");
const filterResult = ref(store.filterResult);
const mybookList = ref(store.mybookLists);
const deleteBookList = ref(store.deleteBookList);
const newbookList = ref<Book[]>([])

// selectValue의 변화를 감지하는 watch 설정
const selectWatch = watch(selectValue, (newValue) => {
  // 초기화
  filterResult.value = []
  // 전체도서
  if (newValue == "전체도서") {
    filterResult.value = mybookList.value;
  } else {
    mybookList.value.forEach(bookinfo => {
      if (bookinfo.isOwned == newValue){
        filterResult.value.push(bookinfo);
      } else if (bookinfo.isOpened == newValue) {
        filterResult.value.push(bookinfo);
      } else {
        if (newValue == "교환가능" && bookinfo.tradeType == "EXCHANGE"){ 
          filterResult.value.push(bookinfo);
        } else if (newValue == "판매가능" && bookinfo.tradeType == "TRADE"){
          filterResult.value.push(bookinfo);
        }
      }
    });
  }
  store.filterResult = filterResult.value;
});



const deleteBooks = () => {
  console.log(deleteBookList.value)
  mybookList.value.forEach((book)=> {
    if(!deleteBookList.value.includes(book.isbn)){
      newbookList.value.push(book)
    }
  })

  console.log("도서 삭제를 완료하였다.")
  deleteBookList.value = []
}

const clickbutton = () => { 
  store.toggledeletebutton();
}

// 얜 이게 끝
const cancelDelete = () => {
  store.toggledeletebutton();
}

const deleteState = computed(() => {
  return store.deletebuttonState
})



onMounted(()=> {
  selectWatch;
})
</script>

<style scoped>

</style>