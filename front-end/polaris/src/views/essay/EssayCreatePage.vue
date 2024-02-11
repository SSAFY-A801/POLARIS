<template>
  <div class="flex justify-center">
    <div id="app" class="container relative border-2 shadow-gray-300 shadow-lg border-maintheme1 w-full m-16 rounded-lg max-w-4xl p-8">
      <div class="flex justify-between">
        <div class="text-2xl font-bold mb-6"> 독후감 쓰기 </div>
        <button @click="goback()" id="back" class="p-2 mb-4">뒤로 가기</button>
      </div>
      <div class="grid grid-cols-12 gap-8">
        <div class="text-lg col-span-2 font-semibold">제목</div>
        <input id="title" class="col-span-10 border-maintheme1 border shadow-md h-10 p-2 rounded-lg"  v-model="title" type="text">
        <div class="text-lg col-span-2 font-semibold">도서 선택</div>
        <div class="col-span-10">
          <select v-if="essayBooks.length" v-model="selectedBook" id="product-options" class="input-field w-full border border-maintheme1 shadow-md h-10 rounded-lg"> 
            <option v-for="essaybook in essayBooks" :key="essaybook.id" :value="essaybook.id">
            {{ essaybook.title }}
            </option>
          </select>
          <div v-else class="text-sm text-gray-400">
            현재 보유한 도서가 존재하지 않습니다. 서재에 등록 후 독후감을 작성해 주세요.
          </div>
        </div>
        <div class="text-lg col-span-2 font-semibold">내용</div>
        <div class="col-span-10">
          <Editor v-model="content"/>
        </div>
        <div class="col-span-2 text-lg font-semibold">공개 여부</div>
        <div class="mt-1">
          <label class="relative items-center cursor-pointer">
                  <input @click="toggleOpened" type="checkbox" v-model="isOpened" class="sr-only peer">
                  <div class="after:bg-white  after:border after:rounded-full w-11 h-6 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:h-5 after:w-5 after:transition-all dark:border-gray-600" :class="{ 'bg-gray-200 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full': !isOpened, 'bg-blue-600 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:bg-blue-600':isOpened}"></div>  
          </label> 
        </div>
      </div>
      <button id="create-essay" class="p-2 m-2 absolute right-5 bottom-5"  @click="writeEssay">
        <font-awesome-icon icon="pen-to-square" />
        독후감 작성
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, computed } from 'vue';
import Editor from '../../components/essay/TiptapEditor.vue';
import { useRouter } from 'vue-router';
import { profileCounterStore } from '@/stores/profilecounter';
import type { Book } from '@/stores/profilecounter';
import axios from 'axios';


const profileStore = profileCounterStore();
const mybookList = profileStore.mybookLists
const essayBooks = computed(()=> {
  return mybookList.filter((mybook)=> mybook.isOwned == true)
})
const title = ref('')
const isOpened = ref(true)
const router = useRouter();
const selectedBook = ref<Book|null>(null)
const content = ref(``)
const writeEssay = () => {
  axios({
    headers: {
      Authorization: `${profileStore.token}`,
      "Content-Type": 'application/json'
    },

    method: 'post',
    url: `${profileStore.BACK_API_URL}/essay`,
    data: {
      title: title.value,
      content: content.value,
      userBookId: selectedBook.value,
      isOpened: isOpened.value
    }
  })
  .then((response) => {
    console.log(response.data)
    const essaydetail = response.data.data
    alert("독후감 게시!")
    router.push({name: 'essaydetail', params:{essayId: essaydetail.id}})
  })
  .catch((error) => {
    console.error(error)
  })

}

const goback = () => {
  router.push({name: 'essaylist'})
}

const toggleOpened = () => {
    isOpened.value = !isOpened.value
  }

onMounted(()=> {
})



</script>

<style lang="scss" scoped>
/* Basic editor styles */

#title {
  @apply bg-backgroundgray
}

#back,
#create-essay {
  @apply bg-maintheme1 text-white  shadow-lg rounded-md font-semibold
}
.tiptap {
  > * + * {
    margin-top: 0.75em;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }

  pre {
    background: #0D0D0D;
    color: #FFF;
    font-family: 'JetBrainsMono', monospace;
    padding: 0.75rem 1rem;
    border-radius: 0.5rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }
  }

  img {
    max-width: 100%;
    height: auto;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 2px solid rgba(#0D0D0D, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0D0D0D, 0.1);
    margin: 2rem 0;
  }
}
</style>