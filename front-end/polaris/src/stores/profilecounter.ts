import { ref, computed, watch, watchEffect } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import axiosInstance from '@/services/axios'
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
  nickname: string,
  userId: number,
  userBookDescription : string,
  userBookPrice: number|null,
  profileUrl: string,
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
  const token = ref(localStorage.getItem('user_token'));
  
  // const loginUserId = JSON.parse(localStorage.getItem('user_info')||"").id
  const BACK_API_URL = 'https://i10a801.p.ssafy.io:8082'
  

  
  // ProfilePage
  // 접속자
  
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
    tradingCnt: 12,
    exchangeCnt: 10,
    followingsCnt: 8,
  });
  
  

  watch(profileUser, (newValue, oldValue) => {
    return newValue
  });
  

  // const loginUser = ref<User|null>(null)

  // const getloginUser = (id: number) => {
  //   axiosInstance.value({
  //     headers: {
  //       Authorization: `${token}`,
  //       "Content-Type": 'application/json'
  //     },
  //     method: 'get',
  //     url: `${BACK_API_URL}/profile/${id}`,
  //   })  
  //   .then((response) => {
  //     const userData = response.data['data']
  //     loginUser.value = userData
  //   })
  //   .catch((error)=> {
  //     console.error("에러발생: ",error)
  //   })
  // }
  

  const getProfile = (id: number) => {
    axiosInstance.value({
      headers: {
        Authorization: `${token.value}`,
        "Content-Type": 'application/json'
      },
      method: 'get',
      url: `${BACK_API_URL}/profile/${id}`,
    })  
    .then((response) => {
      const userData = response.data['data']
      profileUser.value = userData
    })
    .catch((error)=> {
      console.error("에러발생: ",error)
    })
  }
  
  
  
  // ProfileUdpatePage
  // const isMe = computed(()=> {
  //   return profileUser.value.id == Number(loginUserId.value)
  // });


  // MyEssayPage
  // MyTradeListPage
  // MyExchangeListPage
  // MyScrapsPage
  const myscraps = ref([])
  const getMyscraps = (id: string) => {
    if(id){
      axiosInstance.value({
        headers: {
          Authorization: `${token.value}`,
          'Content-Type': 'application/json'
        },
        method: 'get',
        url: `${BACK_API_URL}/essay/${id}/scraps`
      })
      .then((response) => {
        console.log(response.data)
        const res = response.data.data
        if(res){
          myscraps.value = res.scrapPosts
        } else {
          myscraps.value = []
        }
    })
      .catch((error) => {
        console.error(error);
        
      })
    }
  }
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
      axiosInstance.value({
        method: 'get',
        url: `${BACK_API_URL}/api/search?query=${query}&queryType=${queryType.value}`
      })
      .then((response)=>{
        console.log(response.data)
        const data = response.data['item']
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
  axiosInstance.value({
    headers: {
      Authorization: `${token.value}`
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

    return mybookLists.value
}

  const filterResult = mybookLists.value

  const deletebuttonState = ref(false)
  function toggledeletebutton() {
    deletebuttonState.value = !deletebuttonState.value
  }
  return { 
    profileUser, getProfile,
    // loginUserId,isMe,
    searchAPIbookList, BACK_API_URL, token,
    getMyscraps, myscraps,
    getMybookList, toggledeletebutton, deletebuttonState, mybookLists, deleteBookList, searchbookLists, filterResult, bookCartList, bookSearchResultList }
},{persist: true})
