import { createRouter, createWebHistory } from 'vue-router'
import MainPage from '@/views/MainPage.vue'
import LoginPage from '@/views/LoginPage.vue'
import SignupPage from '@/views/SignupPage.vue'
import BookSearchPage from '@/views/BookSearchPage.vue'
import EssayListPage from '@/views/EssayListPage.vue'
import PromotionListPage from '@/views/PromotionListPage.vue'
import ProfilePage from '@/views/ProfilePage.vue'

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
    {//프로필
      path: '/profile',
      name: 'profile',
      component: ProfilePage,
    },
  ]
})

export default router
