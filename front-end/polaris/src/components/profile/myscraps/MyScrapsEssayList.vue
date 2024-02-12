<template>
  <div class="max-w-screen-lg mx-auto">
    <table class="table">
      <thead>
        <tr>
          <th><font-awesome-icon icon="fa-solid fa-note-sticky" class="mr-2"/>독후감 제목</th>
          <th><font-awesome-icon icon="fa-solid fa-book-open" class="mr-2" />도서 제목</th>
          <th><font-awesome-icon icon="fa-solid fa-user" class="mr-2" />작성자</th>
          <th><font-awesome-icon icon="fa-solid fa-calendar-days" class="mr-2"/>날짜</th>
        </tr>
      </thead>
      <tbody>
        <MyScrapsEssayListItem
        v-for="(myscrap,index) in props.myscraps"
          :key="index"
          :myscrap="myscrap"
        />
      </tbody>
    </table>
   </div> 
</template>

<script setup lang="ts">
  import MyScrapsEssayListItem from "../myscraps/MyScrapsEssayListItem.vue";

  const props = defineProps(['myscraps'])
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

th {
  margin: 10px;
}
</style>