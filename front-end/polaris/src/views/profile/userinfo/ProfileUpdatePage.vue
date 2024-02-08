<template>
  <RegionModal v-if="isRegionModalOpen" @close="closeRegionModal" @confirm="updateRegion"/> 
  <div class="container mx-auto mt-28 max-w-6xl bg-backgroundgray">
    <h1 class="text-2xl font-bold p-4 m-4">프로필 수정</h1>
    <div class="flex justify-end">
      <!-- 제출 및 취소 buttons -->
      <button id="submit-button" @click="updateProfile" type="button">
        <i class="fa-regular fa-square-check fa-lg"></i>
        수정 완료
      </button>
      <button id="cancel-button" type="button" @click="cancelProfile">취소</button>
    </div>
    <div class="container grid grid-cols-12 gap-4">
      <!-- 프로필 사진 -->
      <div class="col-span-4">
        <div>
          <div class="flex justify-center">
            <img v-if="imageUrl" id="profile-image" :src="imageUrl" alt="대체 이미지" />
            <img v-else id="profile-image" src="@\assets\profile-man.jpg" alt="">
          </div>
          <div class="text-maintheme1 text-center mt-3">
            <div>
              <label for="update-image" class="custom-file-input-label">
                <!-- <button id="update-image-button" @click="triggerFileInput" >
                  <font-awesome-icon icon="fa-solid fa-images" />
                  대표사진 변경</button> -->
              </label>
              <input id="update-image" type="file" @change="handleFileChange" class="hidden" />
            </div>
            <div class="font-bold mt-2">{{ loginUser.nickname }}</div>
          </div>
        </div>
      </div>
        <!-- 기본정보 -->
      <div class="col-span-8 m">
        <h2 class="font-bold mb-4 text-lg">기본 정보</h2>
        <button id="change-password-button" type="button"  class="password-change" @click="gotoPasswordChange">
          <font-awesome-icon icon="fa-solid fa-lock" />
          비밀번호 변경
        </button>
        <div class="font-semibold mt-8">닉네임</div>
        <input type="nickname" v-model="usernickname" id="Usernickname"  class="w-64 mt-2 mb-4 rounded-md border h-8"/>
        <button @click="nicknameCheck" type="button" id="update-loc-button">
          <font-awesome-icon icon="fa-solid fa-circle-user" />
          닉네임 확인
        </button>
        <div></div>
        <div class="font-semibold">나의 위치</div>
        <input readonly v-model="mylocation" type="location" id="UserLocation" class="mt-2 mb-4 w-64 rounded-md border h-8"/>
        <button @click="openRegionModal" type="button" id="update-loc-button">
          <font-awesome-icon icon="fa-solid fa-location-dot" />
          위치찾기
        </button>
        <div class="font-semibold">ABOUT ME</div>
        <div class="mb-4 pe-4">
          <label for="OrderNotes" class="sr-only">Order notes</label>
          <div
          class=" rounded-lg border border-gray-200 shadow-sm"
          >
          <textarea
          id="OrderNotes"
          v-model="introduction"
          class="mt-2 w-full resize-none sm:text-sm"
          rows="5"
          ></textarea>
        </div>
      </div>
      </div>
    </div>
  </div>
</template>


