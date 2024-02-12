<template>
  <div>
    <div class="container">
    <div class="bg-white shadow rounded-lg p-4 sm:p-6 xl:p-8">
                     <div class="mb-4 flex items-center justify-between">
                        <div>
                           <h3 class="text-xl font-bold text-gray-900 mb-2">판매도서 목록</h3>
                           <span class="text-base font-normal text-gray-500">판매할 도서를 선택하여 추가해주세요</span>
                        </div>
                        <div class="flex-shrink-0">
                           <!-- <button v-if="selectedBooks.length > 0" class="text-sm font-medium text-cyan-600 hover:bg-gray-100 rounded-lg p-2" @click="toggleModal">제거</button>
                            <button v-else class="text-sm font-medium text-cyan-600 hover:bg-gray-100 rounded-lg p-2" @click="toggleModal">추가</button> -->
                          </div>
                     </div>
                     <div class="flex flex-col mt-8">
                        <div class="overflow-x-auto rounded-lg">
                           <div class="align-middle inline-block min-w-full">
                              <div class="shadow overflow-y-auto h-96 sm:rounded-lg">
                                 <table class="min-w-full divide-y divide-gray-200">
                                    <thead class="bg-gray-50">
                                       <tr>
                                          <th scope="col" class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                             도서 이름
                                          </th>
                                          <th scope="col" class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                             저자
                                          </th>
                                          <th scope="col" class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                             ISBN
                                          </th>
                                          <th scope="col" class="p-4 whitespace-nowrap text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                                             판매가격
                                          </th>
                                       </tr>
                                    </thead>
                                    <!-- <tbody class="bg-white">
                                       <tr v-for="(book, index) in selectedBooks" :key="book.id" :class="index % 2 === 0 ? 'bg-white' : 'bg-gray-50'">
                                          <td class="p-4 w-auto text-sm font-normal text-gray-900">
                                            {{ book.title }}
                                          </td>
                                          <td class="p-4 w-auto text-sm font-normal text-gray-500">
                                            {{ book.author }}
                                          </td>
                                          <td class="p-4 whitespace-nowrap text-sm font-noraml text-gray-900">
                                            {{ book.bookIsbn }}
                                          </td>
                                          <td class="p-4 whitespace-nowrap text-sm font-normal text-gray-500">
                                            {{ book.userBookPrice }}원
                                          </td>
                                       </tr>
                                    </tbody> -->
                                 </table>
                              </div>
                           </div>
                        </div>
                     </div>
                     <div class="flex justify-between">
                      <!-- <div class="text-lg font-semibold mt-6">총 판매금액 : {{ totalAmount }}원</div> -->
                      <!-- <button @click="completeSell(chatroomId)" class="w-2/5 mt-6 bg-maintheme1 rounded-lg px-4 py-2 text-lg text-white tracking-wide font-semibold font-sans">판매완료</button> -->
                    </div>
                  </div>
                  <div>
        </div>                 
</div>
  </div>
</template>

<script setup lang="ts">

</script>

<style scoped>

</style>