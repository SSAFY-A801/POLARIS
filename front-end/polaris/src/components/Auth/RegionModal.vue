<template>
  <div class="modal">
    <div class="self-center text-center mt-4 mb-4 font-bold text-gray-600 sm:text-2xl">
        위치 선택하기
    </div>
    <div class="form-group col-md-2 mt-8">
      <label class="text-gray-700" for="sido">시/도 선택
      <select id="sido" v-model="selectedSido" class="block px-3 py-2 mt-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" name="sido">
        <option value=""  >시/도 정보를 선택하세요</option>
        <option v-for="sido in sidoList" :value="{code:sido.code , name:sido.name}">{{ sido.name }}</option>
      </select></label>
      <!-- <div class="flex items-center space-x-2 w-full"><p  class="border text-black-600 w-full pt-4 pr-4 pb-4 pl-4 mt-2 mr-0 mb-2 ml-0 text-base block bg-white border-gray-300 rounded-md  ">{{ sidoInput }}</p></div> -->
    </div>
    
    
    <div class="form-group col-md-2">     
      <label class="text-gray-700" for="gugun">구/군 선택
      <select id="gugun" v-model="selectedGugun"  class="block px-3 py-2 mt-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent"  name="gugun">
        <option disabled value="">구/군 정보를 선택하세요</option>
        <option v-for="gugun in gugunList" :value="{code:gugun.code , name:gugun.name}">{{ gugun.name }}</option>
      </select></label>
      <!-- <div class="flex items-center space-x-2 w-full"><p  class="border text-black-600 w-full pt-4 pr-4 pb-4 pl-4 mt-2 mr-0 mb-2 ml-0 text-base block bg-white border-gray-300 rounded-md  ">{{ gugunInput }}</p></div> -->
    </div>
    
    <div class="form-group col-md-2">     
      <label class="text-gray-700" for="dong">동 선택 
      <select id="dong" v-model="selectedDong" class="block px-3 py-2 mt-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" name="dong">
        <option value="">동/동 정보를 선택하세요</option>
        <option v-for="dong in dongList" :value="{code:dong.code , name:dong.name}">{{ dong.name }}</option>
      </select></label>
      <!-- <div class="flex items-center space-x-2 w-full"><p  class="border text-black-600 w-full pt-4 pr-4 pb-4 pl-4 mt-2 mr-0 mb-2 ml-0 text-base block bg-white border-gray-300 rounded-md  ">{{ dongInput }}</p></div> -->
    </div>

    <div class="form-group col-md-auto">
      <button type="button" id="star-btn" class="btn btn-outline-primary">
        <i class="bi bi-star"></i>
      </button>

      <button @click="confirmRegion" id="nicknamecheck" type="button" class="py-2 px-4 mt-6 bg-white border border-gray-300 hover:border-2 hover:border-maintheme1 hover:text-maintheme1 text-gray-600 transition ease-in duration-200 text-center text-base font-medium  rounded-lg " >
                  확인
              </button>
    </div>
  </div>

</template>

<script setup lang="ts">
import { ref, defineEmits, watchEffect } from 'vue'
import axios from 'axios'

interface Region {
  code: number
  name: string
}

const regionInputName = ref('')
const regionInputCode = ref('')
const emit = defineEmits(['confirm'])

const confirmRegion = () => {
  // console.log('modal:',{ name: regionInputName.value, code: regionInputCode.value })
  emit('confirm', { name: regionInputName.value, code: regionInputCode.value } )
}

const selectedSido = ref<Region | null>(null)
const selectedGugun = ref<Region | null>(null)
const selectedDong = ref<Region | null>(null)

const sidoList = ref<Region[]>([])
const gugunList = ref<Region[]>([])
const dongList = ref<Region[]>([])

watchEffect(async () => {
  if (selectedSido.value) {
    // console.log(selectedSido.value)
    await axios.get(`${import.meta.env.VITE_API_KEY}/regcode/gugun?sido=${selectedSido.value.name}`, {
    headers: {
    "Content-Type": "application/json",
  }
  })
    .then (function(response) {
      gugunList.value = response.data.data.regcodes
    })
  }
})

watchEffect(async () => {
  if (selectedSido.value && selectedGugun.value) {
    await axios.get(`${import.meta.env.VITE_API_KEY}/regcode/dong?sido=${selectedSido.value.name}&gugun=${selectedGugun.value.name}`, {
    headers: {
    "Content-Type": "application/json",
  }
  })
    .then (function(response) {
       dongList.value = response.data.data.regcodes
    })
  }
})

watchEffect(() => {
  if (selectedSido.value && selectedGugun.value && selectedDong.value) {
    // console.log(selectedSido.value)
    regionInputName.value = `${selectedSido.value.name} ${selectedGugun.value.name} ${selectedDong.value.name}`
    regionInputCode.value = selectedDong.value.code.toString()
  }
});

(async () => {
  await axios.get(`${import.meta.env.VITE_API_KEY}/regcode/sido`, {
    headers: {
    "Content-Type": "application/json",
  }
  })
  .then (function(response) {
    sidoList.value = response.data.data.regcodes
  })
})()



</script>

<style scoped>
.modal {
  position: fixed;
  top: 60%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 350px; /* 원하는 너비로 조절 */
  height: 500px; /* 원하는 높이로 조절 */
  padding: 20px;
  background-color: white;
  border: 1px solid #ccc;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 20px;
  }

select {
  width: 100%;
  
}
</style>
