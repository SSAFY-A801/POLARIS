<template>
  <tr>
    <td v-if="Books.length > 1" class=" text-indigo-600">
      <a href="">
        <button @click.prevent="showTradeList()">
          <div v-if="Books[0].bookTitle.length > 30">
            {{ Books[0].bookTitle.slice(0,30) }}... 외 {{ Books.length - 1 }} 권
          </div>
          <div v-else>
            {{ Books[0].bookTitle }} {{ Books.length - 1 }} 권
          </div>
        </button>
      </a>
    </td>
    <td v-else-if="Books.length == 1" class=" text-indigo-600">
      <a href="">
        <button @click.prevent="showTradeList()">
          <div v-if="Books[0].bookTitle.length > 30">
            {{ Books[0].bookTitle.slice(0,30) }}...
          </div>
          <div v-else>
            {{ Books[0].bookTitle }}
          </div>
        </button>
      </a>
    </td>
    <td v-else  class=" text-indigo-600">
          없음
    </td>
    <td >{{ oppositeNickname }}</td>
    <td v-if="price > 0" >{{  tradeType }}</td>
    <td v-else >나눔</td>
    <td >{{  date?.toString().split('T')[0] }}</td>
  </tr>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import type { TradeInfo } from '@/stores/profilecounter';
  import Swal from 'sweetalert2';
  const props = defineProps(['tradeitem'])
  const loginUser = Number(JSON.parse(localStorage.getItem('user_info')||"").id)
  const Books = ref<TradeInfo[]>([])
  const tradeType = ref("")
  const oppositeNickname = ref("")
  const date = ref<Date|null>(null)
  const price = ref(0)

  const showTradeList = () => {
    const bookTitles = Object.values(Books.value).map(book => book.bookTitle);
    const listItems = bookTitles.map((title,index) => `<li class='mb-1'>${index+1}. ${title}</li>`);

  Swal.fire({
    icon: "info",
    title: '거래 도서',
    html: `<ul>${listItems.join('')}</ul>`
  });
};

props.tradeitem.forEach((tradeInfo: TradeInfo) => {
  // 구매인 경우
  if(tradeInfo.receiverId != loginUser){
    tradeType.value ="구매"
  } else {
    tradeType.value ="판매"
  }
  oppositeNickname.value = tradeInfo.oppositeNickname
  date.value = tradeInfo.tradeDate
  price.value += tradeInfo.price
  Books.value.push(tradeInfo)
});

</script>

<style scoped>
td {
  font-size: medium ;
  padding: 3px;
}
</style>