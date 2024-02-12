<template>
  <Navvar></Navvar>
    <div class="container mx-auto mt-48 max-w-6xl font-[gowun-dodum]">
      <h2 class="ml-4 my-2 text-xl underline underline-offset-8 decoration-indigo-500">나의 판매/구매내역</h2>
      <MyTradeList/>
    </div>
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import MyTradeList from '@/components/profile/mytrade/MyTradeList.vue';
import { onMounted } from 'vue';
import { profileCounterStore } from '@/stores/profilecounter';
import axiosInstance from '@/services/axios';

const store = profileCounterStore();
const loginUserId = JSON.parse(localStorage.getItem('user_info')||"").id


onMounted(()=> {
  axiosInstance.value({
    headers: {
      Authorization:`${store.token}`,
      "Content-Type": 'application/json'
    },
    method: 'get',
    url: `${store.BACK_API_URL}/profile/${loginUserId}/trade-books`

  })
  .then((response) => {
    console.log(response.data)
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
</style>