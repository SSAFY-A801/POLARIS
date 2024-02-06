<template>
  <div class="container mx-auto mt-28 max-w-6xl">
    <h2 class="ml-4 my-2 text-xl underline underline-offset-8 decoration-indigo-500">나의 판매/구매내역</h2>
    <MyTradeList/>
  </div>
</template>

<script setup lang="ts">
import MyTradeList from '@/components/profile/mytrade/MyTradeList.vue';
</script>

<style scoped>

</style>