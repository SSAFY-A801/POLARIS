<template>
  <CommentListItem
  v-for="(comment, index) in CommentList"
  :key = "index"
  :commentInfo = "comment"
  />
</template>

<script setup lang="ts">
import CommentListItem from './CommentListItem.vue';
  const CommentList = 4;
</script>

<style scoped>

</style>