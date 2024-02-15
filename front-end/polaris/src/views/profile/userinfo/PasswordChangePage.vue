<template>
  <Navvar></Navvar>
  <div class="container mt-60 font-[gowun-dodum]">
    <div class="flex justify-center">
      <div class="max-w-md w-full m-8 px-4 bg-white rounded-2xl shadow border border-gray-300">
        <div class="py-4 px-4 rounded-t-2xl mb-4 border-b-2">
          <h1 class="text-xl font-bold">비밀번호 변경</h1>
        </div>
        <div class="text-center text-lg">안전한 비밀번호로 내 정보를 보호하세요.</div>
        <div class="mt-8">
          <!-- 비밀번호 변경 제출하면 로그아웃 시키고 로그인 화면으로 push -->
          <div autoComplete="off">
            <div class="flex flex-col mb-8 mx-4">
              <p>현재 비밀번호</p>
              <div class="flex relative">
                <input
                  @click="resetRequest"
                  type="password"
                  v-model="currentpassword"
                  id="current-password"
                  class="rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent"
                />
              </div>
              <div v-if="!currentpassword && requestChange" class="text-red-500 text-sm p-1">
                비밀번호를 입력하세요.
              </div>
            </div>
            <div class="flex flex-col mb-8 mx-4">
              <p>새 비밀번호</p>
              <div class="flex relative">
                <input
                  @click="resetRequest"
                  type="password"
                  v-model="newpassword"
                  id="new-password"
                  placeholder="8~20자, 숫자, 영어, 특수문자 중 최소 1개씩 포함"
                  class="rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-500 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent"
                />
              </div>
              <div class="text-red-500 text-sm mt-1">
                {{ alertMessage }}
              </div>
              <div
                v-if="newpassword && currentpassword == newpassword"
                class="text-red-500 text-sm p-1"
              >
                현재 비밀번호와 다르게 설정해야 합니다.
              </div>
              <div v-if="!newpassword && requestChange" class="text-red-500 text-sm p-1">
                새 비밀번호를 입력하세요.
              </div>
            </div>
            <div class="flex flex-col mb-8 mx-4">
              <p>새 비밀번호 확인</p>
              <div class="flex relative">
                <input
                  @click="resetRequest"
                  type="password"
                  v-model="newpassword2"
                  id="new-password-2"
                  class="rounded-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-500 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent"
                />
              </div>
              <div v-if="!newpassword2 && requestChange" class="text-red-500 text-sm p-1">
                새 비밀번호 확인을 입력하세요.
              </div>
              <div v-if="!isSame && requestChange" class="text-red-500 text-sm p-1">
                새 비밀번호와 일치하지 않습니다.
              </div>
            </div>
            <div class="flex items-center my-4">
              <button @click="changePassword" id="approveChange" class="w-64">비밀번호 변경</button>
              <button
                @click="cancelChangePassword"
                id="cancelChange"
                type="submit"
                class="py-2 px-3 text-white w-64 transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2 rounded-lg"
              >
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
import Navvar from '@/components/common/Navvar.vue'
import { useRouter } from 'vue-router'
import { onMounted, ref, watch } from 'vue'
import axiosInstance from '@/services/axios'
import { profileCounterStore } from '@/stores/profilecounter'
import Swal from 'sweetalert2'

const store = profileCounterStore()
const BACK_API_URL = store.BACK_API_URL
const router = useRouter()
const currentpassword = ref<string>('')
const newpassword = ref<string>('')
const newpassword2 = ref<string>('')
const isSame = ref(true)
const requestChange = ref(false)
const alertMessage = ref<string>('')

const cancelChangePassword = () => {
  router.push({ name: 'ProfileUpdatePage' })
}

const resetRequest = () => {
  requestChange.value = false
}

// 비밀번호 검사 함수
const checkNewpassword = (password: string) => {
  // 비밀번호 검사 로직
  if (password.length < 8 || password.length > 20) {
    alertMessage.value = '비밀번호는 8자에서 최대 20자까지 가능합니다.'
    return
  }

  const hasNumber = /\d/.test(password)
  const hasLetter = /[a-zA-Z]/.test(password)
  const hasSpecialChar = /[~!@#$%^&*_\-+=`|\\(){}[\]:;"'<>,.?/]/.test(password)

  if (!hasNumber || !hasLetter || !hasSpecialChar) {
    alertMessage.value =
      '비밀번호는 숫자, 영어, 특수문자(~!@#$%^&*_-=+`|\\(){}[]:;"\'<>,.?/) 중 최소 1개씩을 포함해야 합니다.'
  } else {
    alertMessage.value = ''
  }
}

// watch 설정
watch(newpassword, (newValue) => {
  checkNewpassword(newValue)
})

// 비밀번호 변경 요청
const changePassword = () => {
  if (newpassword.value && newpassword2.value && currentpassword.value) {
    if (currentpassword.value == newpassword.value) {
      Swal.fire({
        title: '새 비밀번호는 현재 비밀번호와 달라야 합니다.',
        icon: 'error'
      })
    } else if (newpassword.value != newpassword2.value) {
      isSame.value = false
    } else {
      // 비밀번호 변경조건 모두 충족
      axiosInstance
        .value({
          headers: {
            Authorization: `${store.token}`,
            'Content-Type': 'application/json'
          },
          method: 'patch',
          url: `${BACK_API_URL}/user/change_password`,
          data: {
            oldPassword: currentpassword.value,
            newPassword: newpassword.value
          }
        })
        .then((response) => {
          // console.log(response.data)
          Swal.fire({
            title: '비밀번호가 변경되었습니다.\n로그인을 다시 진행해주세요.',
            icon: 'success'
          })
          localStorage.removeItem('user_token')
          localStorage.removeItem('refresh_token')
          localStorage.removeItem('user_info')
          router.push({ name: 'login' })
        })
        .catch((error) => {
          console.error(error)
          Swal.fire({
            title: '현재 비밀번호가 올바르지 않습니다.\n다시 입력해주세요.',
            icon: 'error'
          })
        })
    }
  } else {
    Swal.fire({
      title: '입력을 완료한 후 제출해주세요.',
      icon: 'error'
    })
  }
  requestChange.value = true
}

onMounted(() => {
  currentpassword.value = ''
  newpassword.value = ''
  newpassword2.value = ''
})
</script>

<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../../public/GowunDodum-Regular.ttf');
}
form {
  padding: 5px;
}

input {
  @apply bg-stone-50;
}

p {
  @apply py-2 font-semibold;
}

#checkPassword,
#approveChange {
  background-color: #323f59;
  color: #ffffff;
  margin: 5px;
  @apply py-2 px-4 hover:bg-gray-500 text-white transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg;
}

#cancelChange {
  @apply border border-gray-300 text-maintheme1 m-2 hover:bg-slate-200;
}

#new-password::placeholder {
  @apply text-sm;
}
</style>