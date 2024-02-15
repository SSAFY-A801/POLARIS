<template>
  <div 
  class="grid grid-cols-12 items-center "
  :class="{'bg-gray-200': !follow}">
    <div @click="gotoProfile"  id="following-image"  class="col-span-2">
      <img v-if="following.profileUrl" :src="following.profileUrl" alt="NO IMAGE" class="profile-image">
      <img v-else src="@\assets\following-user.jpg" alt="NO IMAGE" class="profile-image hover:bg-indigo-100">
    </div>
    <div id="following-nickname" class="col-span-3">
      <div>{{ following.nickname }}</div>
    </div>
    <div id="following-location" class="col-span-5">
      <div>{{ following.regcode.si }} {{ following.regcode.gungu }} {{ following.regcode.dong }}</div>
    </div>
    <div @click="followchange" id="follow-toggle" class="col-span-2">
      <button v-if="follow" id="follow-button">언팔로우</button>
      <button v-else id="follow-button">팔로우</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import type { Following } from '@/stores/profilecounter';
import { profileCounterStore } from '@/stores/profilecounter';


interface FollowingInfo {
  following: Following
}
const { following } = defineProps<FollowingInfo>();

const emit = defineEmits<{
  (e: 'followToggle', following: Following, follow: boolean): void
  (e: 'clickProfile',clickProfile: boolean): void
}>()

const follow = ref(true)
const router = useRouter();
const store = profileCounterStore();
const clickProfile = ref(false)

const followchange = () => {
  follow.value = !follow.value
  emit('followToggle',following, follow.value)
}


const gotoProfile = () => {
  router.push({ name: "ProfilePage", params: { id: following.followingId } });
  emit('clickProfile', clickProfile.value);
  clickProfile.value = true;
  // Assuming store.getProfile and store.getMybookList return Promises
  store.getProfile(following.followingId);
  store.mybookLists = []

}
</script>

<style scoped>
  #follow-toggle {
    @apply bg-[#323F59] text-center text-white m-[3px] px-3 py-[3px] rounded-[10px]  hover:bg-gray-500;
  }

  .profile-image {
    @apply w-[60px] h-[60px] object-cover justify-items-center rounded-[70%] border-[1px] hover:border-2 border-solid border-[#121212] hover:border-indigo-700 opacity-100 hover:opacity-75 ;
}


</style>