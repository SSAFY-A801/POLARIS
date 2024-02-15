<template>
  <Navvar></Navvar>

<div class="mt-40 font-[gowun-dodum]">
  <div class="m-auto flex justify-center">
    <div class="mt-24 mb-10 ml-10 w-2/3">
        <h1 class=" text-3xl font-bold text-maintheme1">사용자 도서 검색</h1>      
    </div>
  </div>

    
    <div class="m-auto flex justify-center">
    <div class="w-2/3 border-2 rounded-lg ">
    <!-- 위치 정보 -->
    <nav class="grid grid-cols-4 bg-white flex w-full mt-10 " style="display: flex; justify-content: center;">
        <div class="flex items-center justify-end w-1/4 h-24 bg-white mr-4"><font-awesome-icon icon="fa-solid fa-location-dot" size='lg' />
            <p class="ml-2">위치 필터</p>
        </div>
        <div class='flex items-center justify-start w-3/4 h-24 bg-white ml-10 grid-cols-3'>
        <div class='flex items-center justify-end w-1/5 h-24 bg-white ml-10'>
        <select id="sido" v-model="selectedSido" class="mr-2  block px-3 py-2 text-gray-700 bg-white border border-gray-400 rounded-md shadow-sm w-40 focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" name="sido">
            <option value="null" disabled >시/도 선택</option>
            <option v-for="sido in sidoList" :value="{code:sido.code , name:sido.name}">{{ sido.name }}</option>
        </select>
        </div>
        <div class='flex items-center justify-end w-1/6 h-24 bg-white ml-10'>
        <select id="gugun" v-model="selectedGugun" class="mr-2 block px-3 py-2 text-gray-700 bg-white border border-gray-400 rounded-md shadow-sm w-40 focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent"  name="gugun">
            <option disabled value="null">구/군 선택</option>
            <option v-for="gugun in gugunList" :value="{code:gugun.code , name:gugun.name}">{{ gugun.name }}</option>
        </select>
        </div>
        <div class='flex items-center justify-end w-1/6 h-24 bg-white ml-10'>
        <select id="dong" v-model="selectedDong" class="mr-2 block px-3 py-2 text-gray-700 bg-white border border-gray-400 rounded-md shadow-sm w-40 focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" name="dong">
            <option disabled value="null">동 선택</option>
            <option v-for="dong in dongList" :value="{code:dong.code , name:dong.name}">{{ dong.name }}</option>
        </select>
        </div>
        </div>
        
    </nav>

    <!-- 검색바 -->

    


    <nav class="bg-white mb-10 top-0 w-full mt-4" style="display: flex; justify-content: center;">
        <div class="flex items-center justify-end w-1/4 h-24 bg-white"><font-awesome-icon icon="fa-solid fa-filter" size="lg"/>
            <p class="ml-2">검색 필터</p>
        </div>
        <div class="flex items-center justify-start w-3/4 h-24 bg-white ml-10">
        <div class='flex items-center justify-end w-1/6 h-24 bg-white ml-10'>
        <select class="mr-2 block px-3 py-2 text-gray-700 bg-white border border-gray-400 rounded-md shadow-sm w-36 focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent" v-model="queryType" name="searchfilter">
            <option value="" disabled>검색 구분</option>
            <option value="title">제목</option>
            <option value="author">저자</option>
        </select>
        </div>
        <div class='flex items-center justify-start w-2/3 h-24 bg-white'>
            <div class="flex w-3/4 mx-10 rounded bg-white">
                <input @keyup.enter="bookSearch" v-model="keyword" class=" mt-4 h-12 w-full border border-gray-400 bg-transparent rounded-md px-4 py-1 text-black focus:outline-none focus:ring-2 focus:ring-maintheme1 focus:border-transparent " type="search" name="search" placeholder="검색어를 입력하세요" />
                <button @click.prevent=bookSearch type="submit" class="ml-10 m-4 h-12 rounded bg-maintheme1 px-4 py-4 text-white">
                    <p class="text-white w-14 mt-0">검색</p>
                </button>
            </div>
        </div>
    </div>
    </nav>
    </div>
    </div>

    <!-- 검색 결과 -->
    <div v-if="booksearchResultList.length === 0" class="m-auto flex justify-center">
      <div class="mt-10 mb-10 ml-10 w-2/3">
          <h1 class=" text-2xl font-bold text-maintheme1">검색 결과를 찾을 수 없습니다</h1>      
      </div>
    </div>

    <div v-else  class="m-auto flex justify-center">
      <div class="mt-10 mb-10 ml-10 w-2/3">
          <h1 class=" text-2xl font-bold text-maintheme1">검색 결과</h1>      
      </div>
    </div>

    <div class="m-auto w-full flex justify-center">
        <div class="mt-10 flex flex-wrap justify-center" style="width: fit-content;">
          <div id="booksearchcard" v-for="booksearchResult in booksearchResultList" class="m-10 overflow-hidden rounded-lg shadow-lg cursor-pointer w-full sm:w-1/2 md:w-1/3 lg:w-1/4 xl:w-1/5">
            <RouterLink :to="{name: 'BookDetailPage', params: {id: booksearchResult.userId, isbn: booksearchResult.isbn}}" >
              <button  class="block w-full h-full flex flex-col justify-center items-center">
                <img id="booksearchcardcover" :src="booksearchResult.cover" class="object-cover"/>
                <div class="w-full p-4 bg-white dark:bg-gray-800">
                    <p class="mb-2 text-md font-medium text-gray-800 dark:text-white">{{ truncateTitle(booksearchResult.title) }}</p>
                    <p class="font-light text-gray-400 dark:text-gray-300 text-s">{{ truncateTitle(booksearchResult.author) }}</p>

                    <div v-if="booksearchResult.userBookTradeType =='PURCHASE'" id="trade"  class="rounded-lg mt-2 mb-2 col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2  bg-red-600"  >
                        <p  class="p-1.5 text-lightgray">판매</p>
                    </div>
                    <div v-else-if="booksearchResult.userBookTradeType == 'EXCHANGE'" id="exchange"   class="rounded-lg mt-2 mb-2 col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2 bg-yellow-500">
                        <p  class="p-1.5 text-lightgray">교환</p>
                    </div>
                    <div v-else id="undefined"   class="rounded-lg mt-2 mb-2 col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2 bg-gray-500">
                        <p  class="p-1.5 text-lightgray">미거래</p>
                    </div>

                    <div class="flex mt-4">
                    <img :src="booksearchResult.profileUrl" class="mx-6 object-cover rounded-full border-2 w-10 h-10"/>
                    <div class="flex flex-col justify-between text-sm">
                        <p class="text-gray-800 dark:text-white">{{ booksearchResult.nickname }}</p> 
                        <p class="text-gray-400 dark:text-gray-300">{{ booksearchResult.regcode ? `${booksearchResult.regcode.si} ${booksearchResult.regcode.gungu} ${booksearchResult.regcode.dong}` : '' }}</p>
                    </div>
                                                                      
                    </div>
                </div>
              </button>
            </RouterLink>
        </div>
    </div>
  
   </div>
    
