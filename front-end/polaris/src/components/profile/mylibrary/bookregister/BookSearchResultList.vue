<template>

  <div class="border">
    <BookSearchResultListItem 
      v-for="(item,index) in searchListResult"
      :key="index"
      :searchResult="item"
      @show-alert="handleShowAlert"
       class="border p-3"
       />
  </div>
</template>

<script setup lang="ts">
  import BookSearchResultListItem from "./BookSearchResultListItem.vue";
  import { profileCounterStore } from "@/stores/profilecounter";

  const store = profileCounterStore();
  const searchListResult = store.searchbookLists
  const handleShowAlert = () => {
    alert('이미 포함된 도서입니다.')
  }
</script>

<style scoped>
  img {
    height: 70px;
    width: 60px;
  }

  #cancel-register-book-button {
    @apply bg-white font-bold text-maintheme1 rounded-[5px] py-1 px-2 border border-gray-400 hover:bg-gray-300;
  }

  #register-book-cart-button, 
  #complete-register-book-button {
      @apply bg-[#323F59] border text-white m-[2px] px-1 py-[3px] rounded-[10px] border-solid border-[black] hover:bg-gray-500;
  }

</style>