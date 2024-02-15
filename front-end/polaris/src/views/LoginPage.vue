<template>
    <Navvar></Navvar>
<div id="logincontainer" class="mt-60 flex flex-col w-full max-w-md px-4 py-8 bg-white rounded-lg shadow sm:px-6 md:px-8 lg:px-10 border-2 font-[gowun-dodum]" >
    <div class="self-center mt-4 mb-4 text-xl font-bold text-maintheme1 sm:text-2xl">
        로그인
    </div>
    <div class="mt-8">
        <form action="#" autoComplete="off">
            <div class="flex flex-col mb-2">
                <div class="flex relative mb-4 ">
                    <span class="rounded-l-md inline-flex  items-center px-3 border-t bg-white border-l border-b  border-gray-300 text-gray-500 shadow-sm text-sm ">
                        <svg class="ml-2 mr-2" width="15" height="15" fill="currentColor" viewBox="0 0 1792 1792" xmlns="http://www.w3.org/2000/svg">
                            <path d="M1792 710v794q0 66-47 113t-113 47h-1472q-66 0-113-47t-47-113v-794q44 49 101 87 362 246 497 345 57 42 92.5 65.5t94.5 48 110 24.5h2q51 0 110-24.5t94.5-48 92.5-65.5q170-123 498-345 57-39 100-87zm0-294q0 79-49 151t-122 123q-376 261-468 325-10 7-42.5 30.5t-54 38-52 32.5-57.5 27-50 9h-2q-23 0-50-9t-57.5-27-52-32.5-54-38-42.5-30.5q-91-64-262-182.5t-205-142.5q-62-42-117-115.5t-55-136.5q0-78 41.5-130t118.5-52h1472q65 0 112.5 47t47.5 113z">
                            </path>
                        </svg>
                    </span>
                    <input v-model="userEmail" type="text" id="user_email" class=" h-14 rounded-r-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" placeholder="이메일을 입력하세요"/>
                    </div>
                </div>
                <div class="flex flex-col mb-12">
                    <div class="flex relative ">
                        <span class="rounded-l-md inline-flex  items-center px-3 border-t bg-white border-l border-b  border-gray-300 text-gray-500 shadow-sm text-sm">
                            <svg class="ml-1.5 mr-1.5" width="18" height="18" fill="currentColor" viewBox="0 0 1792 1792" xmlns="http://www.w3.org/2000/svg">
                                <path d="M1376 768q40 0 68 28t28 68v576q0 40-28 68t-68 28h-960q-40 0-68-28t-28-68v-576q0-40 28-68t68-28h32v-320q0-185 131.5-316.5t316.5-131.5 316.5 131.5 131.5 316.5q0 26-19 45t-45 19h-64q-26 0-45-19t-19-45q0-106-75-181t-181-75-181 75-75 181v320h736z">
                                </path>
                            </svg>
                        </span>
                        <input v-model="userPassword" type="password" id="user_password" class=" h-14 rounded-r-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" placeholder="비밀번호를 입력하세요"/>
                        </div>
                    </div>
                    <div  class="flex w-full">
                        <button @click.prevent="userLogin" type="submit" class="h-14 py-3 px-4  bg-maintheme1  text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md  rounded-lg ">
                            로그인
                        </button>
                    </div>
                </form>
            </div>
            <div class="flex items-center justify-center mt-2">
                <router-link :to="{name:'passwordsearch'}">비밀번호 찾기 </router-link>     
                            <p class="text-gray-500 ml-2"> / </p>
                <router-link :to="{name:'signup'}" class="ml-2">회원가입 </router-link>   
            </div>
            <hr class="mt-6 mb-2 border-t border-gray-200">
            <button @click.prevent="kakaoLogin" type="submit" class="flex items-center justify-center mt-4 mb-4 py-3 px-4 bg-kakao_yellow text-kakao_brown w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md rounded-lg">
                <img src="@/assets/styles/kakaosymbol.png" alt="Kakao 로그인 아이콘" class="mr-2">
                카카오 로그인
            </button>
        </div>
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import router from '@/router';
import { useUserStore } from '@/stores/authcounter'
import Swal from 'sweetalert2'

const userEmail = ref('')
const userPassword = ref('')

const userLogin = async () => {
    if ( userEmail.value && userPassword.value ) {
    await axios.post(`${import.meta.env.VITE_API_KEY}/user/login`, 
  JSON.stringify({
    email: userEmail.value,
    password: userPassword.value
    }),
    {headers: {
      "Content-Type": "application/json",
    }}
  
)
.then(function (response) {
    Swal.fire({
          title: "로그인 성공! ",
          icon: "success"
        });
  localStorage.setItem('user_token',(response.data.data.access))
  localStorage.setItem('refresh_token',(response.data.data.refresh))
  localStorage.setItem('user_info' , JSON.stringify(response.data.data))
//   console.log(localStorage.getItem('user_token'))
//   console.log(localStorage.getItem('refresh_token'))
//   const userStore = useUserStore()
//   userStore.setLoginInfo(response.data.data)
  router.push({ name: 'home'})
  })
  .catch(function (error) {
    Swal.fire({
        title: "로그인 실패",
        text: "다시 입력해주세요 ",
        icon: "error"
      })
  })
}}

//카카오로그인 인가코드 받기-카카오로그인 페이지로 리다이렉트
const kakaoLogin = () => {
    window.location.href = `${import.meta.env.VITE_KAKAO_URL}?client_id=${import.meta.env.VITE_KAKAO_ID}&redirect_uri=${import.meta.env.VITE_KAKAO_REDIRECT}&response_type=code`
}



</script>

<style scoped>
@font-face {
font-family: 'gowun-dodum';
src: url('../../../../public/GowunDodum-Regular.ttf');
}


#logincontainer {
  width: 1200px;
  height: 550px;
  margin: 220px auto 50px;
  display: flex;
  flex-grow:1;
}

#sociallogincontainer {
  width: 1200px;
  margin: 0px auto ;
  display: flex;
  flex-grow:1;
}


</style>