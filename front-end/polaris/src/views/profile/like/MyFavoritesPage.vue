<template>
    <div>
      <h2 class="ml-4 my-2 text-xl underline underline-offset-8 decoration-indigo-500">
        찜한 거래 게시글
      </h2>
      <MyFavoritesList></MyFavoritesList>
    </div>
</template>

<script setup lang="ts">
  import MyFavoritesList from '@/components/profile/myfavorites/MyFavoritesList.vue';
</script>

<style scoped>
  h2 {
      @apply mt-6
  }
</style>