<script setup lang="ts">
  import { computed, onMounted, ref, watch } from 'vue';
  import axios from 'axios';
  import { useRouter, useRoute } from 'vue-router'
  import { profileCounterStore } from '@/stores/profilecounter';
  import RegionModal from '@/components/Auth/RegionModal.vue';
  import type { User } from '@/stores/profilecounter';

  const store = profileCounterStore();
  const BACK_API_URL = store.BACK_API_URL
  const router = useRouter();
  const route = useRoute();
  const userInfo = ref<User|null>(null)
  const userInfoString = ref<string>(localStorage.getItem('user_info') ?? "");
  const loginUser = JSON.parse(userInfoString.value)
  const usernickname = ref<string>(loginUser.nickname)
  const imageUrl = ref<string | null>(loginUser.profileUrl);
  const isValidNickname = ref(usernickname.value==loginUser.nickname)
 
  const mylocation = ref("")
  const mylocationCode = ref<number|null>(null)
  const introduction = ref<string|null>("")
  const isRegionModalOpen = ref(false)

  watch(() => usernickname.value, (newNickname, oldNickname) => {
  console.log(newNickname)
  if(newNickname == loginUser.nickname){
    isValidNickname.value = true
  } else {
    isValidNickname.value = false;
  }
  console.log(newNickname == loginUser.nickname)
});


  const openRegionModal = () => {
    isRegionModalOpen.value = true
  }

  const closeRegionModal = () => {
    isRegionModalOpen.value = false
  }

  // 위치 업데이트
  const updateRegion = (newRegion: {name: string, code: string}) => {
    mylocation.value = newRegion.name
    mylocationCode.value = Number(newRegion.code)
    closeRegionModal()
  }

  // 닉네임 검사
  const nicknameCheck = () => {
    axios({
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'get',
      url: `${BACK_API_URL}/user/nickname_check/${usernickname.value}`

    })
    .then((response)=> {
      const res = response.data.data
      if(!res.isInUse){
        alert("사용 가능한 닉네임입니다.")
        isValidNickname.value = true
      } else {
        alert("중복된 닉네임입니다. 다른 닉네임을 설정해 주세요.")
        usernickname.value = ""
        
      }

    })
    .catch((error)=> {
      console.error(error)
    })
  }
  
  // 이미지 파일 변경
  const handleFileChange = (event: Event) => {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
  
    if (file) {
      const reader = new FileReader();
  
      reader.onload = (e) => {
        imageUrl.value = e.target?.result as string;
      };
      reader.readAsDataURL(file);
      // 파일 업로드 후 파일 이름 비우기
      input.value = '';
    }
  };
  
  const triggerFileInput = () => {
    const fileInput = document.getElementById('update-image') as HTMLInputElement;
    fileInput.click();
  };

  // button 들
  function cancelProfile() {
    router.push({name: "ProfilePage"});
  }

  function gotoPasswordChange() {
    router.push({name: "PasswordChangePage"});
  }
  
    
    
  const updateProfile = () => {
    if (usernickname.value ==""){
      alert("닉네임을 입력해주세요.")
    } else if (!isValidNickname.value) {
      alert("닉네임 확인이 필요합니다.")
    } else if (mylocationCode.value == 0){
      alert("나의 위치를 등록해야 합니다.")
    } else {
    // axios 요청
    axios({
      method: 'patch',
      url: `${BACK_API_URL}/profile/${loginUser.id}`,
      headers: {
        Authorization: `${store.token}`,
        "Content-Type": 'application/json'
      },
      data: {
        nickname: usernickname.value,
        regcodeId: mylocationCode.value,
        introduction: introduction.value,
        imageUrl: imageUrl.value,
      },
    })
    .then((response) => {
      console.log(response.data)
      alert("프로필이 수정되었습니다.")
      store.getProfile(Number(route.params.id))
    })
    .catch((error) => {
      console.error(error)
    })
              
      router.push({name: "ProfilePage"})
    }
  } 

  const getProfile = (id: number) => {
  axios({
    headers: {
      Authorization: `${store.token}`,
      "Content-Type": 'application/json'
    },
      method: 'get',
      url: `${BACK_API_URL}/profile/${loginUser.id}`,
    })  
  .then((response) => {
    const userData = response.data['data']
    console.log(`${id}번유저정보`,userData)
    userInfo.value = userData
      if(userInfo.value){
        mylocation.value = `${userInfo.value.regcode.si} ${userInfo.value.regcode.gungu} ${userInfo.value.regcode.dong}`
        mylocationCode.value = userInfo.value.regcode.id
        introduction.value = userInfo.value.introduction
      }
    })
    .catch((error)=> {
      console.error("에러발생: ",error)
    })
  }
  
  onMounted(()=> {
    getProfile(loginUser.id)
  })
</script>

<style scoped>
#update-image {
  opacity: 0;
  position: absolute;
  /* left: -9999px; */
}

.custom-file-input-label {
  display: inline-block;
  cursor: pointer;
}

.custom-file-input-button {
  padding: 10px;
  background-color: #3490dc;
  color: #fff;
  border: none;
  cursor: pointer;
}


#profile-image {
    @apply w-[150px] h-[150px] object-cover justify-items-center rounded-[70%] border border-maintheme1 
}

input {
  @apply bg-white
}

#update-loc-button,
#update-image-button,
#change-password-button,
#submit-button {
    @apply w-auto bg-[#323F59] border text-white m-[3px] px-2.5 py-[5px] rounded-[10px] border-solid  hover:bg-gray-500;
}

#cancel-button {
  @apply font-bold bg-white border border-maintheme1 text-maintheme1 m-2 hover:bg-slate-200 m-[3px] px-2.5 py-[5px] rounded-[10px] border-solid 
}

</style>