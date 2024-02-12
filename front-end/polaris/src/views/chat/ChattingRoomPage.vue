<template>
  <Navvar></Navvar>
  <div class="font-[gowun-dodum]">
    <div class="text-4xl my-4 mt-40">채팅방 내부</div>
    <div class="flex">
      <div>채팅을 시작하면 보여지는 페이지</div>
      <SellChattingBox/>
      <ChangeChattingBox/>
    </div>
  </div>
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import SellChattingBox from '@/components/chat/SellChattingBox.vue';
import ChangeChattingBox from '@/components/chat/ChangeChattingBox.vue';



</script>

<style scoped>
  @font-face {
  font-family: 'gowun-dodum';
  src: url('../../../../public/GowunDodum-Regular.ttf');
}

</style>