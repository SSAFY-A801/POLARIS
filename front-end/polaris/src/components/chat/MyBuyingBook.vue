<template>
   <div class="container">
      <div class="bg-white shadow rounded-lg p-4 sm:p-6 xl:p-8">
         <div class="mb-4 flex items-center justify-between">
            <div>
               <h3 class="text-xl font-bold text-gray-900 mb-2">구매도서 목록</h3>
               <span class="text-base font-normal text-gray-500">구매할 도서를 확인해보세요</span>
            </div>
         </div>
         <div class="flex flex-col mt-8">
            <div class="overflow-x-auto rounded-lg">
               <div class="align-middle inline-block min-w-full">
                  <div class="shadow overflow-y-auto h-96 sm:rounded-lg">
                     <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                           <tr>
                              <th scope="col"
                                 class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                 도서 이름
                              </th>
                              <th scope="col"
                                 class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                 저자
                              </th>
                              <!-- <th scope="col" class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                            ISBN
                                         </th> -->
                              <th scope="col"
                                 class="p-4 whitespace-nowrap text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                 판매가격
                              </th>
                           </tr>
                        </thead>
                        <tbody class="bg-white">{{ props }}
                           <tr v-for="(book, index) in chatRoomTradeBooks" :key="index"
                              class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
                              <td class="p-4 w-auto text-sm font-normal text-gray-900">
                                 {{ book.title }}
                              </td>
                              <td class="p-4 w-auto text-sm font-normal text-gray-500">
                                 {{ book.author }}</td>
                              <!-- <td class="p-4 whitespace-nowrap text-sm font-noraml text-gray-900">
                                         {{ }}</td> -->
                              <td class="p-4 whitespace-nowrap text-sm font-normal text-gray-500">
                                 {{ book.price }}</td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </div>
            </div>
         </div>
         <div class="flex justify-between">
            <div class="text-lg font-semibold mt-6">총 구매금액 : {{ }} 원</div>
         </div>
      </div>
      <div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { defineProps, computed, toRefs, watchEffect, ref } from 'vue';


interface ChatRoomTradeBooks {
   userId: number;
   id: number;
   title: string;
   author: string;
   status: string;
   price: number | null;
   seriesId: number | null;
}

// const props = defineProps< { chatRoomTradeBooks: ChatRoomTradeBooks[] } >(['chatRoomTradeBooks']);
const props = defineProps<{ chatRoomTradeBooks: ChatRoomTradeBooks[] }>();
const chatRoomTradeBooks = ref(props.chatRoomTradeBooks)
// const { chatRoomTradeBooks } = defineProps(['chatRoomTradeBooks']);

// const totalPrice = computed(() => {
//   return props.books?.reduce((sum, book) => sum + (book.price || 0), 0);
// });


// interface Props {
//   chatRoomTradeBooks: ChatRoomTradeBooks[];
// }

// const props = defineProps<Props>();
// console.log('프롭스',props.chatRoomTradeBooks)

</script>

<style scoped></style>