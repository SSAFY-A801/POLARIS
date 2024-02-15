<template>
  <div
    class="relative inline-block rounded-lg px-4 py-1 shadow-sm shadow-stone-400 hover:bg-gray-100"
  >
    <button @click="showEssaydetail" class="container p-2">
      <div id="title" class="text-center mb-4">
        <dd class="font-bold text-maintheme1">{{ essayInfo.title }}</dd>
      </div>
      <div class="flex items-center justify-center border-b text-center pb-2 mb-4">
        <!-- <font-awesome-icon icon="fa-solid fa-user" class="mr-2" /> -->
        <div class="mr-1">
          <img id="profile-image" :src="essayInfo.profileUrl" alt="" />
        </div>
        <div>
          {{ essayInfo.nickname }}
        </div>
      </div>
      <div class="flex justify-center my-2 rounded-lg">
        <div>
          <img
            v-if="essayInfo.bookCoverUrl"
            alt="Home"
            :src="essayInfo.bookCoverUrl"
            class="object-cover"
          />
          <img v-else alt="Home" src="@/assets/book-image.jpg" class="object-cover" />
        </div>
      </div>
      <div class="text-center text-xs mb-4">
        <dd v-if="essayInfo.bookTitle?.length > 30" class="font-normal text-maintheme1">
          <font-awesome-icon icon="fa-solid fa-book-open" class="mr-2" />
          {{ essayInfo.bookTitle?.slice(0, 30) }}...
        </dd>
        <dd v-else>
          <font-awesome-icon icon="fa-solid fa-book-open" class="mr-2" />
          {{ essayInfo.bookTitle }}
        </dd>
        <dd class="mt-1" v-if="essayInfo.bookAuthor">
          / {{ essayInfo.bookAuthor.split(' (')[0] }}
        </dd>
      </div>
      <!-- 도서상태 -->
      <div class="absolute bottom-2 mt-4 grid grid-cols-12 gap-2 text-xs items-center">
        <div class="col-span-4 text-start">
          <p>{{ essayInfo.createdAt.toString().split('T')[0] }}</p>
        </div>
        <div class="flex col-span-4 justify-center">
          <p><font-awesome-icon icon="fa-solid fa-bookmark" style="color: #323f59" /></p>
          <p class="ml-2">{{ localScraps }}</p>
        </div>
        <div class="flex col-span-4 justify-around">
          <p>조회수</p>
          <p>{{ essayInfo.hit }}</p>
        </div>
      </div>
    </button>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import type { Essay } from '@/stores/essaycounter'
import { onMounted, ref } from 'vue'
import axios from 'axios'
import { essayStore } from '@/stores/essaycounter'

interface EssayInfo {
  essayInfo: Essay
}

const { essayInfo } = defineProps<EssayInfo>()

const store = essayStore()
const router = useRouter()
const localScraps = ref(0)

const showEssaydetail = () => {
  router.push({ name: 'essaydetail', params: { essayId: essayInfo.id } })
}

onMounted(() => {
  axios({
    headers: {
      'Content-Type': 'application/json'
    },
    method: 'get',
    url: `${store.BACK_API_URL}/essay/scrap_count/${essayInfo.id}`
  })
    .then((response) => {
      // console.log(response.data)
      localScraps.value = response.data.data
    })
    .catch((error) => {
      console.error(error)
    })
})
</script>

<style scoped>
#profile-image {
  @apply w-[25px] h-[25px] rounded-full border border-gray-500;
}

#title {
  height: 40px;
}

button {
  height: auto;
}

img {
  height: 150px;
}
</style>