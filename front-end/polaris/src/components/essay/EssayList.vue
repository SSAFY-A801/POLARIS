<template>
  <EssayListItem v-for="(essay, index) in props.essayList" :key="index" :essayInfo="essay" />
</template>

<script setup lang="ts">
import EssayListItem from './EssayListItem.vue'

const props = defineProps(['essayList'])
</script>

<style scoped>
</style>