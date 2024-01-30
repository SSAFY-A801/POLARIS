<template>
  <div class="bg-backgroundgray inline-block rounded-lg px-4  py-1 shadow-sm shadow-stone-400 hover:bg-gray-200">
    <div v-if="deleteBooks == true" class="flex">
      <input id="default-checkbox" 
        type="checkbox"
        v-model="deleteBook"
        @click="emitDeleteState"
         class="w-5 h-5 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
    </div>
    <button 
      @click="showMybookdetail"
      :class="{'pointer-events-none cursor-not-allowed': deleteBooks}">
      <img
          alt="Home"
          src="https://images.unsplash.com/photo-1592496431122-2349e0fbc666?crop=entropy&cs=srgb&fm=jpg&ixid=M3w0Mzc0NDd8MHwxfHNlYXJjaHwyfHxib29rJTIwY292ZXJ8ZW58MHx8fHwxNzA2MDE3NDQyfDA&ixlib=rb-4.0.3&q=85&q=85&fmt=jpg&crop=entropy&cs=tinysrgb&w=450"
          class="h-64 w-full rounded-md object-cover"
      >
      <!-- 도서상태 -->
      <div class="mt-4 flex gap-2 text-xs">
        <div id="isOwned" class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2 border  bg-maintheme2">
            <p  class="p-1.5 text-lightgray">{{ bookinfo.isOwned }}</p>
        </div>
        <div id="isOpened" class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2   bg-maintheme2">
            <p  class="p-1.5 text-lightgray">{{ bookinfo.isOpened }}</p>
        </div>
        <!-- 보유 & 공개 일때만 거래 여부 출력 -->
        <div v-if="bookinfo.isOwned =='보유' && bookinfo.isOpened=='공개'" id="tradeType">
          <div v-if="bookinfo.tradeType =='TRADE'" id="trade"  class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2  bg-red-600"  >
            <p  class="p-1.5 text-lightgray">판매</p>
          </div>
          <div v-else-if="bookinfo.tradeType == 'EXCHANGE'" id="exchange"   class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2 bg-yellow-500">
            <p  class="p-1.5 text-lightgray">교환</p>
          </div>
        </div>
      </div>
      <div class="mt-2">
          <dl>
            <div>
                <dt class="sr-only">Title</dt>

                <dd class="font-bold text-maintheme1">{{ bookinfo.title }}</dd>
            </div>
            <div>
                <dt class="sr-only">Author</dt>

                <dd class="font-normal text-maintheme1">{{ bookinfo.author }}</dd>
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
(e: 'deleteBooks', isbn: string, deleteBook: boolean): void
}>()

const emitDeleteState = () => {
  emit('deleteBooks', bookinfo.isbn, !deleteBook.value)
}


  const deleteBooks = computed(() => {
  return store.deletebuttonState
})

const showMybookdetail = () => {
  // params 추가
  router.push({name: 'BookDetailPage' })
}

watch(deleteBooks,(newValue) => {
  deleteBook.value = !newValue
})

</script>


<style scoped>
  #isOwned,
  #isOpened,
  #trade,
  #exchange {
    @apply rounded-md
  }

  button {
    height: 380px;
  }

</style>