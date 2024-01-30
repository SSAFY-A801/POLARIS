<template>
  <BookCartListItem
  v-for="(item,index) in bookcartList" 
  :key="index"
  :bookcart="item"
  @update-book-status="updateBookStatus"
  @delete-bookitem="deleteBookitem"
    class="border p-3"
  />
</template>

<script setup lang="ts">
  import BookCartListItem from './BookCartListItem.vue';
  import { onMounted, ref } from 'vue';
  import { profileCounterStore } from '@/stores/profilecounter';
  import type { Book } from '@/stores/profilecounter';
  

  const store = profileCounterStore();
  const bookcartList = ref<Book[]>(store.bookCartList)

  const deleteBookitem = (isbn:string)=> {
    bookcartList.value = bookcartList.value.filter((book)=> book.isbn != isbn)
    console.log(bookcartList.value)
    return bookcartList.value
  }

  const updateBookStatus = (isbn:string, isOpened:boolean|string, isOwned:boolean|string ) => {
    bookcartList.value.forEach((bookinfo)=> {
      bookinfo.tradeType = 'UNDEFINED';
      if (bookinfo.isbn == isbn){
        if(isOpened == true){
          bookinfo.isOpened = "공개";
        } else {
          bookinfo.isOpened = "비공개";
        }
        if(isOwned == true){
          bookinfo.isOwned = "보유";
        } else {
          bookinfo.isOwned = "미보유";
        }

      }
    })
  }

onMounted(()=> {
})
</script>

<style scoped>

</style>