<template>
  <BookCartListItem
  v-for="(item,index) in bookcartList" 
  :key="index"
  :bookcart="item"
    class="border p-3"
  />
</template>

<script setup lang="ts">
  import BookCartListItem from './BookCartListItem.vue';
  import { ref } from 'vue';
  import { profileCounterStore } from '@/stores/profilecounter';
  import type { Book } from '@/stores/profilecounter';
  

  const store = profileCounterStore();
  const bookcartList = ref<Book[]>(store.bookCartList)

</script>

<style scoped>

</style>