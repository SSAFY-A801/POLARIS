<template>
  <div class="max-w-screen-lg mx-auto">
    <table class="table">
      <thead>
        <tr>
          <th><font-awesome-icon icon="fa-solid fa-book-open" class="mr-2" />받은도서 제목</th>
          <th><font-awesome-icon icon="fa-solid fa-user" class="mr-2" />거래 대상</th>
          <th>
            <font-awesome-icon icon="fa-solid fa-arrow-right-arrow-left" class="mr-2"/>
            내가 준 도서</th>
          <th><font-awesome-icon icon="fa-solid fa-calendar-days" class="mr-2"/>날짜</th>
        </tr>
      </thead>
      <tbody>
        <MyExchangeListItem
          v-for="(exchangeitem,index) in props.exchangehistory" 
          :key="index"
          :exchangeitem="exchangeitem"
        />
      </tbody>
    </table>
	</div>
</template>

<script setup lang="ts">
import MyExchangeListItem from './MyExchangeListItem.vue';
const props = defineProps(['exchangehistory'])


</script>

<style scoped>
  .table {
    width: 100%;
    border-collapse: collapse;
    margin: 25px 0px;
    font-size: 18px;
    text-align: center;
  }

  thead {

    border-bottom: 1px solid black;
  }
</style>