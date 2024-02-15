import { ref, computed, watch } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import axiosInstance from '@/services/axios'
import Swal from 'sweetalert2'

export interface Searchbook {
  isbn: string
  title: string
  bookDescription: string | null
  pubDate: Date
  cover: string
  publisher: string
  author: string
  priceStandard: number
  isOpened?: boolean
  isOwned?: boolean
  userBookTradeType?: string | null
  seriesId?: number | null
  seriesName?: string | null
}

export interface Book extends Searchbook {
  id: number
  nickname: string
  userId: number
  userBookDescription: string
  userBookPrice: number | null
  profileUrl: string
}

export type DeleteBook = {
  id: number
}

export type Regcode = {
  id: number
  si: string
  gungu: string
  dong: string
}

export type Following = {
  followingId: number
  nickname: string
  profileUrl: string
  regcode: Regcode
}

export type User = {
  id: number
  profileUrl: string | null
  nickname: string
  regcode: Regcode
  introduction: string | null
  tradingCnt: number
  exchangeCnt: number
  followingsCnt: number
}

export type ExchangeInfo = {
  bookTitle: string
  nickname: string
  tradeDate: Date
  tradeId: number
  userBookId: number
  userId: number
}

export type TradeInfo = {
  tradeId: number
  senderId: number
  userBookId: number
  oppositeNickname: string
  bookTitle: string
  receiverId: number
  price: number
  tradeDate: Date
}

export const profileCounterStore = defineStore(
  'counter',
  () => {
    // 공통 변수
    const token = ref(localStorage.getItem('user_token') || null)
    // const BACK_API_URL = 'https://i10a801.p.ssafy.io:8082'
    const BACK_API_URL = import.meta.env.VITE_API_KEY

    // ProfilePage
    // 접속자

    // 프로필 유저
    const profileUser = ref<User | null>(null)

    watch(profileUser, (newValue, oldValue) => {
      // console.log(newValue)
    })

    const getProfile = (id: number) => {
      axiosInstance
        .value({
          headers: {
            Authorization: `${token.value}`,
            'Content-Type': 'application/json'
          },
          method: 'get',
          url: `${BACK_API_URL}/profile/${id}`
        })
        .then((response) => {
          const userData = response.data['data']
          profileUser.value = userData
        })
        .catch((error) => {
          console.error('에러발생: ', error)
        })
    }

    // MyScrapsPage
    const myscraps = ref([])
    const getMyscraps = (id: string) => {
      if (id) {
        axiosInstance
          .value({
            headers: {
              Authorization: `${token.value}`,
              'Content-Type': 'application/json'
            },
            method: 'get',
            url: `${BACK_API_URL}/essay/${id}/scraps`
          })
          .then((response) => {
            // console.log(response.data)
            const res = response.data.data
            if (res) {
              myscraps.value = res.scrapPosts
            } else {
              myscraps.value = []
            }
          })
          .catch((error) => {
            console.error(error)
          })
      }
    }
    // MyFavoritePage
    // MyPromotionPage

    // BookRegisterPage
    type searchType = {
      [key: string]: string
    }
    const searchbookLists = ref<Searchbook[]>([])
    const bookCartList = ref<Searchbook[]>([])

    const searchAPIbookList = (query: string, searchCondition: string | null) => {
      if (query == '') {
        Swal.fire({
          title: '검색어를 입력해주세요.',
          icon: 'error'
        })
      } else {
        const queryType = ref<string | null>(null)
        if (searchCondition == null) {
          queryType.value = 'Keyword'
        } else {
          const searchType: searchType = {
            '도서 제목': 'Title',
            저자: 'Author',
            출판사: 'Publisher'
          }
          queryType.value = searchType[searchCondition]
        }
        axiosInstance
          .value({
            method: 'get',
            url: `${BACK_API_URL}/api/search?query=${query}&queryType=${queryType.value}`
          })
          .then((response) => {
            // console.log(response.data)
            const data = response.data['item']
            const searchBooks = ref<Searchbook[]>([])
            if (data.length) {
              data.forEach((book: any) => {
                const date = new Date(book.pubDate)
                const searchBook: Searchbook = {
                  isbn: book.isbn13,
                  title: book.title,
                  bookDescription: book.description,
                  pubDate: date,
                  cover: book.cover,
                  publisher: book.publisher,
                  author: book.author,
                  priceStandard: book.priceStandard
                }
                if (book.seriesInfo) {
                  ;(searchBook.seriesId = book.seriesInfo.seriesId),
                    (searchBook.seriesName = book.seriesInfo.seriesName)
                }
                searchBooks.value.push(searchBook)
              })
              searchbookLists.value = searchBooks.value
            } else {
              Swal.fire({
                title: '검색목록을 불러올 수 없습니다.\n다른 검색어로 시도하세요.',
                icon: 'error'
              })
            }
          })
          .catch((error) => {
            console.error(error)
            searchbookLists.value = []
          })
      }
    }

    // MyLibraryPage
    const deleteBookList = ref<DeleteBook[]>([])
    const bookSearchResultList = ref([])
    const mybookLists = ref<Book[]>([])

    const getMybookList = (id: string) => {
      axiosInstance
        .value({
          headers: {
            Authorization: `${token.value}`
          },
          method: 'get',
          url: `${BACK_API_URL}/book/${id}/library`
        })
        .then((response) => {
          const res = response.data.data
          if (res) {
            mybookLists.value = res['books']
          } else {
            mybookLists.value = []
          }
        })
        .catch((error) => {
          console.error('에러발생: ', error)
        })
    }

    const filterResult = mybookLists.value

    const deletebuttonState = ref(false)
    function toggledeletebutton() {
      deletebuttonState.value = !deletebuttonState.value
    }
    return {
      profileUser,
      getProfile,
      searchAPIbookList,
      BACK_API_URL,
      token,
      getMyscraps,
      myscraps,
      getMybookList,
      toggledeletebutton,
      deletebuttonState,
      mybookLists,
      deleteBookList,
      searchbookLists,
      filterResult,
      bookCartList,
      bookSearchResultList
    }
  },
  { persist: true }
)
