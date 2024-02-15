<template>
  <Navvar></Navvar>
  <div class="container mx-auto mt-48 max-w-6xl font-[gowun-dodum]">
    <div class="flex justify-between">
      <h2 id="title" class="ml-4 my-2 text-xl underline underline-offset-8 decoration-maintheme1">
        나의 교환 내역
      </h2>
      <button @click="backtoProfile" id="goback">뒤로가기</button>
    </div>
    <div v-if="Object.keys(Exchangehistory).length">
      <MyExchangeList :exchangehistory="Exchangehistory" />
    </div>
    <div v-else class="text-center mt-10 text-xl">교환 내역이 존재하지 않습니다.</div>
  </div>
</template>


<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import MyExchangeList from '@/components/profile/myexchange/MyExchangeList.vue'
import { onMounted, ref } from 'vue'
import { profileCounterStore } from '@/stores/profilecounter'
import axiosInstance from '@/services/axios'
import { useRouter } from 'vue-router'
import type { ExchangeInfo } from '@/stores/profilecounter'

type ExchangeHistory = {
  [key: number]: ExchangeInfo[]
}

const router = useRouter()
const store = profileCounterStore()
const Exchangehistory = ref<ExchangeHistory>({})
const loginUserId = Number(JSON.parse(localStorage.getItem('user_info') || '').id)

const backtoProfile = () => {
  router.push({ name: 'ProfilePage' })
}

onMounted(() => {
  axiosInstance
    .value({
      headers: {
        Authorization: `${store.token}`,
        'Content-Type': 'application/json'
      },
      method: 'get',
      url: `${store.BACK_API_URL}/trade/${loginUserId}/exchange_history`
    })
    .then((response) => {
      // console.log(response.data)
      const res = response.data.data['exchangeHistories']
      res.forEach((exchange: ExchangeInfo) => {
        if (exchange['tradeId'] in Exchangehistory.value) {
          Exchangehistory.value[exchange['tradeId']].push(exchange)
        } else {
          Exchangehistory.value[exchange['tradeId']] = [exchange]
        }
      })
      // console.log(Exchangehistory.value)
    })
    .catch((error) => {
      console.error(error)
    })
})
</script>

<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../../public/GowunDodum-Regular.ttf');
}

#goback {
  @apply w-auto hover:bg-gray-500 bg-[#323F59] text-white m-[5px] px-3 py-[5px] rounded-[10px];
}
</style>