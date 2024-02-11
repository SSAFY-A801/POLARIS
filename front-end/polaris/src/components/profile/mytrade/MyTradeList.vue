<template>
  <Navvar></Navvar>
  <div class="max-w-screen-lg mx-auto mt-40">
    <table class="table">
      <thead>
        <tr>
          <th><font-awesome-icon icon="fa-solid fa-book-open" class="mr-2" />도서 제목</th>
          <th><font-awesome-icon icon="fa-solid fa-user" class="mr-2" />거래 대상</th>
          <th>거래 종류</th>
          <th><font-awesome-icon icon="fa-solid fa-calendar-days" class="mr-2"/>날짜</th>
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
import Navvar from '@/components/common/Navvar.vue'
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