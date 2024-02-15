<template>
  <Navvar></Navvar>
  <div class="flex justify-center mt-40 font-[gowun-dodum]">
    <div
      id="app"
      class="container relative border-4 shadow-gray-300 shadow-lg border-maintheme1 w-full m-16 rounded-lg max-w-4xl p-8"
    >
      <div class="flex justify-between">
        <div class="text-2xl font-bold mb-6">독후감 쓰기</div>
        <button @click="goback()" id="back" class="p-2 mb-4">뒤로 가기</button>
      </div>
      <div class="grid grid-cols-12 gap-8">
        <div class="text-lg col-span-2 font-semibold">제목</div>
        <input
          id="title"
          class="col-span-10 border-maintheme1 border-b h-10 p-2"
          v-model="title"
          type="text"
          placeholder="독후감 제목"
        />
        <div class="text-lg col-span-2 font-semibold">도서 선택</div>
        <div class="col-span-10">
          <select
            v-if="essayBooks.length"
            v-model="selectedBook"
            id="book-options"
            class="input-field w-full border-b border-maintheme1 shadow-md h-10"
          >
            <option v-for="essaybook in essayBooks" :key="essaybook.id" :value="essaybook.id">
              {{ essaybook.title }} - {{ essaybook.author }}
            </option>
          </select>
          <div v-else class="text-sm text-gray-400">
            현재 보유한 도서가 존재하지 않습니다. 서재에 등록 후 독후감을 작성해 주세요.
          </div>
        </div>
        <div class="text-lg col-span-2 font-semibold">내용</div>
        <div class="col-span-10">
          <Editor v-model="content" />
        </div>
        <div class="col-span-2 text-lg font-semibold">공개 여부</div>
        <div class="mt-1">
          <label class="relative items-center cursor-pointer">
            <input @click="toggleOpened" type="checkbox" v-model="isOpened" class="sr-only peer" />
            <div
              class="after:bg-white after:border after:rounded-full w-11 h-6 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:h-5 after:w-5 after:transition-all dark:border-gray-600"
              :class="{
                'bg-gray-200 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full':
                  !isOpened,
                'bg-blue-600 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:bg-blue-600':
                  isOpened
              }"
            ></div>
          </label>
        </div>
      </div>
      <button id="create-essay" class="p-2 m-2 absolute right-5 bottom-5" @click="writeEssay">
        <font-awesome-icon icon="pen-to-square" />
        독후감 작성
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { onMounted, ref, computed, watch } from 'vue'
import Editor from '../../components/essay/TiptapEditor.vue'
import { useRouter } from 'vue-router'
import { profileCounterStore } from '@/stores/profilecounter'
import type { Book } from '@/stores/profilecounter'
import axiosInstance from '@/services/axios'
import axios from 'axios'
import Swal from 'sweetalert2'

const profileStore = profileCounterStore()
const loginUserId = ref('')
const mybookList = ref<Book[]>([])
const token = ref(localStorage.getItem('user_token'))
const BACK_API_URL = profileStore.BACK_API_URL
const essayBooks = ref<Book[]>([])

watch(mybookList, (newList) => {
  essayBooks.value = newList.filter((book) => book.isOwned == true)
})

const title = ref('')
const isOpened = ref(true)
const router = useRouter()
const selectedBook = ref<Book | null>(null)
const content = ref(``)

const writeEssay = () => {
  axiosInstance
    .value({
      headers: {
        Authorization: `${token.value}`,
        'Content-Type': 'application/json'
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
      // console.log(response.data)
      const essaydetail = response.data.data
      Swal.fire({
        icon: 'success',
        title: '독후감이 작성되었습니다.'
      })
      router.push({ name: 'essaydetail', params: { essayId: essaydetail.id } })
    })
    .catch((error) => {
      console.error(error)
      Swal.fire({
        icon: 'error',
        text: '해당 도서로 작성하신 독후감이 이미 존재합니다.\n다른 도서의 독후감을 작성해주세요.'
      })
    })
}

const goback = () => {
  router.push({ name: 'essaylist' })
}

const toggleOpened = () => {
  isOpened.value = !isOpened.value
}

onMounted(() => {
  loginUserId.value = JSON.parse(localStorage.getItem('user_info') || '').id
  // 로그인 유저의 서재목록 조회
  axiosInstance
    .value({
      headers: {
        Authorization: `${token.value}`
      },
      method: 'get',
      url: `${BACK_API_URL}/book/${loginUserId.value}/library`
    })
    .then((response) => {
      const res = response.data.data
      if (res['books']) {
        mybookList.value = res['books']
      } else {
        mybookList.value = []
      }
    })
    .catch((error) => {
      console.error('에러발생: ', error)
    })
})
</script>

<style lang="scss" scoped>
/* Basic editor styles */
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../../public/GowunDodum-Regular.ttf');
}

#back,
#create-essay {
  @apply bg-maintheme1 text-white  shadow-lg rounded-md font-semibold;
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
    background: #0d0d0d;
    color: #fff;
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
    border-left: 2px solid rgba(#0d0d0d, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0d0d0d, 0.1);
    margin: 2rem 0;
  }
}
</style>