<template>
  <Header></Header>
  <div class="col-span-12 mt-16">
    <RouterView/>
  </div>
  <Footer></Footer>
</template>

<script setup lang="ts">
import Header from '@/components/common/Header.vue'
import Footer from '@/components/common/Footer.vue'

</script>

<style scoped>

</style>