<template>
  <h2 class="ml-4 my-2 text-xl underline underline-offset-8 decoration-indigo-500">나의 독후감</h2>
  <div v-if="myEssays.length">
    <MyEssayList :myessays="myEssays" />
  </div>
  <div v-else class="m-20 text-center text-xl">현재 작성한 독후감이 존재하지 않습니다.</div>
</template>

<script setup lang="ts">
import MyEssayList from '@/components/profile/myessay/MyEssayList.vue'
import axiosInstance from '@/services/axios'
import { onMounted, ref } from 'vue'
import { profileCounterStore } from '@/stores/profilecounter'

const myEssays = ref([])
const store = profileCounterStore()
const loginUserId = JSON.parse(localStorage.getItem('user_info') || '').id

onMounted(() => {
  axiosInstance
    .value({
      headers: {
        Authorization: `${store.token}`,
        'Content-Type': 'application/json'
      },
      method: 'get',
      url: `${store.BACK_API_URL}/profile/${loginUserId}/essay`
    })
    .then((response) => {
      // console.log(response.data)
      const res = response.data.data
      myEssays.value = res.myEssays
    })
    .catch((error) => {
      console.error(error)
    })
})
</script>

<style scoped>
h2 {
  @apply mt-6;
}
</style>