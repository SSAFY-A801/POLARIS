<template>
  <BookCartListItem
  v-for="(item,index) in bookcartList" 
  :key="index"
  :bookcart="item"
  @update-book-status="updateBookStatus"
    class="border-b p-3"
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
          bookinfo.isOpened = true;
        } else {
          bookinfo.isOpened = false;
        }
        if(isOwned == true){
          bookinfo.isOwned = true;
        } else {
          bookinfo.isOwned = false;
        }
      }
    })
    // console.log(bookcartList.value)

  }

onMounted(()=> {
})
</script>

<style scoped>

</style>