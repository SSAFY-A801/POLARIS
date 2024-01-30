<template>
  <button v-if="!deleteState"  @click="registerBook" id="Book-register" class="shadow hover:bg-gray-100 border border-dashed rounded-md border-deepgray p-2">
    <font-awesome-icon icon="fa-solid fa-plus" size="8x" />  </button>
  <MyLibraryListItem
  v-for="(mybook, index) in mybookList"
  :key = "index"
  :bookinfo = "mybook" 
  />
</template>

<script setup lang="ts">
import MyLibraryListItem from './MyLibraryListItem.vue';
import { profileCounterStore } from '@/stores/profilecounter';
import { ref, computed, onMounted } from 'vue'; 
import { useRouter } from 'vue-router'
const { mybookList } = defineProps(['mybookList']);

const router = useRouter();
const store = profileCounterStore()
const deleteState = computed(() => {
  return store.deletebuttonState
})

function registerBook() {
  router.push({name: "BookRegisterPage"});
}

onMounted(()=> {
})





</script>

<style scoped>
button {
  height: 380px;
}

#Book-register {
    @apply border-2 border-[dashed];
}
</style>