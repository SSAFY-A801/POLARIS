import { ref, computed, watch, watchEffect, reactive } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import type { LoginInfo } from './authcounter';

export interface Searchbook  {
  isbn: string,
  title: string,
  bookDescription: string|null,
  pubDate : Date,
  cover: string,
  publisher : string,
  author: string,
  priceStandard: number,
  isOpened?: boolean,
  isOwned?: boolean,
  userBookTradeType?: string|null,
  seriesId?: number|null,
  seriesName?: string|null,
}

export interface Book extends Searchbook  {
  id: number,
  userId: number,
  userBookDescription : string,
  userBookPrice: number|null,
}



export type DeleteBook = {
  id: number
}


export type Regcode = {
  id: number,
  si: string,
  gungu: string,
  dong: string,
} 


export type Following = {
  followingId: number,
  nickname: string,
  profileUrl: string,
  regcode: Regcode,
}


export type User = {
  id: number,
  profileUrl: string|null,
  nickname: string,
  regcode: Regcode,
  introduction: string|null,
  tradingCnt: number,
  exchangeCnt: number,
  followingsCnt: number,
}


export const profileCounterStore = defineStore('counter', () => {
  // 공통 변수
  const token = localStorage.getItem('user_token')
  const userInfoString = ref<string>(localStorage.getItem('user_info') ?? "");
  const BACK_API_URL = 'https://i10a801.p.ssafy.io:8082'
  

  
  // ProfilePage
  // 접속자

  const loginUser = ref<LoginInfo|null>(null)

  // 프로필 유저
  const profileUser = ref<User>({
    id: 1,
    profileUrl:"https://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2023/07/28/05455ec6-dec5-4016-81b4-97e568c8e249.jpg",
    nickname: "김연아",
    regcode: {
      id: 2629010700,
      si: "경기도",
      gungu: "군포시",
      dong: "군포2동"
    },
    introduction: "스케이트 저보다 잘타시면 네고 허락해드립니다.",
    tradingCnt: 132,
    exchangeCnt: 54,
    followingsCnt: 8,
  });

  watch(profileUser, (newValue, oldValue) => {
    return newValue
  });

  const getProfile = (id: number) => {
    axios({
      headers: {
        Authorization: `${token}`,
        "Content-Type": 'application/json'
      },
        method: 'get',
        url: `${BACK_API_URL}/profile/${id}`,
      })  
    .then((response) => {
      const userData = response.data['data']
      console.log(`${id}번유저정보`,userData)
      profileUser.value = userData
      })
      .catch((error)=> {
        console.error("에러발생: ",error)
      })
    }

  

  // ProfileUdpatePage
  
  // MyEssayPage
  // MyTradeListPage
  // MyExchangeListPage
  // MyScrapsPage
  // MyFavoritePage
  // MyPromotionPage
  
  // BookRegisterPage  
  type searchType = {
    [key: string]: string;
  }
  const searchbookLists = ref<Searchbook[]>([]);
  const bookCartList = ref<Searchbook[]>([])

  const searchAPIbookList = (query:string, searchCondition: string|null) => {
    if (query == ""){
      alert('검색어를 입력해주세요.')
    } else {
      const queryType = ref<string|null>(null)
      if (searchCondition == null) {
        queryType.value = 'Keyword'
      } else {
        const searchType:searchType = {
          '도서 제목': 'Title',
          '저자': 'Author',
          '출판사': 'Publisher',
        }
        queryType.value = searchType[searchCondition]
      }
      axios({
        method: 'get',
        url: `${BACK_API_URL}/api/search?query=${query}&queryType=${queryType.value}`
      })
      .then((response)=>{
        console.log(response.data)
        const data = response.data['item']
        console.log(data)
        const searchBooks = ref<Searchbook[]>([])
        if (data.length){
          data.forEach((book:any)=> {
            const date = new Date(book.pubDate)
            const searchBook :Searchbook = {
              isbn: book.isbn13,
              title: book.title,
              bookDescription: book.description,
              pubDate : date,
              cover: book.cover,
              publisher : book.publisher,
              author: book.author,
              priceStandard: book.priceStandard,
            }
            if(book.seriesInfo){
              searchBook.seriesId = book.seriesInfo.seriesId,
              searchBook.seriesName = book.seriesInfo.seriesName
            }
            searchBooks.value.push(searchBook)
          })
          searchbookLists.value = searchBooks.value
        } else {
          alert("검색목록을 불러올 수 없습니다.\n다른 검색어로 시도하세요.")
        }
      })
      .catch((error)=>{
        console.error(error)
      })
    };
  };

  
  // MyLibraryPage
  const deleteBookList = ref<DeleteBook[]>([])
  const bookSearchResultList = ref([]);
  const mybookLists = ref<Book[]>([]);

const getMybookList = (id:string)=> {
  axios({
    headers: {
      Authorization: `${token}`
    },
      method: 'get',
      url: `${BACK_API_URL}/book/${id}/library`,
    })  
  .then((response) => {
    const res = response.data.data
    if(res){
      mybookLists.value = res['books']
    } else{
      mybookLists.value = []
    }
    })
    .catch((error)=> {
      console.error("에러발생: ",error)
    })
}

  const filterResult = mybookLists.value

  const deletebuttonState = ref(false)
  function toggledeletebutton() {
    deletebuttonState.value = !deletebuttonState.value
  }
  return { 
    profileUser, getProfile,loginUser, userInfoString,
    searchAPIbookList, BACK_API_URL, token,
    getMybookList, toggledeletebutton, deletebuttonState, mybookLists, deleteBookList, searchbookLists, filterResult, bookCartList, bookSearchResultList }
},{persist: true})
