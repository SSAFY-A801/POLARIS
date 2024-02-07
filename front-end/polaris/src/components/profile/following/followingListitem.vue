<template>
  <div 
  class="grid grid-cols-12 items-center"
  :class="{'bg-gray-200': !follow}">
    <div @click="gotoProfile" id="following-image"  class="col-span-2">
      <img v-if="following.profileUrl" :src="following.profileUrl" alt="NO IMAGE" class="profile-image">
      <img v-else src="@\assets\following-user.jpg" alt="NO IMAGE" class="profile-image">
    </div>
    <div id="following-nickname" class="col-span-3">
      <div>{{ following.nickname }}</div>
    </div>
    <div id="following-location" class="col-span-4">
      <div>{{ following.regcode.si }} {{ following.regcode.gungu }} {{ following.regcode.dong }}</div>
    </div>
    <div @click="followchange" id="follow-toggle" class="col-span-3">
      <button v-if="follow" id="follow-button">언팔로우</button>
      <button v-else id="follow-button">팔로우</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import type { Following } from '@/stores/profilecounter';

interface FollowingInfo {
  following: Following
}

const emit = defineEmits<{
  (e: 'followToggle', following: Following, follow: boolean): void
}>()

const follow = ref(true)
const router = useRouter();

const followchange = () => {
  follow.value = !follow.value
  emit('followToggle',following, follow.value)
}

const { following } = defineProps<FollowingInfo>();

const gotoProfile = () => {
  router.push({name: "ProfilePage", params: {id:following.followingId }})
}
</script>

<style scoped>
  #follow-toggle {
    @apply bg-[#323F59] text-center text-white m-[3px] px-3 py-[3px] rounded-[10px]  hover:bg-gray-500;
  }

  .profile-image {
    @apply w-[60px] h-[60px] object-cover justify-items-center rounded-[70%] border-[1px] border-solid border-[#121212];
}


</style>