<template>
  <div class="max-w-screen-lg mx-auto">
    <table class="table">
      <thead>
        <tr>
          <th><font-awesome-icon icon="fa-solid fa-note-sticky" class="mr-2"/>독후감 제목</th>
          <th><font-awesome-icon icon="fa-solid fa-book-open" class="mr-2" />도서명</th>
          <th><font-awesome-icon icon="fa-solid fa-bookmark" class="mr-2"/>스크랩 수</th>
          <th><font-awesome-icon icon="fa-solid fa-calendar-days" class="mr-2"/>날짜</th>
        </tr>
      </thead>
      <tbody>
        <MyEssayListItem
        v-for="(essay,index) in props.myessays" 
        :key="index"
        :essay="essay"
        class="mb-4"
        />
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
  import MyEssayListItem from '../myessay/MyEssayListItem.vue';
  const props = defineProps(['myessays'])
</script>

<style scoped>
.table {
  width: 100%;
  border-collapse: collapse;
  margin: 25px 0;
  font-size: 18px;
  text-align: center;
}

thead {
    border-bottom: 1px solid black;
  }
</style>