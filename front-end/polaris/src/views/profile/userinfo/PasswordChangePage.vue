<template>
  <div class="container mt-28">
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
          <div autoComplete="off">
            <div class="flex flex-col mb-8 mx-4">
              <p>현재 비밀번호</p>
              <div class="flex relative ">
                <input @click="resetRequest" type="password" v-model="currentpassword" id="current-password" class=" rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent"/>
              </div>
              <div v-if="!currentpassword && requestChange"  class="text-red-500 text-sm p-1">
                비밀번호를 입력하세요.
              </div>
            </div>
            <div class="flex flex-col mb-8 mx-4 ">
              <p>새 비밀번호</p>
              <div class="flex relative ">
                <input @click="resetRequest" type="password" v-model="newpassword" id="new-password" placeholder="새 비밀번호 조건" class=" rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-500 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" />
              </div>
              <div class="text-red-500 text-sm mt-1">
                {{ alertMessage }}
              </div>
                <div v-if="newpassword && currentpassword == newpassword" class="text-red-500 text-sm p-1">
                  현재 비밀번호와 다르게 설정해야 합니다.
                </div>
                <div v-if="!newpassword && requestChange"  class="text-red-500 text-sm p-1">
                  새 비밀번호를 입력하세요.
                </div>
            </div>
            <div class="flex flex-col mb-8 mx-4">
              <p>새 비밀번호 확인</p>
              <div class="flex relative">
                <input @click="resetRequest" type="password" v-model="newpassword2" id="new-password-2" placeholder="새 비밀번호 조건"  class=" rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-500 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" />
              </div>
              <div v-if="!newpassword2 && requestChange"  class="text-red-500 text-sm p-1">
                새 비밀번호 확인을 입력하세요.
              </div>
              <div v-if="!isSame && requestChange" class="text-red-500 text-sm p-1">
                새 비밀번호와 일치하지 않습니다.
              </div>
            </div>
            <div class="flex items-center my-4">
              <button @click="changePassword"  id="approveChange" class="py-2 px-4 hover:bg-gray-500 text-white w-64 transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg ">
                비밀번호 변경
              </button>
                <button @click="cancelChangePassword" id="cancelChange" type="submit" class="py-2 px-3  text-white w-64 transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg ">
                취 소
                </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { useRouter } from 'vue-router'
  import { onMounted, ref, watch } from 'vue';

  const router = useRouter()
  const currentpassword = ref<string>('')
  const newpassword = ref<string>('')
  const newpassword2 = ref<string>('')
  const isSame = ref(true)
  const requestChange = ref(false)
  const alertMessage = ref<string>("")
  const cancelChangePassword = () => {
    router.push({name: "ProfileUpdatePage"});
  }
  
  const resetRequest = () => {
    requestChange.value = false
  }


// 비밀번호 검사 함수
const checkNewpassword = (password:string) => {
  // 비밀번호 검사 로직
  if (password.length < 8 || password.length > 20) {
    alertMessage.value = "비밀번호는 8자에서 최대 20자까지 가능합니다.";
    return;
  }

  const hasNumber = /\d/.test(password);
  const hasLetter = /[a-zA-Z]/.test(password);
  const hasSpecialChar = /[~!@#$%^&*_\-+=`|\\(){}[\]:;"'<>,.?/]/.test(password);

  if (!hasNumber || !hasLetter || !hasSpecialChar) {
    alertMessage.value = "비밀번호는 숫자, 영어, 특수문자(~!@#$%^&*_-=+`|\\(){}[]:;\"'<>,.?/) 중 최소 1개씩을 포함해야 합니다.";
  } else {
    alertMessage.value = "";
  }
};

// watch 설정
watch(newpassword, (newValue) => {
  checkNewpassword(newValue);
});


const changePassword = () => {
  console.log(requestChange.value)
  if(newpassword.value && newpassword2.value && currentpassword.value){
    // 1. 현재 비밀번호가 일치하지 않은경우
    // if(currentpassword != 기존비밀번호 ){
    //   alert("현재 비밀번호가 올바르지 않습니다.")
    // }
    if (currentpassword.value == newpassword.value){
      alert("새 비밀번호는 현재 비밀번호와 달라야 합니다.")
    }
    if(newpassword.value != newpassword2.value){
      isSame.value = false
    } else {
      alert("비밀번호 변경 성공!")
    }
  } else {
    alert('입력을 완료한 후 제출해주세요.')
  }
  requestChange.value = true
  console.log('현재 비밀번호: ',currentpassword.value)
  console.log('새 비밀번호: ',newpassword.value)
  console.log('새 비밀번호 확인: ',newpassword2.value)
}

onMounted(()=> {
  currentpassword.value = ''
  newpassword.value = ''
  newpassword2.value = ''

})

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