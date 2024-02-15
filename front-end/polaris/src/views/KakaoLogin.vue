<template>

<div id="kakaologincontainer" class="flex flex-col w-full max-w-md px-4 py-8 bg-white rounded-lg shadow sm:px-6 md:px-8 lg:px-10 border-2" >
    <div class="self-center mt-4 mb-4 text-xl font-bold text-maintheme1 sm:text-2xl">
        사용자 정보 입력
    </div>

    <div class="mt-2">
        <form action="#" autoComplete="off">
          <div class="w-full mt-6 mr-0 mb-0 ml-0 relative space-y-6">
            <!-- 닉네임 입력받기 -->
            <div  class="relative flex mb-2">
              <p class="bg-white pt-0 pr-2 pb-0 pl-2 -mt-3 mr-0 mb-0 ml-2 font-medium text-gray-600 absolute">닉네임</p>
              <div class="flex items-center space-x-2 w-full"><input v-model="nicknameInput"   placeholder="nickname" type="text" class="text-thin border placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent w-full pt-4 pr-4 pb-4 pl-4 mt-2 mr-2 mb-0 ml-0 text-base block bg-white border-gray-300 rounded-md"/>
              <button @click="checkNickname" id="nicknamecheck" type="button" class="py-2 px-4 bg-white border border-gray-300 hover:border-2 hover:border-maintheme1 hover:text-maintheme1 text-gray-600 transition ease-in duration-200 text-center text-base font-medium  rounded-lg " >
                  확인
              </button></div></div> 
              <!-- 닉네임 중복검증 성공시 보임 -->
              <p v-if="isNicknameVerified" class="text-blue-600 text-sm " :style="{ marginLeft: '15px', marginTop: '0' }">사용 가능한 닉네임 입니다.</p>
            
         
            <!-- 이메일 입력받기 -->
            <div class="relative flex">
              <p class="bg-white pt-0 pr-2 pb-0 pl-2 -mt-3 mr-0 mb-0 ml-2 font-medium text-gray-600 absolute">이메일</p>
              <div class="flex items-center space-x-2 w-full"><input :readonly="isEmailVerified" v-model="emailInput" placeholder="polaris@gmail.com" type="text" class="border placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent w-full pt-4 pr-4 pb-4 pl-4 mt-2 mr-0 mb-2 ml-0 text-base block bg-white border-gray-300 rounded-md"/>
              <button @click.prevent="checkEmail" :disabled="!isValidEmail" id="emailcheck" type="button" class="py-2 px-4 bg-white border border-gray-300 hover:border-2 hover:border-maintheme1 hover:text-maintheme1 text-gray-600 transition ease-in duration-200 text-center text-base font-medium  rounded-lg " >
                  인증
              </button></div> 
            </div>
            <p v-show="!isValidEmail && emailInput" class="warning-text text-red-600 text-sm " :style="{ marginLeft: '15px', marginTop: '0' }">올바르지 않은 이메일 주소입니다.</p>  

            <!-- 인증 버튼 누르면 인증창 등장 -->
            <div v-if="isEmailVerificationInput" class="relative flex">
              <div class="flex items-center space-x-2 w-full">
                <input class="border rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" v-model="verificationCode" type="text" placeholder="인증 코드를 입력하세요" />
                <button class=" border text-gray-600 hover:border-2 hover:border-maintheme1 px-4 py-2 rounded-md" @click.prevent="checkVerifyCode">확인</button>
              </div>
            </div>


            <!-- 이메일 인증 성공시 보임 -->
            <div v-if="isEmailVerified" class="text-blue-600 text-sm" :style="{ marginLeft: '15px', marginTop: '0' }">
              인증되었습니다.
            </div>

          </div>
          
          <!-- 위치 정보 입력 받기 -->
          <div class="relative flex mt-8">
              <p class="bg-white pt-0 pr-2 pb-0 pl-2 -mt-3 mr-0 mb-0 ml-2 font-medium text-gray-600 absolute">위치 정보</p>
              <div class="flex items-center space-x-2 w-full"><p  class="border h-14 text-black-600 w-full pt-4 pr-4 pb-4 pl-4 mt-2 mr-0 mb-2 ml-0 text-base block bg-white border-gray-300 rounded-md  ">{{ regionInputName }}</p>
              <button @click="openRegionModal" id="regionsearch" type="button" class="py-2 px-4 bg-white border border-gray-300 hover:border-2 hover:border-maintheme1 hover:text-maintheme1 text-gray-600 transition ease-in duration-200 text-center text-base font-medium  rounded-lg " >
                  위치 찾기
              </button>
            </div>     
          </div>
          <!-- 지역 선택 모달 -->
          <RegionModal v-if="isRegionModalOpen" @close="closeRegionModal" @confirm="updateRegion"/> 
                   
            <div  class="mt-10 flex w-full">
                <button @click.prevent="submitKakaoLogin" type="submit" class="h-14 py-3 px-4  mt-4 bg-maintheme1  text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md  rounded-lg ">
                    제출하기
                </button>
            </div>


        </form>

       
                
    </div>
  </div>

