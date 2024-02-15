<template>
  <div 
  class=" bg-gray-50 inline-block rounded-lg px-4 py-1 shadow-sm shadow-stone-400 hover:bg-gray-100">
    <!-- checkbox -->
    <div v-if="deleteBooks == true" class="flex">
      <input id="default-checkbox" 
        type="checkbox"
        v-model="deleteBook"
        @click="emitDeleteState"
         class="w-5 h-5 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
    </div>
    <button 
      @click="showMybookdetail"
      class="container"
      :class="{'pointer-events-none cursor-not-allowed': deleteBooks}">
      <div class="flex justify-center py-2 rounded-lg">
        <div v-if="bookinfo.cover">
          <img
              id="book-cover"
              alt="Home"
              :src="bookinfo.cover"
              class="rounded-md object-cover"
          >
        </div>
        <div v-else>
          <img
              alt="Home"
              src="@\assets\book-image.jpg"
              class="rounded-md object-cover"
          >
        </div>
      </div>
      <!-- 도서상태 -->
      <div class="my-4 flex justify-center gap-2 text-xs">
        <div id="isOwned" class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2 border  bg-maintheme2">
          <p v-if="bookinfo.isOwned" class="p-1.5 text-lightgray"> 보유 </p>
          <p v-else  class="p-1.5 text-lightgray"> 미보유 </p>
        </div>
        <div id="isOpened" class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2   bg-maintheme2">
          <p v-if="bookinfo.isOpened" class="p-1.5 text-lightgray">공개</p>
          <p v-else  class="p-1.5 text-lightgray">비공개</p>
        </div>
        <!-- 보유 & 공개 일때만 거래 여부 출력 -->
        <div v-if="bookinfo.userBookTradeType =='PURCHASE'" id="trade"  class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2  bg-red-600"  >
          <p  class="p-1.5 text-lightgray">판매</p>
        </div>
        <div v-else-if="bookinfo.userBookTradeType == 'EXCHANGE'" id="exchange"   class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2 bg-yellow-500">
          <p  class="p-1.5 text-lightgray">교환</p>
        </div>
        <div v-else id="undefined"   class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2 bg-gray-500">
          <p  class="p-1.5 text-lightgray">미거래</p>
        </div>
      </div>
      <div class="mt-2">
          <dl>
            <div class="mb-2">
                <dt class="sr-only">Title</dt>
                
                <dd v-if="bookinfo.title.length > 30" class="font-bold text-maintheme1">{{ bookinfo.title.slice(0,30) }}...</dd>
                <dd v-else  class="font-bold text-maintheme1">{{ bookinfo.title }}</dd>
            </div>
            <div>
                <dt class="sr-only">Author</dt>

                <dd  class="font-normal text-maintheme1">{{ bookinfo.author.split(',')[0] }}</dd>
            </div>
          </dl>
      </div>
    </button>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import { profileCounterStore } from '@/stores/profilecounter'; 
import { useRouter } from 'vue-router';
import type { Book } from '@/stores/profilecounter';


interface Bookinfo {
  bookinfo: Book
};

const { bookinfo }= defineProps<Bookinfo>();
const router = useRouter()
const store = profileCounterStore();
const deleteBook = ref<boolean>(false)

const emit = defineEmits<{
(e: 'deleteBooks', id: number, deleteBook: boolean): void
}>()


const emitDeleteState = () => {
  emit('deleteBooks', bookinfo.id, !deleteBook.value)
}


  const deleteBooks = computed(() => {
  return store.deletebuttonState
})

const showMybookdetail = () => {
  // params 추가
  console.log(bookinfo.userId)
  router.push({name: 'BookDetailPage', params: { id: bookinfo.userId, isbn: bookinfo.isbn} })

}

watch(deleteBooks,(newValue) => {
  deleteBook.value = !newValue
})

</script>


<style scoped>
  #book-cover {
    @apply h-[180px]
  }

  #isOwned,
  #isOpened,
  #undefined,
  #trade,
  #exchange {
    @apply rounded-md
  }

  button {
    height: auto;
  }

</style>