<template>
  <div class="container mx-auto mt-8 max-w-6xl">
    <div id="following-list" class="max-w-2xl col-span-7 border border-maintheme1 rounded-lg">
  <!-- 팔로잉 목록 header -->
      <div class="flex justify-between rounded-t-lg bg-maintheme1 text-white p-2">
        <div>
          팔로잉
        </div>
      </div>
      <div id="following-list" class="overflow-auto">
        <!-- 팔로잉 목록 -->
        <followingListitem 
          v-for="index in followings" :key="index"  class="border p-3 min-w-[400] "
        />
      </div>
      <div class="flex justify-around">
        <button id="follow-toggle">취소</button>
        <button id="follow-toggle">확인</button>
      </div>
  </div>
</div>
</template>

<script setup lang="ts">
  import followingListitem from '@/components/profile/following/followingListitem.vue';
  const followings = 15;

</script>


<style scoped>


  #follow-toggle {
    @apply  w-full m-4 bg-[#323F59] text-center text-white m-[3px] px-3 py-[3px] rounded-[10px]  hover:bg-gray-500;
  }
</style>