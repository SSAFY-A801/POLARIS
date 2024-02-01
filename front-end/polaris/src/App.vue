<template>
  <div>
    <Header></Header>
    <router-view></router-view>
    <Footer></Footer>

  </div>
</template>

<script setup lang="ts">
import Header from '@/components/common/Header.vue'
import Footer from '@/components/common/Footer.vue'

</script>

<style scoped>

</style>