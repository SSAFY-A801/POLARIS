<template>
  <div class="container mx-auto mt-8 max-w-6xl bg-backgroundgray">
    <h1 class="text-2xl font-bold">프로필 수정</h1>
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
            <img v-else id="profile-image" src="@\assets\profile-default.jpg" alt="">
          </div>
          <div class="text-maintheme1 text-center mt-3">
            <div>
              <label for="update-image" class="custom-file-input-label">
                <button id="update-image-button" @click="triggerFileInput" >
                  <font-awesome-icon icon="fa-solid fa-images" />
                  대표사진 변경</button>
              </label>
              <input id="update-image" type="file" @change="handleFileChange" class="hidden" />
            </div>
            <div class="font-bold mt-2">{{ user.nickname }}</div>
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
        <input type="nickname" v-model="nickname" id="Usernickname" :placeholder="user.nickname" class="w-64 mt-2 mb-4 rounded-md border h-8"/>
        <div class="font-semibold">나의 위치</div>
        <input readonly v-model="mylocation" type="location" id="UserLocation" :placeholder="user.regcode_id" class="mt-2 mb-4 w-64 rounded-md border h-8"/>
        <button type="button" id="update-loc-button">
          <font-awesome-icon icon="fa-solid fa-location-dot" />
          위치찾기
        </button>
        <div class="font-semibold">ABOUT ME</div>
        <div class="mb-4">
          <label for="OrderNotes" class="sr-only">Order notes</label>
          <div
          class=" rounded-lg border border-gray-200 shadow-sm"
          >
          <textarea
          id="OrderNotes"
          v-model="introduction"
          class="mt-2 w-full resize-none sm:text-sm"
          rows="5"
          :placeholder="user.introduction"
          ></textarea>
        </div>
      </div>
      </div>
    </div>
  </div>
</template>


<script setup lang="ts">
  import { computed, ref } from 'vue';
  import axios from 'axios';
  import { useRouter } from 'vue-router'
  import { profileCounterStore } from '@/stores/profilecounter';

  const store = profileCounterStore();
  const router = useRouter();
  const user = ref(store.user)
  const imageUrl = ref<string | null>(user.value.profile_url);
  const nickname = ref("")
  const mylocation = ref("")
  const introduction = ref("")
  const BACK_API_URL = store.BACK_API_URL

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

  // button
  function cancelProfile() {
    router.push({name: "ProfilePage"});
  }

  function gotoPasswordChange() {
    router.push({name: "PasswordChangePage"});
  }
  
    
    
  function updateProfile(){
    if (nickname.value ==""){
      alert("닉네임을 입력해주세요.")
    } else {
      // 임시 확인 코드
      user.value.nickname = nickname.value,
      user.value.regcode_id = mylocation.value,
      user.value.introduction = introduction.value,
      user.value.profile_url = imageUrl.value,
    // // axios 요청
    // axios({
    //   method: 'put',
    //   url: `${BACK_API_URL}/profile/${user.value.id}`,
    //   headers: {
      
    //   },
    //   data: {
    //     nickname : nickname.value,
    //     regcode_id : mylocation.value,
    //     introduction : introduction.value,
    //     profile_url : imageUrl.value,
    //   },
    // })
    // .then((response) => {
    //   console.log(response.data)
    //   alert("프로필이 수정되었습니다.")
    // })
    // .catch((error) => {
    //   console.error(error)
    // })
              
      router.push({name: "ProfilePage"})
    }
  }
  
  // 닉네임 체크
  // const nicknamecheck = computed(() => {
  //   if(nickname.value.length > 20){
  //   }
  //   return nickname.value
  // })
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
  @apply border border-gray-300 text-maintheme1 m-2 hover:bg-slate-200 m-[3px] px-2.5 py-[5px] rounded-[10px] border-solid 
}

</style>
