<template>
  <div class="container">
    <div class="flex justify-center">
      <div class="max-w-md w-full m-8 px-4  bg-white rounded-2xl shadow  border border-gray-300">
        <div class="py-4 px-4 rounded-t-2xl mb-4 border-b-2">
          <h1 class="text-xl font-bold">비밀번호 변경</h1>
        </div>
        <div class="text-center">
          안전한 비밀번호로 내 정보를 보호하세요.
        </div>
        <div class="mt-8">
          <!-- 비밀번호 변경 제출하면 로그아웃 시키고 로그인 화면으로 push -->
          <form @submit.prevent action="#" autoComplete="off">
            <div class="flex flex-col mb-8 mx-4">
              <p>현재 비밀번호</p>
              <div class="flex relative ">
                <input type="password" id="current-password" class=" rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent"/>
              </div>
            </div>
            <div class="flex flex-col mb-8 mx-4 ">
              <p>새 비밀번호</p>
              <div class="flex relative ">
                <input type="password" id="new-password" placeholder="새 비밀번호 조건" class=" rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-500 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" />
              </div>
            </div>
            <div class="flex flex-col mb-8 mx-4">
              <p>새 비밀번호 확인</p>
              <div class="flex relative">
                <input type="password" id="new-password-2" placeholder="새 비밀번호 조건"  class=" rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-500 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" />
              </div>
            </div>
            <div class="flex items-center my-4">
              <button type="submit" id="approveChange" class="py-2 px-4 hover:bg-gray-500 text-white w-64 transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg ">
                비밀번호 변경
              </button>
                <button @click="cancelChangePassword" id="cancelChange" type="submit" class="py-2 px-3  text-white w-64 transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg ">
                취 소
                </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import {useRouter} from 'vue-router'
  import { ref } from 'vue';

  const router = useRouter()
  
  
  function cancelChangePassword() {
    router.push({name: "ProfileUpdatePage"});
  }
  

</script>

<style scoped>
 form {
  padding: 5px;

 }

 input {
  @apply bg-stone-50
 }

 p {
  @apply py-2 font-semibold
 }

 #approveChange {
    background-color: #323F59;
    color: #ffffff;
    margin: 5px;
  }

 #cancelChange {
  @apply border border-gray-300 text-maintheme1 m-2 hover:bg-slate-200
  }

</style>