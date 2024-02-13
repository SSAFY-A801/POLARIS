<template>
  <div class="max-w-screen-lg mx-auto">
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
          v-for="(tradeitem,index) in props.tradehistory"
           :key="index"
           :tradeitem="tradeitem"
        />
      </tbody>
    </table>
	</div>
</template>

<script setup lang="ts">
import MyTradeListItem from "../mytrade/MyTradeListItem.vue";
const props = defineProps(['tradehistory'])



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