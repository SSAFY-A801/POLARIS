<template>
  <main>
    <p class="h-24">mainview</p>
    <div class="m-auto flex justify-center">
      <div class="mt-24 mb-10 ml-10 w-2/3">
          <h1 class=" text-3xl font-bold text-maintheme1 mb-10">Bestseller Top 20</h1>      
      </div>
    </div>
    <Bestseller></Bestseller>
    <div class="m-auto flex justify-center">
      <div class="mt-24 mb-10 ml-10 w-2/3">
          <h1 class=" text-3xl font-bold text-maintheme1 mb-10">사용자 인기도서 Top 20</h1>      
      </div>
    </div>
    <UserPopularBook></UserPopularBook>
    
    
  </main>


</template>


<script setup lang="ts">

import Bestseller from '@/components/main/Bestseller.vue'
import UserPopularBook from '@/components/main/UserPopularBook.vue'
</script>

<style scoped>

</style>