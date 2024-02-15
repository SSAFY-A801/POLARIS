<template>
  <tr>
    <td class="p-4 text-sm font-normal text-gray-900">{{ book.title }}</td>
    <td class="p-4 text-sm font-normal text-gray-900">{{ book.author }}</td>
    <td class="p-4 text-sm font-normal text-gray-900">{{ book.bookIsbn }}</td>
    <td class="p-4 text-sm font-normal text-gray-900">{{ book.userBookPrice }}원</td>
    <td class="p-4 text-sm font-normal text-gray-500">
      <div class="flex justify-center items-center h-full">
        <input type="checkbox" id="myCheckbox" value="" v-model="isTraded" @click="startEmit" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
      </div>
    </td>
  </tr>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import type { Book } from './SellChattingBox.vue';

interface TradeBookData {
  book: Book

}



const { book } = defineProps<TradeBookData>()
const isTraded = ref<boolean>(false)


const emit = defineEmits<{
  (e:'emit', id: number, isbn: string, isTraded: boolean): void
}>();

const startEmit = () => {
  emit('emit', book.id, book.bookIsbn, !isTraded.value)
}

onMounted(()=> {

})
</script>

<style scoped></style>