<template>
  <div class="flex grid grid-cols-12 items-center">
    <div id="book-info" class="col-span-5 lg:col-span-6">
      <div class="mt-1.5">{{ bookcartInfo.title }}</div>
    </div>
    <div class="flex justify-around col-span-7 lg:col-span-6">
      <div class="mt-1.5">
        공개
        <input @click="openToggle" v-model="isOpened" id="open-book" type="checkbox"  class="w-4 h-4 mx-2 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-600 focus:ring-2 dark:bg-gray-400 dark:border-gray-300">    
        보유
        <input @click="ownToggle" v-model="isOwned" id="own-book" type="checkbox"  class="w-4 h-4 mx-2 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-600 focus:ring-2 dark:bg-gray-400 dark:border-gray-300">    
        <!-- 보유, 공개 여부 checkbox 추가 -->
      </div>  
        <div id="delete-book-cart">
          <button @click.prevent="deleteBookcart(bookcartInfo.isbn)" id="delete-book-cart-button" class="w-14">
            <font-awesome-icon icon="fa-solid fa-trash-can" style="color: #323f59;"/>          </button>
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import { profileCounterStore } from '@/stores/profilecounter';
  import type { Book } from '@/stores/profilecounter';
  type BookCartinfo = {
  bookcart: Book;
};
  const { bookcart } = defineProps<BookCartinfo>();
  const bookcartInfo = ref<typeof bookcart>(bookcart); 
  const isOpened = ref<boolean>(false);
  const isOwned = ref<boolean>(false);
  const store = profileCounterStore();

  const deleteBookcart = (isbn: string) => {
    store.deleteBookitem(isbn);
  }


  const openToggle = () => {
    isOpened.value = !isOpened.value
  }

  const ownToggle = () => {
    isOwned.value = !isOwned.value
  }
</script>

<style scoped>
  #delete-book-cart-button {
      @apply bg-stone-50 border text-white m-[2px] px-1 py-[3px] rounded-[10px] shadow hover:bg-stone-200;
  }
</style>