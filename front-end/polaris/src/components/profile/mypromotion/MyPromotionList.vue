<template>
  <div class="max-w-screen-lg mx-auto">
    <table class="table">
      <thead>
        <tr>
          <th>게시글 제목</th>
          <th>거래 종류</th>
          <th>조회수</th>
          <th><i class="fa-solid fa-calendar-days mr-2"></i>날짜</th>
        </tr>
      </thead>
      <tbody>
        <!-- divide-y 클래스를 사용하여 각 row 사이에 간격을 추가합니다. divide-gray-200는 간격의 색상을 지정합니다. -->
        <MyPromotionListItem
          v-for="index in items" :key="index"
        />
      </tbody>
    </table>

  </div>
</template>

<script setup lang="ts">
  import MyPromotionListItem from '../mypromotion/MyPromotionListItem.vue';
  const items:number = 8;

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