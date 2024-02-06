<template>
  <div class="container mx-auto mt-28 max-w-6xl">
    <div>
      <h2 id="title" class="ml-4 my-2 text-xl underline underline-offset-8 decoration-maintheme1">나의 교환 내역
      </h2>
    </div>
    <MyExchangeList/>
  </div>
</template>


<script setup lang="ts">
import MyExchangeList from '@/components/profile/myexchange/MyExchangeList.vue';
</script>

<style scoped>

</style>