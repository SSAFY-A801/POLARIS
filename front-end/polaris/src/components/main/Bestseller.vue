<template>
    <div class="wrapper">
      <button class="carousel-button" @click="handleOnClickPrevButton"><font-awesome-icon icon="fa-solid fa-chevron-left" style="color: #ffffff;" size="2xl" /></button>
      <div class="carousel-wrapper">
        <div class="carousel-item-wrapper" :style="{ left: `-${currentIndex * widthPx}px` }">
          <div v-for="bookItem in booklistItem?.item" :key="bookItem.title" class="book-list-item carousel-item">
            <router-link :to="{ name: 'bestsellerdatail', params: { id: bookItem.bestRank }}" class="no-underline">
                <div class="m-auto overflow-hidden  cursor-pointer w-60 h-100">
                    <div class="block w-full h-full">
                        
                        <img :src="bookItem.cover" class="object-cover mx-auto" style="height: 220px;"/> 
                        <div class="font-xl text-white font-bold text-lg mx-auto text-center mt-4">
                            {{bookItem.bestRank}}위
                        </div>
                        <div class="w-full px-10 py-6 bg-maintheme1 dark:bg-gray-800" style="height: 200px; text-align: center;">
                            <p class="mb-2 text-md font-xl text-white ">
                                {{truncateTitle(bookItem.title)}}
                            </p>
                            <p class="font-light text-gray-300  text-xs">
                                {{truncateTitle(bookItem.author)}}
                            </p>
                        </div>
                    </div>
                </div>
            </router-link>
          </div>
        </div>
      </div>
      <button class="carousel-button" @click="handleOnClickNextButton"><font-awesome-icon icon="fa-solid fa-chevron-right" style="color: #ffffff;"  size="2xl"/></button>
    </div>


</template>

  
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useBestsellerStore } from '@/stores/bestsellercounter'


const store = useBestsellerStore()
const booklistItem = ref(store.booklistItem)

const currentIndex = ref(0); // 현재 페이지 index

const maxIndex = ref(3); // 최대 페이지 index

const widthPx = 1000; // 전체 너비 px


const truncateTitle = (title: string) => {
    return title.length > 30 ? title.substring(0, 30) + "..." : title;
  };
  
onMounted(async () => {
  await store.fetchBooklistItem()
  booklistItem.value = store.booklistItem
  // console.log("onMounted",userToken.value)

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
  height: 600px;
  background: linear-gradient(180deg, white 35%, #323F59 35%);
}

.carousel-wrapper {
  width: 1000px; 
  overflow: hidden;
  position: relative;
  height: 400px;
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
  height: 450px;
}
</style>