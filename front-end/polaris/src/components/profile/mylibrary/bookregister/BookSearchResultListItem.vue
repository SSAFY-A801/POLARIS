<template>
    <!-- 검색결과 component -->
    <div class="grid grid-cols-12 items-center">
      <div id="book-image"  class="col-span-2">
        <div class="mr-2">
          <img class="p-2" src="@\assets\book-image.jpg" alt="book-image">
        </div>
      </div>
      <div id="book-info" class="col-span-8">
        <div>{{ searchResult.title }}</div>
      </div>
      <div id="register-book-cart" class="col-span-2">
        <button @click="addBookcart(searchResult)" id="register-book-cart-button" class="w-8">
          <font-awesome-icon icon="fa-solid fa-plus" />
        </button>
      </div>
    </div>
    
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { profileCounterStore } from '@/stores/profilecounter';
import type { Book } from '@/stores/profilecounter'
type SearchBookinfo = {
  searchResult: Book
};

const emit = defineEmits(['showAlert'])
const emitShowAlert = () => {
  emit('showAlert');
}

const { searchResult } = defineProps<SearchBookinfo>();
const store = profileCounterStore();
const bookCart = ref<Array<SearchBookinfo['searchResult']>>(store.bookCartList);
const addBookcart = (searchResult: SearchBookinfo['searchResult']) => {
  const included = bookCart.value.some(item => item.isbn === searchResult.isbn)
  if (!included) {
    bookCart.value.push(searchResult);
  } else {
    emitShowAlert();
  }
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