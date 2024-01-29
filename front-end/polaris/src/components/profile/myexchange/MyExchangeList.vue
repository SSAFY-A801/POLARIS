<template>
  <div class="max-w-screen-lg mx-auto">
    <table class="table">
      <thead>
        <tr>
          <th><i class="fa-solid fa-book-open mr-2"></i>도서 제목</th>
          <th><i class="fa-solid fa-person mr-2"></i>거래 대상</th>
          <th><i class="fa-solid fa-arrow-right-arrow-left mr-2"></i>교환받은 도서</th>
          <th><i class="fa-solid fa-calendar-days mr-2"></i>날짜</th>
        </tr>
      </thead>
      <tbody>
        <MyExchangeListItem
          v-for="index in items" :key="index"
        />
      </tbody>
    </table>
	</div>
</template>

<script setup lang="ts">
import MyExchangeListItem from './MyExchangeListItem.vue';
const items = 11;
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