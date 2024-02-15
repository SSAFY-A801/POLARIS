<template>
  <Navvar></Navvar>

    <div id="passwordsearchcontainer" class="mt-40 flex flex-col w-full max-w-md px-4 py-8 bg-white rounded-lg shadow sm:px-6 md:px-8 lg:px-10 border-2">
        <div class="self-center mt-4 mb-14 text-xl font-bold text-maintheme1 sm:text-2xl">
            비밀번호 찾기
        </div>
        
        <div class="relative flex items-center space-x-2 self-center">
            <input v-model="passwordSearchEmail" class="border rounded-md px-3 py-3 focus:outline-none focus:ring-2 focus:ring-maintheme1 w-64" type="text" placeholder="이메일을 입력하세요" />
            <button class="border text-gray-600 hover:border-2 hover:border-maintheme1 px-4 py-2 rounded-md" @click.prevent="sendTemporaryPassword" :disabled="!isValidEmail">확인</button>
        </div>
        <p v-show="!isValidEmail && passwordSearchEmail" class="  warning-text text-red-600 text-sm " :style="{ marginLeft: '30px', marginTop: '10px' }">올바르지 않은 이메일 주소입니다.</p>  
    </div>

</template>



<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { ref, watch } from 'vue'
import axios from 'axios'

const passwordSearchEmail = ref('')
const handleValidEmailForm = (emailAddress: string) => {
  return /^[^@]+@\w+(\.\w+)+\w$/.test(emailAddress)
}
const isValidEmail = ref(handleValidEmailForm(passwordSearchEmail.value))

watch(() => passwordSearchEmail.value,  (current) => {
    isValidEmail.value = handleValidEmailForm(current)
})

const sendTemporaryPassword =async () => {
    await axios.post(`${import.meta.env.VITE_API_KEY}/send_mail/password`, {email: passwordSearchEmail.value}, {
    headers: {
    "Content-Type": "application/json"
  }})
  .then (function (response) {
        alert('이메일로 임시비밀번호를 전송하였습니다.')
  })
	  
.catch(function (error) {
	alert(error.message)
})
}

</script>

<style scoped>

#passwordsearchcontainer {
  width: 1200px;
  height: 300px;
  margin: 250px auto 50px;
  display: flex;
  flex-grow:1;
}

</style>