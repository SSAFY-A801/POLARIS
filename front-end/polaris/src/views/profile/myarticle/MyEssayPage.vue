<template>
    <h2 class="ml-4 my-2 text-xl underline underline-offset-8 decoration-indigo-500">
      나의 독후감
    </h2>
    <div>
      <MyEssayList></MyEssayList>
    </div>
</template>

<script setup lang="ts">
  import MyEssayList from '@/components/profile/myessay/MyEssayList.vue';
</script>

<style scoped>
    h2 {
        @apply mt-6
    }
</style>