</div>

   
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { ref, watchEffect } from 'vue'
import { useRouter } from 'vue-router';
import axios from 'axios'
import Swal from 'sweetalert2';

//위치 필터
interface Region {
  code: number
  name: string
}

const router = useRouter();
const regionInputName = ref('')
const regionInputCode = ref('')


const selectedSido = ref<Region | null>(null)
const selectedGugun = ref<Region | null>(null)
const selectedDong = ref<Region | null>(null)

const sidoList = ref<Region[]>([])
const gugunList = ref<Region[]>([])
const dongList = ref<Region[]>([])


router.beforeEach((to, from, next) => {
  // 내부에 선언
  const userToken = localStorage.getItem('user_token')
  if (to.name === 'BookDetailPage' && !userToken) {
    // 'BookDetailPage' 라우트이면서 userToken이 없는 경우
    Swal.fire({
      title: "로그인이 필요한 작업입니다.",
      icon: 'error'
    }).then(() => {
      next({ path: '/login' });
    });
  } else {
    // 'BookDetailPage' 이외의 다른 라우트 또는 'BookDetailPage'이지만 userToken이 있는 경우
    next();
  }
});



watchEffect(async () => {
  if (selectedSido.value) {
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
    regionInputName.value = `${selectedSido.value.name} ${selectedGugun.value.name} ${selectedDong.value.name}`
    regionInputCode.value = selectedDong.value.code.toString()
    // console.log(regionInputCode.value)
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

//퀴리 타입
const queryType = ref('')
// console.log(queryType.value)

//검색어
const keyword = ref('')




//데이터 요청
type regcodeType = {id:number, si:string, gungu:string, dong:string}
type booksearchResultType = {author:string ,cover:string , id:number, isbn:string, nickname:string, profileUrl:string, regcode:regcodeType, title:string, userBookTradeType: string, userId: number}
const booksearchResultList = ref<booksearchResultType[]>([])


const bookSearch = async () => {
  // console.log(regionInputCode.value)
  // console.log(queryType.value)
  // console.log(keyword.value)

  let params: { [key: string]: string } = {};

  if (regionInputCode.value) {
      params.regcode = regionInputCode.value;
  }

  if (queryType.value) {
      params.queryType = queryType.value;
  }

  if (keyword.value) {
      params.keyword = keyword.value;
  }

  await axios.get(`${import.meta.env.VITE_API_KEY}/book/search`, { params })
    .then(function (response) {
        // console.log(response.data.data.searchBooks)
        booksearchResultList.value = response.data.data.searchBooks
        // console.log('booksearchResult.value', booksearchResultList.value)


    })
    .catch(function (error) {
        alert(error.message)
    }) 
    } 
    
const truncateTitle = (title: string) => {
  return title.length > 22 ? title.substring(0, 22) + "..." : title;
}


</script>

<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../../public/GowunDodum-Regular.ttf');
}


#booksearchcard{
    height: 400px;
    width: 300px;
}

#booksearchcardcover{
    height: 180px;
}
</style>