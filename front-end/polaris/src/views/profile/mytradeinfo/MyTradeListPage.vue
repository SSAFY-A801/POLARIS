<template>
  <Navvar></Navvar>
    <div class="container mx-auto mt-48 max-w-6xl font-[gowun-dodum]">
      <div class="flex justify-between">
      <h2 id="title" class="ml-4 my-2 text-xl underline underline-offset-8 decoration-maintheme1">나의 판매/구매 내역</h2>
      <button @click="backtoProfile" id="goback">
        뒤로가기
      </button>
    </div>
      <MyTradeList
      :tradehistory="Tradehistory"
      />
    </div>
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import MyTradeList from '@/components/profile/mytrade/MyTradeList.vue';
import { onMounted, ref } from 'vue';
import { profileCounterStore } from '@/stores/profilecounter';
import axiosInstance from '@/services/axios';
import { useRouter } from 'vue-router';
import type { TradeInfo } from '@/stores/profilecounter';

type TradeHistory = {
  [key:number]: TradeInfo[]
}

const router = useRouter();
const store = profileCounterStore();
const Tradehistory = ref<TradeHistory>({})
const loginUserId = Number(JSON.parse(localStorage.getItem('user_info')||"").id)

const backtoProfile = () => {
    router.push({name: "ProfilePage"});
  }


onMounted(()=> {
  axiosInstance.value({
    headers: {
      Authorization:`${store.token}`,
      "Content-Type": 'application/json'
    },
    method: 'get',
    url: `${store.BACK_API_URL}/trade/${loginUserId}/purchase_history`

  })
  .then((response) => {
    console.log(response.data)
    const res = response.data.data['purchaseHistories']
    res.forEach((trade:TradeInfo) => {
      if(trade['tradeId'] in Tradehistory.value){
        Tradehistory.value[trade['tradeId']].push(trade)
      } else {
        Tradehistory.value[trade['tradeId']] = [trade]
      }
    });
    console.log(Tradehistory.value)
  })
  .catch((error)=> {
    console.error(error);
    
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