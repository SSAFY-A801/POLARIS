import { createRouter, createWebHistory } from 'vue-router'
import Swal from 'sweetalert2'
// 메인 기능
import MainPage from '@/views/MainPage.vue'
import LoginPage from '@/views/LoginPage.vue'
import SignupPage from '@/views/SignupPage.vue'
import BookSearchPage from '@/views/BookSearchPage.vue'
import PasswordSearchPage from '@/views/PasswordSearchPage.vue'
import KakaoLogin from '@/views/KakaoLogin.vue'
// 프로필
import ProfilePage from '@/views/profile/ProfilePage.vue'
import MyLibraryPage from '@/views/profile/mylibrary/MyLibraryPage.vue'
import MyScrapsPage from '@/views/profile/like/MyScrapsPage.vue'
import MyEssayPage from '@/views/profile/myarticle/MyEssayPage.vue'
import ProfileUpdatePage from '@/views/profile/userinfo/ProfileUpdatePage.vue'
import PasswordChangePage from '@/views/profile/userinfo/PasswordChangePage.vue'
import BookRegisterPage from '@/views/profile/mylibrary/BookRegisterPage.vue'
import MyTradeListPage from '@/views/profile/mytradeinfo/MyTradeListPage.vue'
import MyExchangeListPage from '@/views/profile/mytradeinfo/MyExchangeListPage.vue'
// 채팅
import BookDetailPage from '@/views/profile/mylibrary/BookDetailPage.vue'
import ChattingListPage from '@/views/chat/ChattingListPage.vue'
import ChattingRoomPage from '@/views/chat/ChattingRoomPage.vue'
import SellChattingBox from '@/components/chat/SellChattingBox.vue'
import ChangeChattingBox from '@/components/chat/ChangeChattingBox.vue'
import ConnectSocket from '@/components/chat/ConnectSocket.vue'
import BestsellerdetailPage from '@/components/main/BestsellerdetailPage.vue'
import UserPopularBookDatailPage from '@/components/main/UserPopularBookDetailPage.vue'
import TestFirebase from '@/components/chat/TestFirebase.vue'
// 독후감
import EssayListPage from '@/views/essay/EssayListPage.vue'
import EssayDetailPage from '@/views/essay/EssayDetailPage.vue'
import EssayCreatePage from '@/views/essay/EssayCreatePage.vue'
import EssayEditPage from '@/views/essay/EssayEditPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      //메인페이지
      path: '/',
      name: 'home',
      component: MainPage
    },
    {
      // 로그인 페이지
      path: '/login',
      name: 'login',
      component: LoginPage
    },
    {
      // 회원가입 페이지
      path: '/signup',
      name: 'signup',
      component: SignupPage
    },
    {
      //도서검색 페이지
      path: '/booksearch',
      name: 'booksearch',
      component: BookSearchPage
    },

    {
      //비밀번호 찾기
      path: '/passwordsearch',
      name: 'passwordsearch',
      component: PasswordSearchPage
    },
    {
      //베스트셀러 상세 페이지
      path: '/bestseller/:id',
      name: 'bestsellerdatail',
      component: BestsellerdetailPage,
      props: true
    },
    {
      //사용자 인기 도서 상세 페이지
      path: '/userpopularbook/:id',
      name: 'userpopularbookdatail',
      component: UserPopularBookDatailPage,
      props: true
    },
    // 프로필
    {
      path: '/profile/:id',
      redirect: { name: 'MyLibraryPage' },
      name: 'ProfilePage',
      component: ProfilePage,
      children: [
        { path: 'library', name: 'MyLibraryPage', component: MyLibraryPage },
        { path: 'myscraps', name: 'MyScrapsPage', component: MyScrapsPage },
        { path: 'myessay', name: 'MyEssayPage', component: MyEssayPage }
      ]
    },
    // Profile 수정
    {
      path: '/profile/:id/update',
      name: 'ProfileUpdatePage',
      component: ProfileUpdatePage
    },
    // Password 변경
    {
      path: '/profile/:id/password',
      name: 'PasswordChangePage',
      component: PasswordChangePage
    },

    // 서재에 도서등록 => 모달 제작이 되면 없앨 예정
    {
      path: '/book/:id/library',
      name: 'BookRegisterPage',
      component: BookRegisterPage
    },
    // 거래내역 확인
    {
      path: '/profile/:id/trade-books',
      name: 'MyTradeListPage',
      component: MyTradeListPage
    },
    {
      path: '/profile/:id/exchange-books',
      name: 'MyExchangeListPage',
      component: MyExchangeListPage
    },
    // 사용자 도서 상세 페이지
    {
      path: '/profile/:id/libray/:isbn',
      name: 'BookDetailPage',
      component: BookDetailPage
    },
    // 채팅방 관련 라우터
    {
      path: '/:id/chat/',
      name: 'chat',
      component: ChattingListPage
    },
    {
      path: '/chat/:userId',
      name: 'chatting',
      component: ChattingRoomPage
    },
    {
      path: '/chat/:chatroomId/sell',
      name: 'sellchattingbox',
      component: SellChattingBox
    },
    {
      path: '/chat/:chatroomId/change',
      name: 'changechattingbox',
      component: ChangeChattingBox
    },
    // {
    //   path: '/chat/start/:chatRoomId',
    //   name: 'connectSocket',
    //   component: ConnectSocket
    // },
    {
      path: '/test',
      name: 'testfirebase',
      component: TestFirebase
    },

    // 독후감
    {
      path: '/essaylist',
      name: 'essaylist',
      component: EssayListPage
    },
    {
      path: '/essaylist/:essayId',
      name: 'essaydetail',
      component: EssayDetailPage
    },
    {
      path: '/essaylist/create',
      name: 'essaycreate',
      component: EssayCreatePage
    },
    {
      path: '/essaylist/:essayId/edit',
      name: 'essayedit',
      component: EssayEditPage
    },
    //카카오로그인
    {
      path: '/kakaologin',
      name: 'kakaologin',
      component: KakaoLogin
    }
  ]
})

export default router

router.beforeEach((to, from, next) => {
  // 내부에 선언
  const userToken = localStorage.getItem('user_token')
  if ((to.path.startsWith('/profile') || to.name === 'essaycreate') && !userToken) {
    // 'ProfilePage' 라우트이면서 userToken이 없는 경우
    Swal.fire({
      title: '로그인이 필요한 작업입니다.',
      icon: 'error'
    }).then(() => {
      next({ path: '/login' })
    })
  } else {
    // 'ProfilePage' 이외의 다른 라우트 또는 'ProfilePage'이지만 userToken이 있는 경우
    next()
  }
})
