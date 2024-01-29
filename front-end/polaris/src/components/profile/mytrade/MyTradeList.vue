<template>
  <div class="max-w-screen-lg mx-auto">
    <table class="table">
      <thead>
        <tr>
          <th><i class="fa-solid fa-book-open mr-2"></i>도서 제목</th>
          <th><i class="fa-solid fa-person mr-2"></i>거래 대상</th>
          <th>거래 종류</th>
          <th><i class="fa-solid fa-calendar-days mr-2"></i>날짜</th>
        </tr>
      </thead>
      <tbody>
        <MyTradeListItem
          v-for="index in items" :key="index"
        />
      </tbody>
    </table>
	</div>
</template>

<script setup lang="ts">
import MyTradeListItem from "../mytrade/MyTradeListItem.vue";
const items = 4;
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