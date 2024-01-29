<template>
  <div class="max-w-screen-lg mx-auto">
    <table class="table">
      <thead>
        <tr>
          <th>독후감 제목</th>
          <th><i class="fa-solid fa-book-open mr-2"></i>도서명</th>
          <th>스크랩 수</th>
          <th><i class="fa-solid fa-calendar-days mr-2"></i>날짜</th>
        </tr>
      </thead>
      <tbody>
        <MyEssayListItem
        v-for="index in items" :key="index"
        class="mb-4"
        />
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
  import MyEssayListItem from '../myessay/MyEssayListItem.vue';
  const items:number = 7;
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