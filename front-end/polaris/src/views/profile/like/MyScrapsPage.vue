<template>
    <div>
        <h2 class="ml-4 my-2 text-xl underline underline-offset-8 decoration-indigo-500">스크랩한 독후감
          <i class="fa-solid fa-book-bookmark ml-1"></i>
        </h2>
        <MyScrapsEssayList></MyScrapsEssayList>
    </div>
</template>

<script setup lang="ts">
  import MyScrapsEssayList from '@/components/profile/myscraps/MyScrapsEssayList.vue';
</script>

<style scoped>
  h2 {
      @apply mt-6
  }
</style>