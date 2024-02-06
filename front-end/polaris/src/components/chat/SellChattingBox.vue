<template>
  <div class="first-container">
    <div class="content">
<div class="text-2xl m-3 font-semibold">도서 거래를 위한 채팅공간</div>
<div class="flex">
<div class="flex">
  <div class="bg-white shadow rounded-lg p-4 ml-4" style="height: 700px;">
  <div>
    <h3 class="text-xl font-bold text-gray-900 mb-2">Chat Room</h3>
    <span class="text-base font-normal text-gray-500">상대방과 이야기하여 거래도서를 선택해보세요</span>
  </div>
  <div>
    <div class="bg-gray-50 shadow rounded-lg mb-8 mr-4 ml-4 mt-4" style="width: 400px; height: 500px;">
      <div>대화를 나눠보자</div>
      <ConnectSocket/>
    </div>
  </div>
  <div class="flex justify-center">
      <button class="inline-block rounded bg-maintheme1 h-8 px-2 py-1 text-sm font-medium text-white mr-3">img</button>
      <textarea class="border" style="width: 300px;"></textarea>
      <button  class="inline-block rounded bg-maintheme1 h-8 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2 ml-3">전송</button>
    </div>
  </div>
</div>
<div class="ml-10">
  <MySellingBook/>
</div>
</div>  
</div></div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import MySellingBook from './MySellingBook.vue';
import ConnectSocket from './ConnectSocket.vue'


</script>

<style scoped>
  .first-container {
    padding-top: 50px;
    display: grid;
    grid-template-columns: 1fr auto 1fr;
    height: 100vh;
    align-items: center;
  }

  .content {
    grid-column: 2;
  }
</style>
