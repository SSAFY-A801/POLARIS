<template>
    <div class="wrapper">
        <div class="book-info">
            <div class="m-auto flex justify-center">
            <div class="mt-10 mb-2 ml-10 icon-title">
                <font-awesome-icon icon="fa-solid fa-crown" size="2xl" style="color: #FFD43B;" />
                <h1 class=" text-3xl font-bold text-maintheme1 mb-2">이 주의 독후감</h1>
                      
            </div>
            </div>
            
            <div class="book-detail mt-4 mb-4">
                <h1 class=" text-center text-2xl font-bold text-gray-800 mb-2"> {{ WeeklyBook.bookTitle }}</h1>
                <p class="text-center text-xl font-bold text-gray-600 mb-2"> {{ WeeklyBook.author }}</p>
                <p class="text-center text-lg"> {{ WeeklyBook.bookDescription }}</p>  
            </div>
            <div class="w-full text-center" style="display: flex; justify-content: center;">
                <p class="text-lg font-bold mr-6">
                    <font-awesome-icon icon="fa-solid fa-pen-to-square" size="lg"/> {{ WeeklyBook.nickname }}
                </p>
                <p class="text-lg font-bold ml-6">
                    <font-awesome-icon icon="fa-solid fa-heart" size="lg"/> {{ WeeklyBook.scrapCount }}
                </p>
            </div>

            <div class="author-info w-full ">
                <router-link :to="{ name: 'essaydetail', params: { essayId: WeeklyBook.essayId }}" class="h-14 py-3 px-4 mt-4  bg-maintheme1  w-60 text-maintheme3 transition ease-in duration-200 text-center text-lg font-semibold shadow-md  rounded-lg ">독후감 보러가기</router-link>
               
                
            </div>
        </div>
        <div class="book-image">
            <img :src="WeeklyBook.cover" alt="Book Cover" style="height: 350px;" />
        </div>
    </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import axios from 'axios'

type WeeklyBookType = {cover:string , author:string , bookTitle:string, scrapCount: number, bookDescription: string, userId: number, nickname: string, essayId: number}
const WeeklyBook = ref<WeeklyBookType>({
  cover: '',
  author: '',
  bookTitle: '',
  scrapCount: 0,
  bookDescription: '',
  userId: 0,
  nickname: '',
  essayId: 0
})

onMounted(async () => {
  await axios.get(`${import.meta.env.VITE_API_KEY}/essay/most_scrapped`)
  .then(function (response) {
  WeeklyBook.value = response.data.data
})
.catch(function (error) {
	alert(error.message)
})
})

</script>

<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../public/GowunDodum-Regular.ttf');
}
.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 32px; 
  height: 600px;
  background: linear-gradient(150deg, white 65%, #eccf52 65%);
}


.book-info {
    padding-left: 10%;
    padding-right: 5%;
    /* margin-left: 150px; */
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    /* align-items: start; */
    gap: 20px;
    width: 60%;
}


.author-info{
    display: flex;
    justify-content: center;

}
.author-info p {
    margin-bottom: 5px;
}

.author-info button {
    padding: 10px;
    margin-top: 10px;
}

.book-image {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 80%;
    width: 40%;
    position: relative;
}

.book-image img {
    position: relative;
    z-index: 1;
    transform: translateY(-40px);
}

.book-image::after {
    content: "";
    position: absolute;
    z-index: 0;
    bottom: 70px;
    left: 50%;
    width: 400px;
    height: 60px;
    background: rgb(163, 155, 86);
    border-radius: 50%;
    transform: translateX(-50%);
    box-shadow: 0 20px 30px 5px hsla(54, 92%, 26%, 0.15);
}

</style>