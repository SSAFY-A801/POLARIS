import { ref, computed, watch } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';

export interface Searchbook  {
  isbn: string,
  title: string,
  // 설정 안한 경우 빈 문자열(string)
  bookDescription: string|null,
  pubDate : string,
  cover: string,
  publisher : string,
  author: string,
  price_standard: number,
  isOpened?: number,
  isOwned?: number,
  userBooktradeType?: string|null,
  seriesId?: number|null,
  seriesName?: string|null,
}

export interface Book extends Searchbook  {
  id?: number,
  userBookDescription : string,
  userBookPrice: number|null,
}

export type Regcode = {
  id: number,
  si: string,
  gungu: string,
  dong: string,
} 


export type User = {
  id: number,
  profile_url: string|null,
  nickname: string|null,
  regcode_id: Regcode,
  introduction: string,
  trade_cnt: number,
  exchange_cnt: number,
  followings: number,
}

export const profileCounterStore = defineStore('counter', () => {
  // 공통 변수
  
  
  // ProfilePage
  const user: User = {
    id: 1,
    profile_url:"https://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2023/07/28/05455ec6-dec5-4016-81b4-97e568c8e249.jpg",
    nickname: "절대존엄퀸갓상시숭배피겨올타임레전드",
    regcode_id: {
      id: 2629010700,
      si: "경기도",
      gungu: "군포시",
      dong: "군포2동"
    },
    introduction: "스케이트 저보다 잘타시면 네고 허락해드립니다.",
    trade_cnt: 132,
    exchange_cnt: 54,
    followings: 8,
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
  
  type searchType = {
    [key: string]: string;
  }
  const searchbookLists = ref<Searchbook[]>([]);
  const bookCartList = ref<Searchbook[]>([])
  const Aladin_API_URL = '/api/ItemSearch.aspx'
  const TTBKey = 'ttbkimsw28261657004'
  
  const searchAPIbookList = (Query:string, searchCondition: string|null) => {
    if (Query == ""){
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
        url: `${Aladin_API_URL}?ttbkey=${TTBKey}&Query=${Query}&QueryType=${QueryType.value}
        &MaxResults=20&start=1&SearchTarget=Book&output=js&Version=20131101&Cover=MidBig`,
      })
      .then((response)=>{
        const data = response.data['item']
        const searchBooks = ref<Searchbook[]>([])
        data.forEach((book:any)=> {
          const searchBook :Searchbook = {
            isbn: book.isbn,
            title: book.title,
            bookDescription: book.description,
            pubDate : book.pubDate,
            cover: book.cover,
            publisher : book.publisher,
            author: book.author,
            price_standard: book.priceStandard,
          }
          if(book.seriesInfo){
            searchBook.seriesId = book.seriesInfo.seriesId,
            searchBook.seriesName = book.seriesInfo.seriesName
          }
          searchBooks.value.push(searchBook)
        })
        searchbookLists.value = searchBooks.value
      })
      .catch((error)=>{
        console.error(error)
      })
    };
  };
  
  // MyLibraryPage
  
  // const BACK_API_URL = 'http://i10a801.p.ssafy.io:8082'
  const token = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW5namluaGE0QGdtYWlsLmNvbSIsImF1dGgiOiJBVVRIT1JJVFkiLCJpZCI6MTYsImVtYWlsIjoia2luZ2ppbmhhNEBnbWFpbC5jb20iLCJuaWNrbmFtZSI6IuuPme2DhOu2iOyjvOuoueq5gOuvuOyEnCIsImV4cCI6MTcyNDc1MzQ0NX0.BPYiE7fRj2n1_fssmIFJsgYdj5yTYYGcv5yTmZ8jv20'
  const deleteBookList = ref<Book[]>([])
  const bookSearchResultList = ref([]);
  const mybookLists = ref<Book[]>([]);

const getMybookList = ()=> {
  axios({
    headers: {
      Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW5namluaGEyQGdtYWlsLmNvbSIsImF1dGgiOiJBVVRIT1JJVFkiLCJpZCI6MTQsImVtYWlsIjoia2luZ2ppbmhhMkBnbWFpbC5jb20iLCJleHAiOjE3MjQ2ODc5MjJ9.lzyGKu9Vgq3aBItSvODOKmRzE59WrRwx-win-v4uHKI'
    },
      method: 'get',
      url: '/another-api/book/1/library',
    })  
  .then((response) => {
    const res = response.data
    mybookLists.value = res.data['books']
    })
    .catch((error)=> {
      console.error(error)
    })
}

  // const getMybookList = () => {
  //   axios.get('/another-api/profile/1',{
  //     headers: {
  //       Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW5namluaGE0QGdtYWlsLmNvbSIsImF1dGgiOiJBVVRIT1JJVFkiLCJpZCI6MTYsImVtYWlsIjoia2luZ2ppbmhhNEBnbWFpbC5jb20iLCJuaWNrbmFtZSI6IuuPme2DhOu2iOyjvOuoueq5gOuvuOyEnCIsImV4cCI6MTcyNDc1MzQ0NX0.BPYiE7fRj2n1_fssmIFJsgYdj5yTYYGcv5yTmZ8jv20',
  //     },
  //   })
  //       .then((response) => {
  //         console.log("response 결과:")
  //         console.log(response.data)
  //   })
  //   .catch((error) => {
  //     console.log(error)
  //   })
  // }
  const filterResult = mybookLists.value




  const deletebuttonState = ref(false)
  function toggledeletebutton() {
    deletebuttonState.value = !deletebuttonState.value
  }
  return { 
    user, 
    searchAPIbookList,
    getMybookList, toggledeletebutton, deletebuttonState, mybookLists, deleteBookList, searchbookLists, filterResult, bookCartList, bookSearchResultList }
},{persist: true})