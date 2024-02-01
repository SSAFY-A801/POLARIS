<template>
    <nav class="bg-maintheme1 fixed top-0 w-full">
        <div class="px-12 mx-0 ">
            <div class="flex items-center justify-between h-24">
                <div class=" flex items-center w-full h-24">
                    <a class="flex-shrink-0" href="/">
                        <img class="w-8 h-8" src="" alt="Workflow"/>
                    </a>

                    <div class="flex items-baseline ml-10 space-x-2 sm:space-x-4 md:space-x-6 lg:space-x-8">
                        <router-link :to="{name: 'home'}" class="text-white">Home</router-link>
                        <router-link :to="{name: 'booksearch'}" class="text-white">도서 검색</router-link>
                        <router-link :to="{name: 'essaylist'}" class="text-white" >독후감 게시판</router-link>
                        <router-link :to="{name: 'promotionlist'}" class="text-white" >홍보 게시판</router-link>
                    </div>

                    <div class='flex items-center justify-center w-1/2 h-24 bg-maintheme1'>
                    <div class="flex w-full mx-10 rounded bg-white">
                        <input class=" w-full border-none bg-transparent px-4 py-1 text-gray-400 outline-none focus:outline-none " type="search" name="search" placeholder="검색어를 입력하세요" />
                        <button type="submit" class="m-2 rounded bg-maintheme1 px-4 py-2 text-white">
                            <svg class="fill-current h-6 w-6" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" id="Capa_1" x="0px" y="0px" viewBox="0 0 56.966 56.966" style="enable-background:new 0 0 56.966 56.966;" xml:space="preserve" width="512px" height="512px">
                            <path d="M55.146,51.887L41.588,37.786c3.486-4.144,5.396-9.358,5.396-14.786c0-12.682-10.318-23-23-23s-23,10.318-23,23  s10.318,23,23,23c4.761,0,9.298-1.436,13.177-4.162l13.661,14.208c0.571,0.593,1.339,0.92,2.162,0.92  c0.779,0,1.518-0.297,2.079-0.837C56.255,54.982,56.293,53.08,55.146,51.887z M23.984,6c9.374,0,17,7.626,17,17s-7.626,17-17,17  s-17-7.626-17-17S14.61,6,23.984,6z" />
                            </svg>
                        </button>
                    </div>
                    </div>

                    <div class="ml-auto flex flex-row">
                        <router-link :to="{name: 'login'}" v-if="!userToken" class="text-white mr-5">로그인</router-link>
                        <router-link :to="{name: 'signup'}" v-if="!userToken"  class="text-white">회원가입</router-link>
                        <router-link :to="{name: 'ProfilePage'}" v-if="userToken"  class="text-white mr-4">프로필</router-link>
                        <button v-if="userToken" @click="logout"  class="text-white ml-4 bg-transparent border-none outline-none focus:outline-none cursor-pointer">로그아웃</button>
                    </div>
                    
                </div>

                <div class="flex -mr-2 md:hidden">
                    <button class="text-gray-800 dark:text-white hover:text-gray-300 inline-flex items-center justify-center p-2 rounded-md focus:outline-none">
                        <svg width="20" height="20" fill="currentColor" class="w-8 h-8" viewBox="0 0 1792 1792" xmlns="http://www.w3.org/2000/svg">
                            <path d="M1664 1344v128q0 26-19 45t-45 19h-1408q-26 0-45-19t-19-45v-128q0-26 19-45t45-19h1408q26 0 45 19t19 45zm0-512v128q0 26-19 45t-45 19h-1408q-26 0-45-19t-19-45v-128q0-26 19-45t45-19h1408q26 0 45 19t19 45zm0-512v128q0 26-19 45t-45 19h-1408q-26 0-45-19t-19-45v-128q0-26 19-45t45-19h1408q26 0 45 19t19 45z">
                            </path>
                        </svg>
                    </button>
                </div>

            </div>
        </div>
    </nav>
</template>


<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const userToken = ref(localStorage.getItem('user_token'))
// const userToken = ref("Dd")
// console.log(userToken)

const router = useRouter()


watchEffect(() => {
    userToken.value = localStorage.getItem('user_token')
    console.log(localStorage.getItem('user_token'))
    console.log("watchEffect is running") 
    console.log(userToken.value)

})


//로그아웃
const logout = async () => {
    await axios.post('http://i10a801.p.ssafy.io:8082/user/logout', {}, {
    headers: {
    "Authorization" : `${userToken.value}`,
    "Content-Type": "application/json",
  }
  })
  .then(function (response) {
    // userToken.value = null
    localStorage.removeItem('user_token')
    userToken.value = null
    alert("로그아웃 되었습니다")
    console.log(response.status)
    router.push({name: 'home'})
  })

}

</script>

<style scoped>

</style>