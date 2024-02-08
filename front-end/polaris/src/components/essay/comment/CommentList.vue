<template>
  <EssayListItem
  v-for="(essay, index) in EssayList"
  :key = "index"
  :essayInfo = "essay"
  />
</template>

<script setup lang="ts">
  const EssayList = 4;
</script>

<style scoped>

</style>