</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { onMounted } from 'vue'
import axios from 'axios'
import router from '@/router'
import { useUserStore } from '@/stores/authcounter'
import RegionModal from '@/components/Auth/RegionModal.vue'
import Swal from 'sweetalert2'

//인가코드 빼오기
const code = new URL(window.location.href).searchParams.get('code');

const kakaoId = ref()
//인가코드 전송
const fetchToken = async () => {
  try {
    Swal.fire({
        title: "카카오 로그인 중",
        text: "잠시만 기다려 주세요 ",
        icon: "info"
      })
    await axios.post(`${import.meta.env.VITE_API_KEY}/user/login/oauth2/code/kakao`, null, {
    params: {
        code: code
    }})
    .then(function(response) {
      // 로그인 한 유저일 시
      if (response.data.status === 200) {
        // console.log(response)
        localStorage.setItem('user_token',(response.data.data.access))
        localStorage.setItem('refresh_token',(response.data.data.refresh))
        localStorage.setItem('user_info' , JSON.stringify(response.data.data))
        // console.log(localStorage.getItem('user_token'))
        // console.log(localStorage.getItem('refresh_token'))
        // console.log(localStorage.getItem('user_info'))
        // const userStore = useUserStore()
        // userStore.setLoginInfo(response.data.data)
        Swal.fire({
        title: "카카오 로그인 성공!",
        icon: "success"
      })
        router.push({ name: 'home'}) //메인페이지로 이동
      } else { // 처음 가입한 유저 일 시
        Swal.fire({
        title: "처음 로그인 하셨나요?",
        text: " 사용자 정보를 입력해주세요 ",
        icon: "info"
      })
        kakaoId.value = response.data.data.kakaoProfile.id
        submitKakaoLogin()

      }
    })
  } catch (error) {
    console.error(error)
    alert(error)
  }
};

onMounted(() => {
  fetchToken();
})



const nicknameInput = ref('')
const isNicknameVerified = ref(false)


const emailInput = ref('')

const handleValidEmailForm = (emailAddress: string) => {
  return /^[^@]+@\w+(\.\w+)+\w$/.test(emailAddress)
}
const isValidEmail = ref(handleValidEmailForm(emailInput.value))
const isEmailVerificationInput = ref(false)
const verificationCode = ref('')
const isEmailVerified = ref(false)

watch(() => emailInput.value,  (current) => {
    isValidEmail.value = handleValidEmailForm(current)
})


const regionInputName = ref('')
console.log(regionInputName.value)
const regionInputCode = ref('')
const isRegionModalOpen = ref(false)

const openRegionModal = () => {
  isRegionModalOpen.value = true
}

const closeRegionModal = () => {
  isRegionModalOpen.value = false
}

const updateRegion = (newRegion: {name: string, code: string}) => {
  regionInputName.value = newRegion.name
  regionInputCode.value = newRegion.code
  // console.log("메인페이지:", regionInputName.value)
  closeRegionModal()
}


