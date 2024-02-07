<template>
    <div class="wrapper">
      <button class="carousel-button" @click="handleOnClickPrevButton"><font-awesome-icon icon="fa-solid fa-chevron-left" size="xl" /></button>
      <div class="carousel-wrapper">
        <div class="carousel-item-wrapper" :style="{ left: `-${currentIndex * widthPx}px` }">
          <div v-for="(userPopularBook, index) in userPopularBookList"  class="book-list-item carousel-item">
            <router-link :to="{ name: 'userpopularbookdatail', params: { id: index+1 }}" class="no-underline">
                <div class="m-auto overflow-hidden rounded-lg shadow-lg cursor-pointer w-48 h-72">
                    <div class="block w-full h-full">
                        <div class="font-medium text-maintheme1 font-bold text-md mx-auto text-center mt-2 mb-2">
                            {{index+1}}위
                        </div>
                        <img :src="userPopularBook.cover" class="object-cover mx-auto" style="width: 85px;"/> 
                        <div class="w-full p-4 bg-white dark:bg-gray-800">
                            <p class="mb-2 text-xs font-medium text-gray-800 ">
                                {{truncateTitle(userPopularBook.title)}}
                            </p>
                            <p class="font-light text-gray-400  text-xs">
                                {{truncateTitle(userPopularBook.author)}}
                            </p>
                        </div>
                    </div>
                </div>
            </router-link>
          </div>
        </div>
      </div>
      <button class="carousel-button" @click="handleOnClickNextButton"><font-awesome-icon icon="fa-solid fa-chevron-right" size="xl" /></button>
    </div>
  </template>
  
<script setup lang="ts">

import { ref, onMounted } from 'vue'
import { useUserPopularBookStore } from '@/stores/userpopularbookcounter'

const store = useUserPopularBookStore()
const userPopularBookList = ref(store.userPopularBookList)


const currentIndex = ref(0); // 현재 페이지 index

const maxIndex = ref(3); // 최대 페이지 index

const widthPx = 1000; // 전체 너비 px


const truncateTitle = (title: string) => {
    if (!title) return "";
    return title.length > 25 ? title.substring(0, 25) + "..." : title;
  };
  
// onMounted(async () => {
//   await axios.get('https://i10a801.p.ssafy.io:8082/book/popular_books')
//   .then(function (response) {
//     userPopularBookList.value = response.data.data
//     console.log(userPopularBookList.value)
//   })
//   .catch(function (error) {
//     alert(error.message)
//   })

// })

onMounted(async () => {
  await store.fetchPopularBooklistItem()
  userPopularBookList.value = store.userPopularBookList
  console.log(userPopularBookList.value)

})

const handleOnClickNextButton = () => {
  if (currentIndex.value < maxIndex.value) {
    currentIndex.value++;
  }
};

const handleOnClickPrevButton = () => {
  if (currentIndex.value > 0) {
    currentIndex.value--;
  }
};


</script>

<style scoped>
.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 32px; 
}

.carousel-wrapper {
  width: 1000px; 
  overflow: hidden;
  position: relative;
  height: 300px;
}
.carousel-item-wrapper {
  width: fit-content;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  position: absolute;
  top: 0;
  transition: all 0.8s ease-in-out;
}
.carousel-item {
  width: 200px; 
}
</style>