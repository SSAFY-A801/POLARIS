<template>
  <div class="max-w-screen-lg mx-auto">
    <table class="table">
      <thead>
        <tr>
          <th>게시글 제목 <i class="fa-brands fa-get-pocket ml-2 fa-lg"></i></th>
          <th><i class="fa-solid fa-person mr-2"></i>거래 대상</th>
          <th>거래 종류</th>
          <th><i class="fa-solid fa-calendar-days mr-2"></i>날짜</th>
        </tr>
      </thead>
      <tbody>
        <MyFavoritesListItem
          v-for="index in items" :key="index"
        />
      </tbody>
    </table>

	</div>
</template>

<script setup lang="ts">
 import MyFavoritesListItem from '../myfavorites/MyFavoritesListItem.vue';
 const items:number = 5;
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