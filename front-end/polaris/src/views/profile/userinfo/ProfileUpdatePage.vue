<template>
  <div class="container mx-auto mt-8 max-w-6xl">
    <h1 class="text-2xl font-bold">프로필 수정</h1>
    <div class="flex justify-end">
      <!-- form-submit 하는 유일한 buttons -->
      <form action="">
        <button type="submit"  class="hover:bg-gray-500" >
          <i class="fa-regular fa-square-check fa-lg"></i>
          수정 완료
        </button>
      </form>
      <button type="button"  class="hover:bg-gray-500" @click="cancelProfile">취소</button>
    </div>
    <div class="container grid grid-cols-12 gap-4">
      <!-- 프로필 사진 -->
      <div class="col-span-4">
        <div>
          <div class="flex justify-center">
            <img src="@/assets/profile-default.jpg" alt="profile-image" id="profile-image">
            <!-- <img v-if="user.profile_url" id="profile-image" :src="user.profile_url" alt="대체 이미지" />
            <img v-else id="profile-image" src="@\assets\profile-default.jpg" alt=""> -->
          </div>
          <!-- {{ imageUrl }} -->
          <div class="text-maintheme1 text-center mt-3">
            <div>
              <label for="update-image" class="custom-file-input-label">
                <button id="update-image-button" @click="triggerFileInput" >
                  <font-awesome-icon icon="fa-solid fa-images" />
                  대표사진 변경</button>
              </label>
              <input id="update-image" type="file" @change="handleFileChange" class="hidden" />
            </div>
            <div class="font-bold mt-2">역삼동미친고양이</div>
          </div>
        </div>
      </div>
        <!-- 기본정보 -->
      <div class="col-span-8 m">
        <h2 class="font-bold mb-4 text-lg">기본 정보</h2>
        <button type="button"  class="password-change" @click="gotoPasswordChange">
          <font-awesome-icon icon="fa-solid fa-lock" />
          비밀번호 변경
        </button>
        <div class="font-semibold mt-8">닉네임</div>
        <input type="nickname" id="Usernickname" :placeholder="user.nickname" class="mt-2 mb-4 rounded-md border h-8"/>
        <div class="font-semibold">나의 위치</div>
        <input readonly type="location" id="UserLocation" :placeholder="user.regcode_id" class="mt-2 mb-4 w-64 rounded-md border h-8"/>
        <button type="button">
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
          class="mt-2 mb-4 w-full resize-none sm:text-sm"
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
  import { ref } from 'vue';
  import axios from 'axios';
  import { useRouter } from 'vue-router'
  import { profileCounterStore } from '@/stores/profilecounter';

  const store = profileCounterStore();
  const router = useRouter();
  const imageUrl = ref<string | null>(null);
  const user = store.user

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
    // // axios 요청
    // axios({

    // })
    // .then((response) => {

    // })
    // .catch((error) => {
    //   console.error(error)
    // })
    router.push({name: "ProfilePage"})
  }
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
  /* 예시 스타일, 프로젝트에 맞게 수정하세요 */
  padding: 10px;
  background-color: #3490dc;
  color: #fff;
  border: none;
  cursor: pointer;
}


#profile-image {
    @apply w-[150px] h-[150px] object-cover justify-items-center rounded-[70%] border border-maintheme1 
}


button {
    @apply w-auto bg-[#323F59] border text-white m-[3px] px-2.5 py-[5px] rounded-[10px] border-solid border-[black] hover:bg-gray-600;
}
</style>
