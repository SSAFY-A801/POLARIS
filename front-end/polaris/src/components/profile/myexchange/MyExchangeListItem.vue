<template>
  <tr class="border-b">
    <td v-if="receiveBooks.length > 1"  class=" text-indigo-600">
      <a href="">
      <button @click.prevent="showTradeList">
        <div v-if="receiveBooks[0].bookTitle.length > 20">
          {{ receiveBooks[0].bookTitle.slice(0,20) }}... 외 {{ receiveBooks.length - 1 }} 권
        </div>
        <div v-else>
          {{ receiveBooks[0].bookTitle}} 외 {{ receiveBooks.length - 1 }} 권
        </div>
      </button>
      </a>
    </td>
    <td v-else-if="receiveBooks.length == 1"  class=" text-indigo-600">
      <a href="">
      <button @click.prevent="showTradeList">
        <div v-if="receiveBooks[0].bookTitle.length > 20">
          {{ receiveBooks[0].bookTitle.slice(0,20) }} ...
        </div>
        <div v-else>
          {{ receiveBooks[0].bookTitle}}
        </div>
      </button>
      </a>
    </td>
    <td v-else>
      없음
    </td>
    <td >{{ counter }}</td>
    <td v-if="sendBooks.length > 1">
      <div v-if="sendBooks[0].bookTitle.length > 20">
        {{ sendBooks[0].bookTitle.slice(0,20) }}... 외 {{ sendBooks.length - 1 }} 권
      </div>
      <div v-else>
        {{ sendBooks[0].bookTitle }}... 외 {{ sendBooks.length - 1 }} 권
      </div>
    </td>
    <td v-else-if="sendBooks.length == 1">
      <div v-if="sendBooks[0].bookTitle.length > 20">
        {{ sendBooks[0].bookTitle.slice(0,20) }} ...
      </div>
      <div v-else>
        {{ sendBooks[0].bookTitle }}
      </div>
    </td>
    <td v-else> 없음 </td>
    <td >{{ date?.toString().split('T')[0] }}</td>
  </tr>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import type { ExchangeInfo } from '@/stores/profilecounter';
  import Swal from 'sweetalert2';
  const props = defineProps(['exchangeitem'])
  const loginUser = Number(JSON.parse(localStorage.getItem('user_info')||"").id)
  const sendBooks = ref<ExchangeInfo[]>([])
  const receiveBooks = ref<ExchangeInfo[]>([])
  const counter = ref("")
  const date = ref<Date|null>(null)

  const showTradeList = () => {
  Swal.fire({
    icon: "info",
    html: `
    <div class="mb-2">내가 받은 도서</div>
      <ul class='text-sm mb-4'>
        ${receiveBooks.value.map((book,index) => `<li>${index+1}. ${book.bookTitle}</li>`).join('')}
      </ul>
    <div class="mb-2">내가 준 도서</div>
      <ul class='text-sm class="mb-2"'>
        ${sendBooks.value.map((book,index) => `<li>${index+1}. ${book.bookTitle}</li>`).join('')}
      </ul>
    `,
  });
};

props.exchangeitem.forEach((tradeInfo: ExchangeInfo) => {
  if(tradeInfo.userId == loginUser){
    sendBooks.value.push(tradeInfo)
  } else {
    receiveBooks.value.push(tradeInfo)
    counter.value = tradeInfo.nickname
    date.value = tradeInfo.tradeDate
  }
});

</script>

<style scoped>
td {
  font-size: medium ;
  padding: 3px;
}
</style>