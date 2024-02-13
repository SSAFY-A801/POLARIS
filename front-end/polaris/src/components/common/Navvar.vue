<template>
    <nav class="fixed bg-white top-20 w-full z-30 shadow-lg font-[gowun-dodum]">
        <div class="px-12 mx-0 ">
            <div class="flex items-center h-20">
              <div class=" flex items-center justif-center w-full h-20">
                 <!-- <div class="flex m-auto flex-row"></div> -->
                 
                 <div class="flex items-center w-1/3 ml-10 justify-center space-x-2 sm:space-x-4 md:space-x-6 lg:space-x-8">
                     <router-link :to="{name: 'booksearch'}" class="w-80 text-resize px-20 text-maintheme1 text-xl font-bold text-center mr-2">도서 검색</router-link>
                     <router-link :to="{name: 'essaylist'}" class="w-40 text-resize text-maintheme1 text-xl font-bold text-center" >독후감 게시판</router-link>
                 </div>
                

                 <div class="flex ml-auto flex-row px-20">
                     <router-link :to="{name: 'login'}" v-if="!userToken" class="text-maintheme1 mr-14 font-bold"><font-awesome-icon icon="fa-solid fa-arrow-right-to-bracket" size="xl" style="color: #323F59;"  class="mr-2" />로그인</router-link>
                     <router-link :to="{name: 'signup'}" v-if="!userToken"  class="text-maintheme1 font-bold"><font-awesome-icon :icon="['fas', 'user-plus']" size="xl" style="color: #323F59;"  class="mr-2" />회원가입</router-link>
                     <p  v-if="userToken && loginUser.id !== undefined && loginUser.id !== null" class="text-maintheme1 font-bold mt-4 mr-4" >{{ userNickname }}</p>
                     <router-link v-if="userToken && loginUser.id !== null" 
                                 :to="{ name: 'ProfilePage', params: { id: loginUser.id }}" 
                                 class="text-maintheme1 mr-14"><img class="col-span-1 object-cover" id="profile-image" :src="userProfileUrl || '@/assets/profile-default.jpg'" alt="Profile-Image"></router-link>
                     <button v-if="userToken" @click="logout"  class="text-maintheme1 font-bold  bg-transparent border-none outline-none focus:outline-none cursor-pointer"><font-awesome-icon icon="fa-solid fa-arrow-right-from-bracket" size="xl" style="color: #323F59;" class="ml-4 mr-2"/>로그아웃</button>
                 </div>
             </div>

            </div>
        </div>
    </nav>
</template>


<script setup lang="ts">
import { onBeforeMount, ref, watchEffect, computed } from 'vue'
import axios, { AxiosError } from 'axios'
import { useRouter } from 'vue-router'
import { profileCounterStore } from '@/stores/profilecounter'
import axiosInstance from '@/services/axios'


// const props = defineProps(['userToken'])
const router = useRouter();
const userToken = ref(localStorage.getItem('user_token'))
const refreshToken = ref(localStorage.getItem('refresh_token'))
const userInfoString = ref<string>(localStorage.getItem('user_info') ?? "");
// 사용자 정보를 나타내는 인터페이스 정의
interface UserInfo {
  id?: string | null;
  email?: string | null;
  // 다른 속성들도 필요에 따라 추가할 수 있습니다.
}

// userInfoString에서 가져온 값을 파싱하여 UserInfo 타입으로 사용
let loginUser: UserInfo;

try {
  loginUser = JSON.parse(userInfoString.value) || {}; // 빈 객체로 기본값 설정
} catch (error) {
  // console.error("Error parsing user_info:", error);
  loginUser = {}; // JSON 파싱에 실패한 경우 빈 객체로 기본값 설정
}
// loginUser를 사용할 때 loginUser.id를 체크할 때 에러가 발생하지 않습니다.


const userNickname = ref<string | null>(null)
const userProfileUrl = ref<string | null>(null)

const getUserInfo = async() => {
  try {
    const response = await axios({
      headers: {
        "Authorization": `${userToken.value}`,
        "Content-Type": 'application/json'
      },
      method: 'get',
      url: `https://i10a801.p.ssafy.io:8082/profile/${loginUser.id}`,
    })
    userNickname.value = response.data.data.nickname
    userProfileUrl.value = response.data.data.profileUrl
  } catch (error) {
    console.error("에러발생: ", error)
  }
}

onBeforeMount(() => {
  getUserInfo()
})




watchEffect(() => {
    userToken.value = localStorage.getItem('user_token')
    // console.log(localStorage.getItem('user_token'))
    // console.log("watchEffect is running") 
    // console.log("watchEffect",userToken.value)

})



// 로그아웃
const logout = async () => {
    await axios.get('https://i10a801.p.ssafy.io:8082/user/logout', {
    headers: {
    "Authorization" : userToken.value,
    "Content-Type": "application/json",
  }
  })
  .then(function (response) {
    localStorage.removeItem('user_token')
    localStorage.removeItem('refresh_token')
    localStorage.removeItem('user_info')
    localStorage.removeItem('counter')
    userToken.value = null
    alert("로그아웃 되었습니다")
    console.log(response.status)
    router.push({name: 'home'})
  }).catch(async function (error) {
    if (error.response.data.status === 420) {
      await axios.post('https://i10a801.p.ssafy.io:8082/user/reissue', {"email": loginUser.email}, {
              headers: {
                "Authorization": refreshToken.value,
                "Content-Type": "application/json"
              }
                
            }).then(async function(response) {
              const newToken = response.data.data.access // 새로 발급받은 토큰
              await axios.get('https://i10a801.p.ssafy.io:8082/user/logout', {
                headers: {
                    "Authorization" : newToken,
                    "Content-Type": "application/json",
                }})
                .then(function(response){
                  localStorage.removeItem('user_token')
                  localStorage.removeItem('refresh_token')
                  localStorage.removeItem('user_info')
                  localStorage.removeItem('counter')
                  userToken.value = null
                  alert("로그아웃 되었습니다")
                  console.log(response.status)
                  router.push({name: 'home'})
                })

            })
        } else {
            alert(error)

    }
  }) 


}


</script>

<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../public/GowunDodum-Regular.ttf');
}

a {
  text-decoration: none;
}

#logo {
    height: 80px;
}

#profile-image {
    @apply w-[55px] h-[55px] object-cover justify-items-center rounded-[70%] border-[3px] border-solid border-[#121212];
}
</style>
