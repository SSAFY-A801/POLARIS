<template>
  <tr>
    <td class=" text-indigo-600"><button @click="gotoMyEssay">{{ essay.essayTitle }}</button></td>
    <td v-if="essay.bookTitle && essay.bookTitle.length > 20">{{ essay.bookTitle.slice(0,20) }}...</td>
    <td v-else >{{ essay.bookTitle }}</td>
    <td >{{ essay.scrapCount }}</td>
    <td >{{ essay.createdAt.toString().split('T')[0] }}</td>
  </tr>
</template>

<script setup lang="ts">
 import { useRouter } from 'vue-router';

 const router = useRouter();
 const props = defineProps(['essay'])

 const gotoMyEssay = () => {
  router.push({name: 'essaydetail', params:{essayId: props.essay.essayId}})
 }
</script>

<style scoped>
td {
  font-size: medium ;
  padding: 3px;
}
</style>