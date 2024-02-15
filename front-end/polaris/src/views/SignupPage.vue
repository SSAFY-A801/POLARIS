<template>
  <Navvar></Navvar>
  <div id="signupcontainer" class="flex flex-col w-full max-w-md px-4 py-8 bg-white rounded-lg shadow sm:px-6 md:px-8 lg:px-10 border-2 font-[gowun-dodum]" >
    <div class="self-center mt-4 mb-4 text-xl font-bold text-maintheme1 sm:text-2xl">
        회원가입
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
            
            <!-- 비밀번호 입력받기 -->
            <div class="relative ">
              <p class="bg-white pt-0 pr-2 pb-0 pl-2 -mt-3 mr-0 mb-0 ml-2 font-medium text-gray-600 absolute">비밀번호</p>
              <input v-model="passwordInput" placeholder="password" type="password" class="border placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent w-full pt-4 pr-4 pb-4 pl-4 mt-2 mr-0 mb-0 ml-0 text-base block bg-white border-gray-300 rounded-md"/>
            </div>
            <div class="relative">
              <p class="bg-white pt-0 pr-2 pb-0 pl-2 -mt-3 mr-0 mb-0 ml-2 font-medium text-gray-600 absolute">비밀번호 확인</p>
              <input v-model="passwordCheck" @input="checkPassword" placeholder="password" type="password" class="border placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent w-full pt-4 pr-4 pb-4 pl-4 mt-2 mr-0 mb-4 ml-0 text-base block bg-white border-gray-300 rounded-md"/>
            </div>
            <p v-if="isPasswordVerified" class="text-red-600 text-sm" :style="{ marginLeft: '15px', marginTop: '0' }">비밀번호가 일치하지 않습니다.</p>
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
                   
            <div  class="flex w-full">
                <button @click.prevent="submitUserSignup" type="submit" class="h-14 py-3 px-4  mt-4 bg-maintheme1  text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md  rounded-lg ">
                    회원가입
                </button>
            </div>

            
            <!-- <hr class="mt-6 mb-6 border-t border-gray-200">

            <div class="mt-4">
              <button type="submit" class="flex items-center justify-center mt-4 mb-0 py-3 px-4 bg-kakao_yellow text-kakao_brown w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md rounded-lg">
              <img src="@/assets/styles/kakaosymbol.png" alt="Kakao 로그인 아이콘" class="mr-2">
              카카오 회원가입
              </button>
            </div> -->

        </form>

       
                
    </div>
  </div>

  
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import RegionModal from '@/components/Auth/RegionModal.vue'
import axios from 'axios'
import Swal from 'sweetalert2'


const router = useRouter()

const nicknameInput = ref('')
const isNicknameVerified = ref(false)


const emailInput = ref('')
const isEmailVerifyCodeSend = ref(false)

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

const passwordInput = ref('')
const passwordCheck = ref('')
const isPasswordVerified = ref(false)


const regionInputName = ref('')
// console.log(regionInputName.value)
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
        text: "이미 사용중인 닉네임 입니다 ",
        icon: "error"
      });
    } else {
        Swal.fire({
          text: "사용가능한 닉네임 입니다 ",
          icon: "success"
        });
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
        });
  isEmailVerificationInput.value = true
	// console.log(response.status)
})
.catch(function () {
	Swal.fire({
        text: "인증코드 발송에 실패했습니다 ",
        icon: "error"
      });
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
      });
    } else {
      Swal.fire({
          text: "가입이 가능한 이메일 입니다 ",
          icon: "success"
        });
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
          title: "인증 성공! ",
          icon: "success"
        });
  isEmailVerificationInput.value =false
  isEmailVerified.value = true
	// console.log(response.status)
})
.catch(function (error) {
	Swal.fire({
          title: "인증 실패 ",
          icon: "error"
        });
})
}


//비밀번호 확인
//입력한 두 개의 비밀번호가 일치하는지 검증
const checkPassword = () => {
  if (passwordInput.value !== passwordCheck.value) {
    isPasswordVerified.value = true
  } else {
    isPasswordVerified.value = false
  }
}


//회원 입력 정보 제출
 //회원가입 정보 서버로 전송
  //회원가입 성공시 로그인 페이지로 이동
const submitUserSignup = async () => {
  if (isNicknameVerified.value && isEmailVerified.value && !isPasswordVerified.value && regionInputCode.value) {
  await axios.post(`${import.meta.env.VITE_API_KEY}/user`, 
  JSON.stringify({
    email: emailInput.value,
    password: passwordInput.value,
    region : regionInputCode.value,
		nickname : nicknameInput.value
    }),
    {headers: {
      "Content-Type": "application/json",
    }}
)
.then(function (response) {
  console.log(response.status)
  Swal.fire({
          title: "회원가입 성공! ",
          icon: "success"
        });
  router.push({name:'login'})

  // localStorage.setItem('user_token',JSON.stringify(response.accessToken))
  // localStorage.setItem('user_info' , JSON.stringify(response.userInfo))
  // const localStorageInfo = JSON.parse(localStorage.getItem('userInfo')) localstorage에서 가져올때
  })
  .catch(function (error) {
    Swal.fire({
        title: "회원가입 실패",
        icon: "error"
      });
  }) 
} else {
  Swal.fire({
        title: '회원가입 오류',
        text: "정보를 모두 입력해주세요 ",
        icon: "error"
      });
  // console.log('닉네임:', isNicknameVerified.value)
  // console.log('이메일:', isEmailVerified.value)
  // console.log('비밀번호:', isPasswordVerified.value)
  // console.log('지역:', regionInputCode.value)
}
}



</script>

<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../../public/GowunDodum-Regular.ttf');
}


#signupcontainer {
  width: 1200px;
  height: 750px;
  margin: 220px auto 50px;
  display: flex;
  flex-grow:1;
}

#nicknamecheck {
  width: 80px;
  height: 45px;
}

#emailcheck {
  width: 80px;
  height: 45px;
}

#regionsearch {
  width: 160px;
  height: 45px;
}


/* #socialsignupcontainer {
  width: 1200px;
  margin: 0px auto ;
  display: flex;
  flex-grow:1;
} */

</style>