<template>
  <div class="container">
    <div class="flex justify-center">
      <div class="max-w-md w-full m-8 px-4 py-4 bg-white rounded-lg shadow sm:px-6 md:px-8 lg:px-10 border border-gray-300">
        <div>
          <div class="flex ml-auto justify-between mb-4">
            <h1 class="text-2xl">비밀번호 변경</h1>
            <div>
              <button @click="cancelChangePassword" type="submit" class="py-1.5 px-3 hover:bg-gray-500 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg ">
              취소
            </button>
            </div>
          </div>
          <div class="text-center">
            안전한 비밀번호로 내 정보를 보호하세요.
          </div>
        </div>
        <div class="mt-8">
            <!-- 비밀번호 변경 제출하면 로그아웃 시키고 로그인 화면으로 push -->
          <form @submit.prevent action="#" autoComplete="off">
              <div class="flex flex-col mb-8 mx-4">
                <p>현재 비밀번호</p>
                <div class="flex relative ">
                  <input id="current-password" class=" rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent"/>
                </div>
              </div>
              <div class="flex flex-col mb-8 mx-4 ">
                <p>새 비밀번호</p>
                <div class="flex relative ">
                  <input id="new-password" placeholder="새 비밀번호 조건" class=" rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" />
                </div>
              </div>
              <div class="flex flex-col mb-8 mx-4">
                <p>새 비밀번호 확인</p>
                <div class="flex relative">
                  <input id="new-password-2" placeholder="새 비밀번호 조건"  class=" rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" />
                </div>
              </div>
              <div class="flex items-center my-6">
                  <div class="flex ml-auto">
                    <button type="submit" class="py-2 px-4 hover:bg-gray-500 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg ">
                    비밀번호 변경
                </button>
                  </div>
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

 button {
    background-color: #323F59;
    color: #ffffff;
    margin: 5px;
  }

</style>