import { defineStore } from 'pinia'

export type LoginInfo = {
  profileUrl: string
  access: string
  nickname: string
  refresh: string
  id: string
  email: string
}

export const useUserStore = defineStore({
  id: 'user',
  state: () => ({
    loginInfo: null as LoginInfo | null
  }),
  actions: {
    setLoginInfo(loginInfo: LoginInfo) {
      this.loginInfo = loginInfo
    }
  }
})



//꺼낼때 사용법(ex.nickname)

// <template>
//   <div>
//     <h1>{{ userStore.loginInfo?.nickname }}</h1>
//   </div>
// </template>

// <script setup lang="ts">
// import { useUserStore } from '@/stores/authcounter' 

// const userStore = useUserStore()
// </script>
