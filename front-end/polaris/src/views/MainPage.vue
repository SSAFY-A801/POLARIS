<template>
  <main>
    <Navvar></Navvar>

    <div class="mt-40"><WeeklyBook></WeeklyBook></div>
    
    <div class=" mt-14 m-auto flex justify-center">
      <div class="mt-24 mb-2 ml-10">
          <h1 class=" text-4xl font-bold text-maintheme1 mb-2">서점 베스트셀러 Top 20</h1>      
      </div>
    </div>
    <Bestseller></Bestseller>
    <div class=" custom-background m-auto flex justify-center">
      <div class="mt-24 mb-2 ml-10">
          <h1 class=" text-4xl font-bold text-gray-200 mb-2">북극성 인기도서 Top 20</h1>      
      </div>
    </div>
    <UserPopularBook></UserPopularBook>
    
    
  </main>


</template>


<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import Bestseller from '@/components/main/Bestseller.vue'
import UserPopularBook from '@/components/main/UserPopularBook.vue'
import WeeklyBook from '@/components/main/WeeklyBook.vue'
</script>

<style scoped>
.custom-background{
  background-color: #323F59
}
</style>