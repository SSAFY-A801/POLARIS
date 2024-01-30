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

export type User = {
  id: number,
  profile_url: string,
  nickname: string,
  regcode_id: string,
  introduction: string,
  trade_cnt: number,
  exchange_cnt: number,
  followings: number[],
}

export const profileCounterStore = defineStore('counter', () => {
  // 공통 변수
  const BACK_API_URL = '백엔드서버'
  const Aladin_API_URL = 'http://www.aladin.co.kr/ttb/api/ItemSearch.aspx'
  const TTBKey = 'ttbonyo91219001'

  
  // ProfilePage
  const user: User = {
    id: 1,
    profile_url: ("./assets/images/room1.jpg"),
    nickname: "역삼동미친고양이",
    regcode_id: "2629010700",
    introduction: "네고 사절함.",
    trade_cnt: 132,
    exchange_cnt: 54,
    followings: [3,6,2,8],
  }
  // ProfileUdpatePage

  // MyEssayPage
  // MyTradeListPage
  // MyExchangeListPage
  // MyScrapsPage
  // MyFavoritePage
  // MyPromotionPage
  // FollowingListPage

  // BookRegisterPage
  const bookCartList = ref<Book[]>([])

  type searchType = {
    [key: string]: string;
  }

  const searchAPIbookList = (Query:string, searchCondition: string|null) => {
    console.log(Query, searchCondition)
    if (Query == null){
      alert('검색어를 입력해주세요.')
    } else {
      const QueryType = ref<string|null>(null)
      if (searchCondition == null) {
        QueryType.value = 'Keyword'
      } else {
        const searchType:searchType = {
          '도서 제목': 'Title',
          '저자': 'Author',
          '출판사': 'Publisher',
        }
        QueryType.value = searchType[searchCondition]
      }
      axios({
        method: 'get',
        url: `${Aladin_API_URL}?ttbkey=[${TTBKey}]&Query=${Query}&QueryType=${QueryType.value}&MaxResults=10&start=1&SearchTarget=Book&output=json`,
      })
      .then((response)=>{
        console.log(response.data)
      })
      .catch(()=>{
  
      })
    }
  }
  
  
  // const logBookCartList = computed(() => {
  //   return bookCartList.value;
  // });
  
  // watch(logBookCartList, () => {});



  // MyLibraryPage
  // const getMybookList = () => {
  //   axios({
  //     method: 'get',
  //     url: '/profile/library',
  //   })
  //   .then((response) => {
  //     console.log(response.data)
  //   })
  //   .catch((error) => {
  //     console.log(error)
  //   })
  // }
  const deleteBookList = ref<string[]>([])
  const bookSearchResultList = ref([])
  const mybookLists = ref([
    {
      id: 1,
      cover : "image_url",
      title: "아포리즘",
      author: "쇼펜하우어",
      isbn: "111",
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
      title: "쇼펜하 아포",
      author: "쇼펜하우어",
      isbn: "222",
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
      isbn: "555",
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
  const searchbookLists = ref([
    {
      id: 1,
      cover : "image_url",
      title: "오펜하이머",
      author: "줄리어스",
      isbn: "090",
      publisher : "포레스트 북스",
      pubDate : "2023-06-01",
      bookDescription: "blah blah",
      userBookDescription : "좋은 책!",
      price_standard: 10000,
      userBookPrice: 5000,
      isOpened: "공개",
      isOwned: "보유",
      tradeType: "TRADE",
      seriesId: 7,
      seriesName: "seriesName",
    },
    {
      id: 2,
      cover : "image_url",
      title: "그대만을",
      author: "쇼펜하우어",
      isbn: "971",
      publisher : "포레스트 북스",
      pubDate : "2023-06-11",
      bookDescription: "blah blah",
      userBookDescription : "좋은 책!",
      price_standard: 1000,
      userBookPrice: 50000,
      isOpened: "공개",
      isOwned: "보유",
      tradeType: "TRADE",
      seriesId: 1,
      seriesName: "seriesName",
    },
    {
      id: 3,
      cover : "image_url",
      title: "아프니깐 청춘이다",
      author: "김난도",
      isbn: "88865",
      publisher : "포레스트 북스",
      pubDate : "2018-06-01",
      bookDescription: "blah blah",
      userBookDescription : "좋은 책!",
      price_standard: 10000,
      userBookPrice: 5000,
      isOpened: "비공개",
      isOwned: "보유",
      tradeType: "UNDEFINED",
      seriesId: 14,
      seriesName: "seriesName",
    },
  ]);

  const filterResult = ref<Book[]>(mybookLists.value)




  const deletebuttonState = ref(false)
  function toggledeletebutton() {
    deletebuttonState.value = !deletebuttonState.value
  }
  return { 
    user,
    searchAPIbookList,
    toggledeletebutton, deletebuttonState, mybookLists, deleteBookList, searchbookLists, filterResult, bookCartList, bookSearchResultList }
},{persist: true})
