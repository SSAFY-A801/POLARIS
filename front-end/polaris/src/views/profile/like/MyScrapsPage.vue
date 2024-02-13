<template>
    <div>
        <h2 class="ml-4 my-2 text-xl underline underline-offset-8 decoration-indigo-500">스크랩한 독후감
          <i class="fa-solid fa-book-bookmark ml-1"></i>
        </h2>
        <MyScrapsEssayList
        v-if="myscraps.length"
        :myscraps="myscraps"
        />
        <div v-else class="text-center mt-10 font-bold text-xl">
          현재 스크랩한 독후감이 존재하지 않습니다.
        </div>
    </div>
</template>

<script setup lang="ts">
  import MyScrapsEssayList from '@/components/profile/myscraps/MyScrapsEssayList.vue';
  import { onMounted, ref } from 'vue';
  import axiosInstance from '@/services/axios';
  import { profileCounterStore } from '@/stores/profilecounter';
  const loginUserId = JSON.parse(localStorage.getItem('user_info')||"").id
  const store = profileCounterStore();
  const myscraps = ref(store.myscraps)

  onMounted(()=> {
    store.getMyscraps(loginUserId);

  })

</script>

<style scoped>
  h2 {
      @apply mt-6
  }
</style>