<template>
  <div class="flex grid grid-cols-12 items-center">
    <div id="following-image"  class="col-span-2">
      <img src="@\assets\following-user.jpg" alt="NO IMAGE" class="profile-image">
    </div>
    <div id="following-nickname" class="col-span-3">
      <div>닉네임</div>
    </div>
    <div id="following-location" class="col-span-4">
      <div>팔로잉 지역</div>
    </div>
    <div id="follow-toggle" class="col-span-3">
      <button id="follow-toggle">언팔로우</button>
    </div>
  </div>
</template>

<script setup lang="ts">

</script>

<style scoped>
  #follow-toggle {
    @apply bg-[#323F59] text-center text-white m-[3px] px-3 py-[3px] rounded-[10px]  hover:bg-gray-500;
  }

  .profile-image {
    @apply w-[60px] h-[60px] object-cover justify-items-center rounded-[70%] border-[1px] border-solid border-[#121212];
}


</style>