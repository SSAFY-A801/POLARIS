<template>
    <!-- 모달 제작이 되면 페이지를 없애고 다 모달로 옮길 예정-->
  <div id="register-book"  class="container md:mx-auto min-w-[600px] mt-8 max-w-6xl border rounded-lg h-[600px]">
    <!-- header -->
    <div class="flex justify-between rounded-t-lg bg-maintheme1 p-2 h-[50px]">
      <div class="text-white text-xl ml-3">
        도서 등록
      </div>
      <div>
        <button @click="cancelRegister" id="cancel-register-book-button">
          <font-awesome-icon icon="fa-solid fa-xmark" size="xl" />        
        </button>
      </div>
    </div>
    <div class="grid grid-cols-12 gap-4 p-3 border border-black h-[550px]" >
        <!-- 도서 검색 -->
        <div id="register-book" class="col-span-6 border border-maintheme1 rounded-lg">
          <!-- 검색 바 -->
          <p>{{  keyword }}</p>
          <div class="flex flex-wrap flex-col md:flex-row justify-end">
              <input v-model="keyword" type="text" id="book"  class="w-auto rounded-lg appearance-none border border-gray-500 py-2 px-4 m-2 bg-gray-50 text-maintheme1 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-gray-600 focus:border-transparent" placeholder="도서 검색"/>
              <div  class="flex items-center mt-4 md:mt-0">
                <div class="relative inline-block">
                  <!-- md:hidden: 화면이 중간(medium) 크기 이하일 때(select 엘리먼트가 hidden 됨) -->
                  {{  filter }}
                  <select v-model="filter" id="search-filter" class="border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                    <option selected>도서 제목</option>
                    <option>저자</option>
                    <option>출판사</option>
                  </select>
                </div>
                <button @click="searchAPIbook(keyword,filter)" type="button" class="w-full text-white mx-2 md:ml-2 md:w-auto py-1 px-3 my-2 md:my-0 bg-maintheme1 hover:bg-gray-500 rounded-lg">
                  <font-awesome-icon icon="fa-solid fa-magnifying-glass"/>                </button>
              </div>
          </div>
          <!-- 검색 결과 목록 -->
          <BookSearchResultList/>
        </div>

        <!-- 도서바구니 -->
        <div id="book-cart" class="relative col-span-6 border border-maintheme1 rounded-t-lg">
          <!-- 도서바구니 header -->
          <div id="cart-header" class="flex rounded-t-md text-maintheme1 font-bold p-2">
            <div class="items-center text-lg">
              도서바구니
              <font-awesome-icon icon="fa-solid fa-cart-shopping" />
            </div>
          </div>
          <!-- 도서바구니 목록 -->
          <form action="">
              <!-- 도서바구니 BookCartListItem -->
              <div>
                <BookCartList/>
              </div>
            <!-- 담기완료 button 행 -->
            <div class="border">
              <div class="absolute bottom-2 right-2" >
                <button @click="submitForm" type="submit" id="complete-register-book-button">
                  등록
                  <font-awesome-icon icon="fa-solid fa-circle-check" style="color: white;"/>
                </button>
              </div>
            </div>
          </form>
        </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router'
import BookCartList from '@/components/profile/mylibrary/bookregister/BookCartList.vue';
import BookSearchResultList from '@/components/profile/mylibrary/bookregister/BookSearchResultList.vue';
import { profileCounterStore } from '@/stores/profilecounter';

const keyword = ref<string>("")
const filter = ref<string|null>(null)
const store = profileCounterStore();
const router = useRouter()
const mybookLists = ref(store.mybookLists)
const bookCartList = ref(store.bookCartList)


const searchAPIbook = (keyword:string, filter: string|null) => {
  store.searchAPIbookList(keyword, filter);
}

const cancelRegister = () => {
  router.push({name: "MyLibraryPage"})
}

const submitForm = () => {
  bookCartList.value.forEach((bookcart)=> {
    mybookLists.value.push(bookcart)
  })
  console.log(mybookLists.value)
  
  router.push({name: "MyLibraryPage"})
}

onMounted(()=> {
})
</script>


<style scoped>
#cart-header {
  height: 50px;
}
img {
  height: 70px;
  width: 60px;
}

#cancel-register-book-button {
  @apply bg-white font-bold text-maintheme1 rounded-[5px] py-1 px-2 border border-gray-400 hover:bg-gray-300;
}

#complete-register-book-button {
    @apply bg-[#323F59] border text-white m-[2px] px-3 py-2 rounded-[15px] hover:bg-gray-500 text-lg;
}

</style>