//닉네임 중복검사
// 입력한 닉네임 서버로 전송
// 중복 검사 성공시 isNicknameVerified = true로 바꾸기
const checkNickname = async () => {
  await axios.get(`${import.meta.env.VITE_API_KEY}/user/nickname_check/${nicknameInput.value}`, {
    headers: {
    "Content-Type": "application/json",
  }
  })
  .then(function (response) {
    if (response.data.data.isInUse) {
      isNicknameVerified.value = false
      Swal.fire({
        text: "이미 존재하는 닉네임 입니다 ",
        icon: "error"
      })
      // console.log(isNicknameVerified.value)
	    // console.log(response.data.message)
    } else {
      Swal.fire({
        text: "사용 가능한 닉네임 입니다 ",
        icon: "success"
      })
      isNicknameVerified.value = true
      // console.log(isNicknameVerified.value)
      // console.log(response.data.message)
    }

})
.catch(function (error) {
	alert(error.message)
})
}


//이메일 전송 
 // 입력한 이메일을 서버로 전송
  // 성공시 isEmailVerificationInput.value =true로 바꾸기
const sendEmail = async () => {
  await axios.post(`${import.meta.env.VITE_API_KEY}/send_mail/email`, 
  JSON.stringify({
      email: emailInput.value
    }),
    {headers: {
      "Content-Type": "application/json",
    }}
)
.then(function (response) {
  Swal.fire({
        text: "이메일로 인증코드가 발송되었습니다 ",
        icon: "success"
      })
  isEmailVerificationInput.value = true
	// console.log(response.status)
})
.catch(function () {
  Swal.fire({
        text: "이메일 전송에 실패하였습니다",
        icon: "error"
      })
})
}


//이메일 중복 체크
//존재하지 않는 이메일일 경우 sendEmail 함수 실행(이메일 전송)
const checkEmail = async () => {
  await axios.get(`${import.meta.env.VITE_API_KEY}/user/email_check/${emailInput.value}`, {
    headers: {
    "Content-Type": "application/json",
  }
  })
  .then(function (response) {
    if (response.data.data.isInUse) {
      Swal.fire({
        text: "이미 존재하는 이메일 입니다 ",
        icon: "error"
      })
      // console.log('이메일 인증 여부 검증 실패')
    } else {
      Swal.fire({
        text: "가입 가능한 이메일 입니다 ",
        icon: "success"
      })
      // console.log('이메일 인증 여부 검증  성공')
      sendEmail()
    }
  })
  .catch(function (error) {
    alert(error.message)
  })
  }


//이메일 인증코드 전송
// 입력받은 인증코드 서버로 전송
// 인증 성공시 isEmailVerificationInput.value =false 로 바꾸기,  isEmailVerified = true로 바꾸기
const checkVerifyCode = async () => {
  await axios.post(`${import.meta.env.VITE_API_KEY}/user/email_cert`, 
    JSON.stringify({
      email: emailInput.value,
      code: verificationCode.value
    }),
    {headers: {
      "Content-Type": "application/json",
    }}
)
.then(function (response) {
  Swal.fire({
        title: "인증 성공!",
        icon: "success"
      })
  isEmailVerificationInput.value =false
  isEmailVerified.value = true
	// console.log(response.status)
})
.catch(function (error) {
	alert(error.message)
})
}


const submitKakaoLogin = async () => {
  if (isNicknameVerified.value && isEmailVerified.value && regionInputCode.value) {
  await axios.post(`${import.meta.env.VITE_API_KEY}/user/join/oauth2/code/kakao`, 
  JSON.stringify({
    nickname : nicknameInput.value,
    region : regionInputCode.value,
    kakaoId: kakaoId.value,
    email: emailInput.value,
    
    }),
    {headers: {
      "Content-Type": "application/json",
    }}
)
.then(function (response) {
  localStorage.setItem('user_token',(response.data.data.access))
  localStorage.setItem('refresh_token',(response.data.data.refresh))
  localStorage.setItem('user_info' , JSON.stringify(response.data.data))
  // const userStore = useUserStore()
  // userStore.setLoginInfo(response.data.data)
  Swal.fire({
        title: "카카오 로그인 성공!",
        icon: "success"
      })
  router.push({ name: 'home'}) //메인페이지로 이동
  })
  .catch(function (error) {
    alert(error.message)
  }) 
} else {
  Swal.fire({
        text: " 정보를 모두 입력해주세요 ",
        icon: "error"
      })
}}

</script>

<style scoped>
#kakaologincontainer {
  width: 1200px;
  height: 600px;
  margin: 200px auto 50px;
  display: flex;
  flex-grow:1;
}
</style>