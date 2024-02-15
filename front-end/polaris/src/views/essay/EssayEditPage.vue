<template>
  <Navvar></Navvar>
  <div v-if="essay" class="flex justify-center mt-40 font-[gowun-dodum]">
    <div
      id="app"
      class="container relative border-2 shadow-gray-300 shadow-lg border-maintheme1 w-full m-16 rounded-lg max-w-4xl p-8"
    >
      <div class="flex justify-between">
        <div class="text-2xl font-bold mb-6">독후감 수정</div>
        <button @click="goback()" id="back" class="p-2 mb-4">취소</button>
      </div>
      <div class="grid grid-cols-12 gap-8">
        <div class="text-lg col-span-2 font-semibold">제목</div>
        <input
          id="title"
          class="col-span-10 p-2 border-maintheme1 border shadow-md h-10 rounded-lg"
          v-model="essay.title"
          type="text"
        />
        <div class="text-lg col-span-2 font-semibold">도서 선택</div>
        <input
          disabled
          class="col-span-10 border shadow-md h-10 p-2 border-maintheme1 rounded-lg"
          type="text"
          :placeholder="essay?.bookTitle"
        />
        <div class="text-lg col-span-2 font-semibold">내용</div>
        <div class="col-span-10">
          <Editor v-model="essay.content" />
        </div>
        <div class="col-span-2 text-lg font-semibold">공개 여부</div>
        <div class="mt-1">
          <label class="relative items-center cursor-pointer">
            <input
              @click="toggleOpened"
              type="checkbox"
              v-model="essay.isOpened"
              class="sr-only peer"
            />
            <div
              class="after:bg-white after:border after:rounded-full w-11 h-6 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:h-5 after:w-5 after:transition-all dark:border-gray-600"
              :class="{
                'bg-gray-200 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full':
                  !essay.isOpened,
                'bg-blue-600 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:bg-blue-600':
                  essay.isOpened
              }"
            ></div>
          </label>
        </div>
      </div>
      <button id="edit-essay" class="p-2 m-2 absolute right-5 bottom-5" @click="editEssay">
        <font-awesome-icon icon="pen-to-square" />
        독후감 수정
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { onMounted, ref } from 'vue'
import Editor from '../../components/essay/TiptapEditor.vue'
import { useRouter, useRoute } from 'vue-router'
import axiosInstance from '@/services/axios'
import type { Essay } from '@/stores/essaycounter'
import { essayStore } from '@/stores/essaycounter'
import Swal from 'sweetalert2'

const store = essayStore()
const essay = ref<Essay | null>(null)
const route = useRoute()
const router = useRouter()
const editEssay = () => {
  if (essay.value) {
    axiosInstance
      .value({
        headers: {
          Authorization: `${store.token}`,
          'Content-Type': 'application/json'
        },
        method: 'patch',
        url: `${store.BACK_API_URL}/essay`,
        data: {
          id: essay.value.id,
          title: essay.value.title,
          content: essay.value.content,
          userBookId: essay.value.userBookId,
          isOpened: essay.value.isOpened
        }
      })
      .then((response) => {
        // console.log(response.data)
      })
      .catch((error) => {
        console.error(error)
      })
    Swal.fire({
      icon: 'success',
      title: '독후감이 수정되었습니다.'
    })
    if (!essay.value.isOpened) {
      Swal.fire({
        icon: 'info',
        title: '독후감이 비공개로 전환됩니다.'
      })
      router.push({ name: 'essaylist' })
    } else {
      router.push({ name: 'essaydetail', params: { essayId: essay.value.id } })
    }
  }
}

const goback = () => {
  router.push({ name: 'essaydetail' })
}

const toggleOpened = () => {
  // isOpened.value = !isOpened.value
}

onMounted(() => {
  axiosInstance
    .value({
      headers: {
        Authorization: `${store.token}`,
        'Content-Type': 'application/json'
      },
      method: 'get',
      url: `${store.BACK_API_URL}/essay/${route.params.essayId}`
    })
    .then((response) => {
      // console.log(response.data)
      essay.value = response.data.data
    })
    .catch((error) => {
      console.error(error)
    })
})
</script>

<style lang="scss" scoped>
/* Basic editor styles */
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../../public/GowunDodum-Regular.ttf');
}

#title {
  @apply bg-backgroundgray;
}

#back,
#edit-essay {
  @apply bg-maintheme1 text-white  shadow-lg rounded-md font-semibold;
}
.tiptap {
  > * + * {
    margin-top: 0.75em;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }
}

.content {
  padding: 1rem 0 0;

  h3 {
    margin: 1rem 0 0.5rem;
  }

  pre {
    border-radius: 5px;
    color: #333;
  }
}
</style>