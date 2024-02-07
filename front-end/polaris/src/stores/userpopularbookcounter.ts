import { defineStore } from 'pinia'
import axios from 'axios'


type userPopularBookType = {isbn: string,
    cover: string,
    title: string,
    author: string,
    publisher: string,
    pubDate: string,
    bookDescription: string,
    priceStandard: number}


export const useUserPopularBookStore = defineStore({
  id: 'userpopularbook',
  state: () => ({
    userPopularBookList: null as userPopularBookType[] | null
  }),
  actions: {
    async fetchPopularBooklistItem() {
      const response = await axios.get('https://i10a801.p.ssafy.io:8082/book/popular_books')
      this.userPopularBookList = response.data.data
    }
  }
})