<template>
  <Header></Header>
  <div class="col-span-12 mt-20">
    <RouterView/>
  </div>
  <Footer></Footer>
</template>

<script setup lang="ts">
import Header from '@/components/common/Header.vue'

</script>

<style scoped>

</style>