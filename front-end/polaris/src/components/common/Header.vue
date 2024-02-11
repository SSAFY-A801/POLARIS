<template>
    <nav class="bg-white fixed top-0 w-full z-50">
        <div class="px-12 mx-0 flex justify-center"> 
            <div class="flex items-center space-x-2 sm:space-x-4 md:space-x-6 lg:space-x-8">
                <router-link :to="{name: 'home'}" class="text-white">
                    <img id="logo" src="@\assets\polarisLogo.png" alt="">
                </router-link>
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
    "Content-Type": "application/json"
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