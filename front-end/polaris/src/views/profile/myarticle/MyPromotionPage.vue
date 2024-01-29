<template>
    <h2 class="ml-4 my-2 text-xl underline underline-offset-8 decoration-indigo-500">
        나의 홍보 게시물
    </h2>
    <div>
        <MyPromotionList></MyPromotionList>
    </div>
</template>

<script setup lang="ts">
  import MyPromotionList from '@/components/profile/mypromotion/MyPromotionList.vue';
</script>

<style scoped>
    h2 {
        @apply mt-6
    }
</style>