<template>
    <!-- 검색결과 component -->
    <div class="grid grid-cols-12 items-center">
      <div id="book-image"  class="col-span-2">
        <div class="mr-2">
          <img class="p-2" :src="searchResult.cover" alt="book-image">
        </div>
      </div>
      <div id="book-info" class="col-span-9">
        <div class="mb-2 font-semibold">{{ searchResult.title }}</div>
        <div>{{ searchResult.author }}</div>
      </div>
      <div id="register-book-cart" class="col-span-1">
        <button @click="addBookcart(searchResult)" id="register-book-cart-button" class="w-8">
          <font-awesome-icon icon="fa-solid fa-plus" />
        </button>
      </div>
    </div>
    
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import { profileCounterStore } from '@/stores/profilecounter';
import type { Searchbook } from '@/stores/profilecounter'
type SearchBookinfo = {
  searchResult: Searchbook
};

const emit = defineEmits(['showAlert'])
const emitShowAlert = () => {
  emit('showAlert');
}

const { searchResult } = defineProps<SearchBookinfo>();
const store = profileCounterStore();
const bookCartList = computed(()=> {
  return store.bookCartList
})

const mybookLists = computed(()=> {
  return store.mybookLists
})

const addBookcart = (searchResult: SearchBookinfo['searchResult']) => {
  const bookcartIncluded = bookCartList.value.some(item => item.isbn === searchResult.isbn)
  const libraryIncluded = mybookLists.value.some(item => item.isbn === searchResult.isbn)
  const included = libraryIncluded || bookcartIncluded
  if (included) {
    emitShowAlert();
    // console.log('현재 도서바구니목록: ',store.bookCartList)
  } else {
    searchResult.isOpened = false
    searchResult.isOwned = false
    bookCartList.value.push(searchResult);
  }
  // console.log(searchResult)
};
</script>

<style scoped>
  img {
    height: 70px;
    width: 60px;
  }


  #register-book-cart-button {
    @apply bg-stone-50 border m-[2px] px-1 py-[3px] rounded-[10px] shadow hover:bg-stone-200;
  }
</style>