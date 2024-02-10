<template>
  <div class="mt-16 flex justify-center">
    <div class="container w-full mt-8 max-w-6xl bg-backgroundgray p-4">
      <!-- 최상단 -->
      <div class="flex justify-between items-center p-2 m-2 border-b  border-black">
        <div class="text-2xl font-bold">독후감 게시판</div>
        <button @click="writeEssay" id="write-essay">독후감 작성</button>
      </div>
      <!-- 검색바 -->
      <div class="flex flex-wrap flex-col md:flex-row justify-end border-b-2 pb-4 mb-8">
        <div class="relative inline-block">
          <!-- md:hidden: 화면이 중간(medium) 크기 이하일 때(select 엘리먼트가 hidden 됨) -->
          <!-- {{  filter }} -->
          <select v-model="filter" id="search-filter" class="m-2 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
            <option selected>도서제목</option>
            <option>독후감제목</option>
            <option>작성자</option>
          </select>
        </div>
        <div>
          <input @keyup.enter="essaySearch(keyword,filter)" v-model="keyword" type="text" id="book"  class="w-96 rounded-lg appearance-none border border-maintheme1 shadow-md py-2 px-4 m-2 bg-gray-50 text-maintheme1 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-gray-600 focus:border-transparent" placeholder="독후감 검색"/>
          <button @click="essaySearch(keyword, filter)" type="button" class="w-16 text-white mx-2 md:mr-2 md:w-16 py-2 px-3 my-2 md:my-0 bg-maintheme1 hover:bg-gray-500 rounded-lg">
            <font-awesome-icon icon="fa-solid fa-magnifying-glass" size="xl"/>
          </button>
        </div>
      </div>
      <div class="grid grid-cols-2 gap-8 sm:grid-cols-4">
        <EssayList :essayList="essayList" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import EssayList from '../../components/essay/EssayList.vue';
import axios from 'axios';
import { essayStore } from '@/stores/essaycounter';
import type { Essay } from '@/stores/essaycounter';

const router = useRouter();
const filter = ref("")
const keyword = ref("")
const store = essayStore();
const essayList = ref<Essay[]>([])

const writeEssay = () => {
  router.push({name: 'essaycreate'})
}

const essaySearch = (keyword: string, filter: string) => {
  const key = ref<string>("")
  if(filter != ""){
    const searchCondition = {
      "도서제목": "bookTitle",
      "독후감제목": "title",
      "작성자": "user"
    } as { [key: string]: string };
    key.value = searchCondition[filter]
  } 
  console.log(keyword, key.value)
  axios({
    headers: {
      "Content-Type": 'application/json'
    },
    method: 'get',
    url: `${store.BACK_API_URL}/essay`,
    params: {
      pgno: 1,
      spp: 20,
      key: "",
      word: ""
    }

  })
  .then((response) => {
    // console.log(response.data)
    const res = response.data.data
    essayList.value = res
    console.log(essayList.value)
  })
  .catch((error)=> {
    console.error(error)
  })

}

onMounted(()=> {

})

</script>

<style scoped>


#write-essay {
    @apply bg-[#323F59] border text-white font-bold m-[5px] px-5 py-[10px] rounded-[10px]  hover:bg-gray-500;
}
</style>