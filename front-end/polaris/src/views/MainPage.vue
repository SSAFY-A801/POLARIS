<template>
  <main>
    <p class="h-24">mainview</p>
    
    <div v-if="booklistItem" >
        <div v-for="bookItem in booklistItem.item" :key="bookItem.itemId">
          <img :src=  "bookItem.cover"/> 
          <div>{{  bookItem.title }}</div>
          <div>{{  bookItem.author }}</div>
        </div>
    </div>
  </main>


</template>

<script setup lang="ts" > 
import { ref , onMounted } from 'vue'
import axios from 'axios'
// import { BaseCarousel, BaseCarouselItem } from 'vue-base-carousel'
// import "vue-base-carousel/style.css"

type bookItemType = {
  adult: boolean , 
  author : string , 
  bestDuration : string , 
  bestRank :number ,
  categoryId : number , 
  categoryName : string ,
  cover:string , 
  customerReviewRank:number,
  description:string,
  fixedPrice: boolean,
  isbn : string,
  isbn13 : string,
  itemId: number,
  link: string, 
  mallType: string,
  mileage : number,
  priceSales : number,
  priceStandard : number,
  pubDate : string,
  publisher : string,
  salesPoint : number,
  stockStatus : string,
  subInfo : object,
  title : string
}

type bookListType = {item:bookItemType[] ,itemsPerPage:number , link:string,logo:string,pubDate:string,query:string,searchCategoryId:number,searchCategoryName:string,startIndex:number,title:string,totalResults:number,version:string}

const booklistItem = ref<bookListType | null>()


onMounted(async () => {
   await axios.get<bookListType,{data:bookListType}>('http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbkimsw28261657008&Query=aladdin&QueryType=Bestseller&MaxResults=20&start=1&SearchTarget=Book&output=js&Version=20131101',{
    headers: {
    "Content-Type": "applicatio/json",
    "Accept":"applicatio/json",
  }})
   .then((data) => 
    console.log(booklistItem.value = data.data)
    )
    .catch (function (error) {
    alert(error.message)
  }) 
})

</script>


