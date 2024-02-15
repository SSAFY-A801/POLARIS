<template>
  <button v-if="!deleteState && isMe" @click="registerBook" id="Book-register" class="relative shadow hover:bg-gray-100 border border-gray-200 rounded-md p-2">
    <div>
      <font-awesome-icon icon="fa-solid fa-plus" size="8x" style="color: #323F59;" />     
    </div>
  </button>


      <MyLibraryListItem
      v-for="(mybook, index) in props.mybookList"
      :key = "index"
      :bookinfo = "mybook"
      @delete-books="deleteBookstate" 
      />
</template>

<script setup lang="ts">
import MyLibraryListItem from './MyLibraryListItem.vue';
import { profileCounterStore } from '@/stores/profilecounter';
import { ref, computed, onMounted } from 'vue'; 
import { useRouter } from 'vue-router'

const props = defineProps(['mybookList','isMe']);

type DeleteBook = {
  id: number
}

const router = useRouter();
const store = profileCounterStore()
const deleteBookList = ref<DeleteBook[]>(store.deleteBookList)
const deleteState = computed(() => {
  return store.deletebuttonState
})

function registerBook() {
  router.push({name: "BookRegisterPage"});
}


const deleteBookstate = (bookId: number, deleteBook: boolean) => {
  if (deleteBook) {
    const included = ref(false)
    deleteBookList.value.forEach((book)=> {
    if(book['id'] == bookId){
        included.value = true
      } 
    })
    if (!included.value) {
      deleteBookList.value.push({"id" : bookId});
    }
  } else {
    // If deleteBook is false, filter out the book with a matching ISBN
    deleteBookList.value = deleteBookList.value.filter(
      (book) => book['id'] !== bookId
    );
  }
  // console.log(deleteBookList.value)
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