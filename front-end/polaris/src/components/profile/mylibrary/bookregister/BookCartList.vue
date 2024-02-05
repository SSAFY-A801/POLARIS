<template>
  <BookCartListItem
  v-for="(item,index) in bookcartList" 
  :key="index"
  :bookcart="item"
  @update-book-status="updateBookStatus"
    class="border p-3"
  />
</template>

<script setup lang="ts">
  import BookCartListItem from './BookCartListItem.vue';
  import { onMounted, ref, computed } from 'vue';
  import { profileCounterStore } from '@/stores/profilecounter';
  

  const store = profileCounterStore();
  const bookcartList = ref(computed(()=> {
    return store.bookCartList
  }))

  const updateBookStatus = (isbn:string, isOpened:boolean, isOwned:boolean ) => {
    bookcartList.value.forEach((bookinfo)=> {
      if (bookinfo.isbn == isbn){
        if(isOpened == true){
          bookinfo.isOpened = 1;
        } else {
          bookinfo.isOpened = 0;
        }
        if(isOwned == true){
          bookinfo.isOwned = 1;
        } else {
          bookinfo.isOwned = 0;
        }
      }
    })
    console.log(bookcartList.value)

  }

onMounted(()=> {
})
</script>

<style scoped>

</style>