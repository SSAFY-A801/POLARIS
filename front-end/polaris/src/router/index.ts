import { createRouter, createWebHistory } from 'vue-router'
import MainPage from '@/views/MainPage.vue'
import LoginPage from '@/views/LoginPage.vue'
import SignupPage from '@/views/SignupPage.vue'
import BookSearchPage from '@/views/BookSearchPage.vue'
import EssayListPage from '@/views/EssayListPage.vue'
import PromotionListPage from '@/views/PromotionListPage.vue'
import PasswordSearchPage from '@/views/PasswordSearchPage.vue'
import ProfilePage from '@/views/profile/ProfilePage.vue'
import MyLibraryPage from '@/views/profile/mylibrary/MyLibraryPage.vue'
import MyScrapsPage from '@/views/profile/like/MyScrapsPage.vue'
import MyFavoritesPage from '@/views/profile/like/MyFavoritesPage.vue'
import MyEssayPage from '@/views/profile/myarticle/MyEssayPage.vue'
import MyPromotionPage from '@/views/profile/myarticle/MyPromotionPage.vue'
import MyArticlePage from '@/views/profile/myarticle/MyArticlePage.vue'
import ProfileUpdatePage from '@/views/profile/userinfo/ProfileUpdatePage.vue'
import PasswordChangePage from '@/views/profile/userinfo/PasswordChangePage.vue'
import BookRegisterPage from '@/views/profile/mylibrary/BookRegisterPage.vue'
import MyTradeListPage from '@/views/profile/mytradeinfo/MyTradeListPage.vue'
import MyExchangeListPage from '@/views/profile/mytradeinfo/MyExchangeListPage.vue'
import FollowingListPage from '@/views/profile/FollowingListPage.vue'
import BookDetailPage from '@/views/profile/mylibrary/BookDetailPage.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [

    {//메인페이지
      path: '/',
      name: 'home',
      component: MainPage,
    },
    { // 로그인 페이지
      path: '/login',
      name: 'login',
      component: LoginPage,
    },
    { // 회원가입 페이지
      path: '/signup',
      name: 'signup',
      component: SignupPage,
    },
    {//도서검색 페이지
      path: '/booksearch',
      name: 'booksearch',
      component: BookSearchPage,
    },
    {//독후감 게시판 페이지
      path: '/essaylist',
      name: 'essaylist',
      component: EssayListPage,
    },
    {//홍보 게시판 페이지
      path: '/promotionlist',
      name: 'promotionlist',
      component: PromotionListPage,
    },
    {//비밀번호 찾기
      path: '/passwordsearch',
      name: 'passwordsearch',
      component: PasswordSearchPage,
    },
    // Profile
    {
      path: '/profile',
      redirect: '/profile/library',
      name: 'ProfilePage',
      component: ProfilePage,
      children: [
        { path: 'library',
          name: 'MyLibraryPage',
          component: MyLibraryPage,
        },
        { path: 'myscraps',
          name: 'MyScrapsPage',
          component: MyScrapsPage 
        },
        { path: 'myfavorites',
          name: 'MyFavoritesPage',
          component: MyFavoritesPage
         },
        { path: 'myarticle',
          redirect: '/profile/myarticle/myessay',
          name: 'MyArticlePage', 
          component: MyArticlePage, 
          // 나의독후감, 나의홍보게시글
          children: [
            { path: 'myessay',
            name: 'MyEssayPage',
            component: MyEssayPage 
          },
          { path: 'myscraps',
            name: 'MyPromotionPage',
            component: MyPromotionPage 
          },
          ]
        },
      ],
    },
    // Profile 수정
    { 
      path: '/profile/update',
      name: 'ProfileUpdatePage',
      component: ProfileUpdatePage, 
    },
    // Password 변경
    { 
      path: '/passwordchange',
      name: 'PasswordChangePage',
      component: PasswordChangePage, 
    },

    // 서재에 도서등록 => 모달 제작이 되면 없앨 예정
    { 
      path: '/registerbook',
      name: 'BookRegisterPage',
      component: BookRegisterPage,
    },
    // 거래내역 확인
    { 
      path: '/profile/trade-books',
      name: 'MyTradeListPage',
      component: MyTradeListPage,
    },
    { 
      path: '/profile/exchange-books',
      name: 'MyExchangeListPage',
      component: MyExchangeListPage,
    },
    // 팔로잉 목록 확인
    { 
      path: '/profile/follow',
      name: 'FollowingListPage',
      component: FollowingListPage,
    },
    // 사용자 도서 상세 페이지
    {
      path: '/bookdetail',
      name: 'BookDetailPage',
      component: BookDetailPage,

    }
  ]
})

export default router
