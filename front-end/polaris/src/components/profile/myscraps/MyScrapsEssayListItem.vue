<template>
  <tr>
    <td class="table-cell text-indigo-600"><button @click="gotoEssay">{{ myscrap.essayTitle }}</button></td>
    <td v-if="myscrap.bookTitle.length > 30" class="table-cell">{{ myscrap.bookTitle.slice(0,30) }}...</td>
    <td v-else class="table-cell">{{ myscrap.bookTitle }}...</td>
    <td class="table-cell">{{ myscrap.writerNickname }}</td>
    <td class="table-cell">{{ myscrap.createdAt.toString().split('T')[0] }}</td>
  </tr>
</template>

<script setup lang="ts">
  import { useRouter } from 'vue-router';

  const router = useRouter();
  const props = defineProps(['myscrap'])

  const gotoEssay = () => {
    router.push({name: 'essaydetail', params:{essayId: props.myscrap.essayId} })
  }
</script>

<style scoped>
td {
  font-size: medium ;
  padding: 3px;
}
</style>