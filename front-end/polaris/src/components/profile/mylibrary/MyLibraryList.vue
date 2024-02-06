<template>
  <button v-if="!deleteState"  @click="registerBook" id="Book-register" class="shadow hover:bg-gray-100 border border-dashed rounded-md border-deepgray p-2">
    <font-awesome-icon icon="fa-solid fa-plus" size="8x" style="color: #323F59;" />  </button>
  <MyLibraryListItem
  v-for="(mybook, index) in mybookList"
  :key = "index"
  :bookinfo = "mybook"
  @delete-books="deleteBookstate" 
  />
</template>

<script setup lang="ts">
import MyLibraryListItem from './MyLibraryListItem.vue';
import { profileCounterStore, type Book } from '@/stores/profilecounter';
import { ref, computed, onMounted, watch } from 'vue'; 
import { useRouter } from 'vue-router'
const { mybookList } = defineProps(['mybookList']);

const router = useRouter();
const store = profileCounterStore()
const deleteBookList = ref<Book[]>(store.deleteBookList)
const deleteState = computed(() => {
  return store.deletebuttonState
})

function registerBook() {
  router.push({name: "BookRegisterPage"});
}

const deleteBookstate = (bookinfo: Book, deleteBook: boolean) => {
  if (deleteBook) {
    if (!deleteBookList.value.includes(bookinfo)) {
      deleteBookList.value.push(bookinfo);
    }
  } else {
    // If deleteBook is false, filter out the book with a matching ISBN
    deleteBookList.value = deleteBookList.value.filter(
      (book) => book.isbn !== bookinfo.isbn
    );
  }
  console.log(deleteBookList.value)
  store.deleteBookList = deleteBookList.value
};



onMounted(()=> {
})





</script>

<style scoped>
button {
  height: 380px;
}

#Book-register {
    @apply border-2 border-[dashed];
}
</style>