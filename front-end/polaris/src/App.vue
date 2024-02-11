<template>
  <Header></Header>
  <div class="col-span-12 mt-20">
    <RouterView/>
  </div>
  <Footer></Footer>
</template>

<script setup lang="ts">
import Header from '@/components/common/Header.vue'
import Footer from '@/components/common/Footer.vue' 
import { onMounted } from 'vue'
import { profileCounterStore } from './stores/profilecounter';

const profileStore = profileCounterStore();

onMounted(()=> {
  if(localStorage.getItem('user_info')){
    const userInfoString = localStorage.getItem('user_info') || ""
    const userInfo = JSON.parse(userInfoString)
    profileStore.loginUserId = userInfo.id
  }
})
</script>

<style scoped>

</style>