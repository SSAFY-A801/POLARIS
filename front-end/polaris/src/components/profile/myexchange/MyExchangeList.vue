<template>
  <div class="max-w-screen-lg mx-auto">
    <table class="table">
      <thead>
        <tr>
          <th><font-awesome-icon icon="fa-solid fa-book-open" class="mr-2" />도서 제목</th>
          <th><font-awesome-icon icon="fa-solid fa-user" class="mr-2" />거래 대상</th>
          <th>
            <!-- <font-awesome-icon icon="fa-solid fa-arrow-right-arrow-left" class="mr-2"/> -->
            교환받은 도서</th>
          <th><font-awesome-icon icon="fa-solid fa-calendar-days" class="mr-2"/>날짜</th>
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