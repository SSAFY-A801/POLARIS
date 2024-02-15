import { defineStore } from 'pinia'
import axios from 'axios'


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
  
export const useBestsellerStore = defineStore({
  id: 'bestseller',
  state: () => ({
    booklistItem: null as bookListType | null
  }),
  actions: {
    async fetchBooklistItem() {
      const response = await axios.get(`${import.meta.env.VITE_API_KEY}/api/bestseller`)
      this.booklistItem = response.data
    }
  }
})