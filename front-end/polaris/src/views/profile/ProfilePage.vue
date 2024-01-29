<template>
  <div class="flex justify-center">
    <div class="container w-full mt-8 max-w-6xl bg-backgroundgray p-4">
      <!-- 프로필 페이지 상단 -->
      <div class="upper-section">
        <div class="flex justify-end">
          <font-awesome-icon icon="fa-solid fa-rotate-right" />
          <button v-if="isMe" id="profile-update" @click="gotoUpdateProfile" class="profile-button hover:bg-gray-500">
            <font-awesome-icon icon="pen-to-square" />
            프로필 수정
          </button>
        </div>
        <div class="grid grid-cols-12 gap-4">
          <!-- 프로필 상단 - 좌측 -->
          <div class="md:col-span-4 col-span-12" >
            <div class="flex justify-center">
              <img src="@\assets\profile-default.jpg" alt="profile-image" id="profile-image">
            </div>
              <div v-if="isMe" class="text-maintheme1 font-bold text-center m-3 justify-center p-2">
                <div id="userid" class="font-bold flex items-center justify-center m-2">
                  왕이될상의고양이  
                </div>
              </div>
              <div v-else class="text-maintheme1 font-bold text-center m-3 grid grid-cols-3 justify-center p-2">
                <div id="userid" class="col-span-2 font-bold flex items-center justify-center m-2">
                  왕이될상의고양이  
                </div>
                <button v-if="myFollwing"  class="col-span-1" id="follow">
                  언팔로우
                </button>
                <button v-else  class="col-span-1" id="follow">
                  팔로우
                </button>
              </div>
            <div class="flex justify-center mb-8">
              <div class="inline-grid grid-cols-3 gap-4">
                <button @click="gotoTradeList" id="trade" class=" hover:text-deepgray">
                  <div>판매/구매</div>
                  <div>100</div>
                </button>
                <button @click="gotoExchangeList" id="exchange" class=" hover:text-deepgray">
                  <div >교환</div>
                  <div>50</div>
                </button>
                <button @click="gotoFollowingList" id="following" class="hover:text-deepgray">
                  <div>Following</div>
                  <div>122</div>
                </button>
              </div>
            </div>
          </div>
          <!-- 프로필 상단 - 우측 -->
          <div class="md:col-span-8 col-span-12 m-2">
            <div class="container grid grid-cols-4 flex">
              <div class="text-maintheme1 m-2 font-bold">
                <div class="mb-2">나의 위치</div>
              </div>
              <div class="col-span-2 text-maintheme1 m-2">
                <div class="mb-2">서울특별시 강남구 역삼동</div>
              </div>
            </div>
            <div class="container grid grid-cols-4 flex mb-20">
              <div class="text-maintheme1 m-2 font-bold">
                <div>ABOUT ME</div>
              </div>
              <div class="col-span-2 text-maintheme1 m-2">
                  책 읽기를 좋아하는 세상에서 제일 귀여운 고양이입니다. 
                  제가 쓴 독후감 많이 읽어주세요.북극성 정말 잘 사용하고 있어요. 히히
                  그리고용 또..
              </div>
            </div>
            <div class="flex justify-end">
              <div v-if="!isMe">
                <button @click="gotoTradechat" id="tradechat">
                  구매 채팅
                </button>
                <button @click="gotoExchangechat" id="exchangechat">
                  교환 채팅
                </button>
              </div>
              <div v-else>
                <button @click="gotoMychatList" id="mychat">
                  나의 채팅
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- tabs -->
      <div class="hidden sm:block">
        <div class=" border-gray-200 dark:border-gray-700 bg-maintheme1">
          <nav class="-mb-px flex gap-6">
            <div class="shrink-0 border border-transparent p-3 text-sm font-medium text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200">
              <RouterLink :to="{ name: 'MyLibraryPage' }">
                <a
                  class="shrink-0 border border-transparent p-3 text-sm font-medium text-gray-500 hover:text-gray-400 dark:text-gray-400 dark:hover:text-gray-200"
                >
                나의 서재 
                </a>
              </RouterLink>
              <!-- 접속자 == User 인 경우에만 보여주기  -->
              <span v-if="isMe">
                <RouterLink :to="{ name: 'MyScrapsPage' }">
                  <a
                    class="shrink-0 border border-transparent p-3 text-sm font-medium text-gray-500 hover:text-gray-400 dark:text-gray-400 dark:hover:text-gray-200"
                  >
                  스크랩한 독후감 
                  </a>
                </RouterLink>
                <RouterLink :to="{ name: 'MyFavoritesPage' }">
                  <a
                    class="shrink-0 border border-transparent p-3 text-sm font-medium text-gray-500 hover:text-gray-400 dark:text-gray-400 dark:hover:text-gray-200"
                  >
                  찜한 홍보 게시글
                  </a>
                </RouterLink>
                <RouterLink :to="{ name: 'MyArticlePage' }">
                  <a
                    class="shrink-0 border border-transparent p-3 text-sm font-medium text-gray-500 hover:text-gray-400 dark:text-gray-400 dark:hover:text-gray-200"
                  >
                    나의 게시글
                  </a>
                </RouterLink>
              </span>
            </div>
          </nav>
        </div>
      </div>
      <!-- 하위 페이지 -->
      <RouterView></RouterView>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted } from "vue";
  import { useRouter } from 'vue-router'
  const router = useRouter();
  const isMe = ref<boolean>(true)
  const myFollwing = ref<boolean>(true)

  // button 클릭
  const gotoUpdateProfile = () => {
    router.push({name: "ProfileUpdatePage"});
  }

  const gotoTradeList = () => {
    router.push({name: "MyTradeListPage"});
  }

  const gotoExchangeList = () => {
    router.push({name: "MyExchangeListPage"});
  }

  const gotoFollowingList = () => {
    router.push({name: "FollowingListPage"});
  }

  const gotoMychatList = () => {
    // router.push({name: "FollowingListPage"});
  }

  const gotoTradechat = () => {
    // router.push({name: "FollowingListPage"});
  }

  const gotoExchangechat = () => {
    // router.push({name: "FollowingListPage"});
  }

</script>

<style scoped>
#profile-image {
    @apply w-[150px] h-[150px] object-cover justify-items-center rounded-[70%] border-[3px] border-solid border-[#121212];
}

#tradechat,
#exchangechat,
#mychat,
#follow,
#profile-update {
    @apply bg-[#323F59] border text-white m-[5px] px-2.5 py-[5px] rounded-[10px]  hover:bg-gray-500;
}
a {
    @apply text-white
}
</style>