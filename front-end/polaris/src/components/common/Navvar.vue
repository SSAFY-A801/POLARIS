<template>
    <nav class="fixed bg-white top-20 w-full z-30 shadow-lg">
        <div class="px-12 mx-0 ">
            <div class="flex items-center h-24">
              <div class=" flex items-center justify-center w-full h-24">
                 
                 <div class="flex items-center justify-center space-x-2 sm:space-x-4 md:space-x-6 lg:space-x-8">
                     <router-link :to="{name: 'booksearch'}" class="text-maintheme1 text-2xl font-bold text-center mr-10">도서 검색</router-link>
                     <router-link :to="{name: 'essaylist'}" class="ml-10 text-maintheme1 text-2xl font-bold text-center" >독후감 게시판</router-link>
                 </div>
                

                 <div class="flex ml-auto flex-row">
                     <router-link :to="{name: 'login'}" v-if="!userToken" class="text-maintheme1 mr-5"><font-awesome-icon icon="fa-solid fa-arrow-right-to-bracket" class="mr-2" />로그인</router-link>
                     <router-link :to="{name: 'signup'}" v-if="!userToken"  class="text-maintheme1"><font-awesome-icon :icon="['fas', 'user-plus']" class="mr-2" />회원가입</router-link>
                     <router-link v-if="userToken && loginUser.id !== undefined && loginUser.id !== null" 
                                 :to="{ name: 'ProfilePage', params: { id: loginUser.id }}" 
                                 class="text-maintheme1 mr-4">프로필</router-link>
                     <button v-if="userToken" @click="logout"  class="text-maintheme1 mx-4 bg-transparent border-none outline-none focus:outline-none cursor-pointer"><font-awesome-icon icon="fa-solid fa-arrow-right-from-bracket" size="2xl" style="color: #323F59;" class="ml-4 mr-2"/>로그아웃</button>
                 </div>
             </div>

            </div>
        </div>
    </nav>
</template>


<script setup lang="ts">
import { onMounted, ref, watchEffect, computed } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { profileCounterStore } from '@/stores/profilecounter'


// const props = defineProps(['userToken'])
const router = useRouter();
const userToken = ref(localStorage.getItem('user_token'))
const userInfoString = ref<string>(localStorage.getItem('user_info') ?? "");
// 사용자 정보를 나타내는 인터페이스 정의
interface UserInfo {
  id?: string | null;
  // 다른 속성들도 필요에 따라 추가할 수 있습니다.
}

// userInfoString에서 가져온 값을 파싱하여 UserInfo 타입으로 사용
let loginUser: UserInfo;

try {
  loginUser = JSON.parse(userInfoString.value) || {}; // 빈 객체로 기본값 설정
} catch (error) {
  console.error("Error parsing user_info:", error);
  loginUser = {}; // JSON 파싱에 실패한 경우 빈 객체로 기본값 설정
}
// loginUser를 사용할 때 loginUser.id를 체크할 때 에러가 발생하지 않습니다.



watchEffect(() => {
    userToken.value = localStorage.getItem('user_token')
    // console.log(localStorage.getItem('user_token'))
    // console.log("watchEffect is running") 
    // console.log("watchEffect",userToken.value)

})



//로그아웃
const logout = async () => {
    await axios.get('https://i10a801.p.ssafy.io:8082/user/logout', {
    headers: {
    "Authorization" : userToken.value?.replace("\"", ""),
    "Content-Type": "application/json",
  }
  })
  .then(function (response) {
    localStorage.removeItem('user_token')
    userToken.value = null
    alert("로그아웃 되었습니다")
    console.log(response.status)
    router.push({name: 'home'})
  })

}

</script>

<style scoped>
a {
  text-decoration: none;
}

#logo {
    height: 80px;
}
</style>
