import { ref, computed, watch } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';

export type Book =  {
  id: number,
  cover: string,
  title: string,
  author: string,
  isbn: string,
  publisher : string,
  pubDate : string,
  bookDescription: string,
  userBookDescription : string,
  price_standard: number,
  userBookPrice: number,
  isOpened: string,
  isOwned: string,
  tradeType: string,
  seriesId: number,
  seriesName: string,
}

export const profileCounterStore = defineStore('counter', () => {
  // ProfilePage

  // MyEssayPage

  // MyTradeListPage

  // MyExchangeListPage

  // MyScrapsPage

  // MyFavoritePage

  // MyPromotionPage

  // BookRegisterPage

  // FollowingListPage


  // MyLibraryPage
  // const getMybookList = () => {
  //   axios({
  //     method: 'get',
  //     url: 'https://d05c09c5-63ed-4958-8c77-57a581384189.mock.pstmn.io/profile/library',
  //   })
  //   .then((response) => {
  //     console.log(response.data)
  //   })
  //   .catch((error) => {
  //     console.log(error)
  //   })
  // }
  const bookCartList = ref<Book[]>([])
  const bookSearchResultList = ref([])
  const mybookLists = ref([
    {
      id: 1,
      cover : "image_url",
      title: "쇼펜하우어 아포리즘",
      author: "쇼펜하우어",
      isbn: "9791192625553",
      publisher : "포레스트 북스",
      pubDate : "2023-06-01",
      bookDescription: "blah blah",
      userBookDescription : "좋은 책!",
      price_standard: 10000,
      userBookPrice: 5000,
      isOpened: "공개",
      isOwned: "보유",
      tradeType: "TRADE",
      seriesId: 1,
      seriesName: "seriesName",
    },
    {
      id: 2,
      cover : "image_url",
      title: "쇼펜하 아포리즘",
      author: "쇼펜하우어",
      isbn: "9791192125553",
      publisher : "포레스트 북스",
      pubDate : "2023-06-01",
      bookDescription: "blah blah",
      userBookDescription : "좋은 책!",
      price_standard: 10000,
      userBookPrice: 5000,
      isOpened: "공개",
      isOwned: "보유",
      tradeType: "TRADE",
      seriesId: 1,
      seriesName: "seriesName",
    },
    {
      id: 3,
      cover : "image_url",
      title: "쇼펜하우 아포리즘",
      author: "쇼펜하우어",
      isbn: "9791192625543",
      publisher : "포레스트 북스",
      pubDate : "2023-06-01",
      bookDescription: "blah blah",
      userBookDescription : "좋은 책!",
      price_standard: 10000,
      userBookPrice: 5000,
      isOpened: "공개",
      isOwned: "보유",
      tradeType: "EXCHANGE",
      seriesId: 4,
      seriesName: "seriesName",
    },
  ]);

  const filterResult = ref<Book[]>(mybookLists.value)
  

  const deleteBookitem = (isbn:string)=> {
    bookCartList.value = bookCartList.value.filter((item) => item.isbn != isbn )
    console.log(bookCartList.value)
    // bookCartList.value = [...bookCartList.value]
  }

  const deletebuttonState = ref(false)
  function toggledeletebutton() {
    deletebuttonState.value = !deletebuttonState.value
  }
  return { 
    toggledeletebutton, deletebuttonState, mybookLists, filterResult, bookCartList, bookSearchResultList, deleteBookitem }
},{persist: true})
