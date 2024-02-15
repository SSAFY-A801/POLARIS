<template>
  <Navvar></Navvar>
  <div class="mt-40">
    <!-- Main modal -->
    <div
      v-if="showModal"
      id="default-modal"
      tabindex="-1"
      aria-hidden="true"
      class="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-50 w-full max-w-2xl max-h-full bg-white shadow dark:bg-gray-700 font-[gowun-dodum]"
    >
      <div class="relative w-full max-w-2xl max-h-full">
        <!-- Modal content -->
        <!-- Modal header -->
        <div class="flex justify-between bg-maintheme1 text-white">
          <h3 class="text-xl text-white m-3">Following 목록</h3>
        </div>
        <div
          class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600"
        ></div>
        <!-- Modal body -->
        <div
          v-if="followings_list.length"
          id="following-list"
          class="overflow-auto max-h-[500px] overflow-y-auto"
        >
          <!-- 팔로잉 목록 -->
          <followingListitem
            v-for="(following, index) in followings_list"
            :key="index"
            :following="following"
            @follow-toggle="handlefollow"
            @click-profile="handleModal"
            class="border p-3 min-w-[400]"
          />
        </div>
        <div v-else class="text-center text-lg font-bold">
          현재 팔로우한 유저가 존재하지 않습니다.
        </div>
        <!-- Modal footer -->
        <div
          class="flex justify-between p-4 md:p-5 border-t border-gray-200 rounded-b dark:border-gray-600"
        >
          <button
            id="updateFollowings"
            @click="updateFollowings"
            data-modal-hide="default-modal"
            type="button"
            class="w-full text-xl"
          >
            확 인
          </button>
          <button
            id="closeModal"
            @click="closeModal"
            data-modal-hide="default-modal"
            type="button"
            class="w-full text-xl"
          >
            취 소
          </button>
        </div>
      </div>
    </div>

    <!-- 프로필 페이지 -->
    <div class="flex justify-center font-[gowun-dodum]">
      <div class="container w-full mt-8 max-w-6xl p-4">
        <!-- 프로필 페이지 상단 -->
        <div class="upper-section">
          <div class="flex justify-end">
            <button
              v-if="isMe"
              id="profile-update"
              @click="gotoUpdateProfile"
              class="profile-button hover:bg-gray-500"
            >
              <font-awesome-icon icon="pen-to-square" />
              프로필 수정
            </button>
          </div>
          <div class="grid grid-cols-12 gap-4">
            <!-- 프로필 상단 - 좌측 -->
            <div class="md:col-span-4 col-span-12">
              <div class="flex justify-center">
                <img
                  v-if="profileUser.profileUrl"
                  :src="profileUser.profileUrl"
                  alt="profile-image"
                  id="profile-image"
                />
                <img
                  v-else
                  src="@\assets\profile-man.jpg"
                  alt="alternative-image"
                  id="profile-image"
                />
              </div>
              <div v-if="isMe" class="text-maintheme1 font-bold text-center m-3 justify-center p-2">
                <div id="usernickname" class="font-bold flex items-center justify-center m-2">
                  {{ profileUser.nickname }}
                </div>
              </div>
              <div
                v-else
                class="text-maintheme1 font-bold text-center m-3 grid grid-cols-3 justify-center p-2"
              >
                <div
                  id="usernickname"
                  class="col-span-2 font-bold flex items-center justify-center m-2"
                >
                  {{ profileUser.nickname }}
                </div>
                <button
                  @click="unfollow(profileUser)"
                  v-if="myFollwing"
                  class="col-span-1"
                  id="follow"
                >
                  언팔로우
                </button>
                <button @click="follow(profileUser)" v-else class="col-span-1" id="follow">
                  팔로우
                </button>
              </div>
              <div class="flex justify-center mb-8">
                <div
                  class="inline-grid grid-cols-3 gap-4"
                  :class="{ 'pointer-events-none cursor-not-allowed': !isMe }"
                >
                  <button @click="gotoTradeList" id="trade" class="hover:text-deepgray">
                    <div>판매/구매</div>
                    <div>{{ profileUser.tradingCnt }}</div>
                  </button>
                  <button @click="gotoExchangeList" id="exchange" class="hover:text-deepgray">
                    <div>교환</div>
                    <div>{{ profileUser.exchangeCnt }}</div>
                  </button>
                  <button @click="clickModal" id="following" class="hover:text-deepgray">
                    <div>Following</div>
                    <div>{{ profileUser.followingsCnt }}</div>
                  </button>
                </div>
              </div>
            </div>
            <!-- 프로필 상단 - 우측 -->
            <div class="md:col-span-8 col-span-12 m-2 flex flex-col justify-between">
              <div class="grid grid-cols-4">
                <div class="text-maintheme1 m-2 font-bold col-span-1">
                  <div class="mb-2">나의 위치</div>
                </div>
                <div class="col-span-3 text-maintheme1 m-2">
                  <div class="mb-2">
                    {{ profileUser.regcode.si }} {{ profileUser.regcode.gungu }}
                    {{ profileUser.regcode.dong }}
                  </div>
                </div>
                <div class="text-maintheme1 m-2 font-bold">
                  <div class="col-span-2">ABOUT ME</div>
                </div>
                <div class="col-span-3 text-maintheme1 m-2">
                  {{ profileUser.introduction }}
                </div>
              </div>
              <div class="flex justify-end">
                <div v-if="!isMe">
                  <button @click="gotoTradechat" id="tradechat">구매 채팅</button>
                  <button @click="gotoExchangechat" id="exchangechat">교환 채팅</button>
                </div>
                <div v-else>
                  <button @click="gotoMychatList" id="mychat">
                    <font-awesome-icon icon="fa-solid fa-comments" style="color: #ffffff" />
                    나의 채팅
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- tabs -->
        <div class="hidden sm:block">
          <div class="border-gray-200 dark:border-gray-700 bg-maintheme1 shadow-md">
            <nav class="-mb-px flex gap-6">
              <div
                class="shrink-0 border border-transparent p-3 text-sm font-medium text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200"
              >
                <RouterLink :to="{ name: 'MyLibraryPage', params: { id: profileUser.id } }">
                  <a
                    class="shrink-0 border border-transparent px-6 py-3 font-medium text-gray-500 hover:text-gray-400 dark:text-gray-400 dark:hover:text-gray-200"
                  >
                    나의 서재
                  </a>
                </RouterLink>
                <!-- 접속자 == User 인 경우에만 보여주기  -->
                <span v-if="isMe">
                  <RouterLink :to="{ name: 'MyScrapsPage', params: { id: profileUser.id } }">
                    <a
                      class="shrink-0 border border-transparent px-6 py-3 text-sm font-medium text-gray-500 hover:text-gray-400 dark:text-gray-400 dark:hover:text-gray-200"
                    >
                      스크랩한 독후감
                    </a>
                  </RouterLink>
                  <RouterLink :to="{ name: 'MyEssayPage', params: { id: profileUser.id } }">
                    <a
                      class="shrink-0 border border-transparent px-6 py-3 text-sm font-medium text-gray-500 hover:text-gray-400 dark:text-gray-400 dark:hover:text-gray-200"
                    >
                      나의 독후감
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
  </div>
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import followingListitem from '@/components/profile/following/followingListitem.vue'
import { ref, onMounted, computed, watch } from 'vue'
import { useRouter, useRoute, onBeforeRouteUpdate } from 'vue-router'
import { profileCounterStore } from '@/stores/profilecounter'
import type { Following, User } from '@/stores/profilecounter'
import axiosInstance from '@/services/axios'
import { useChatStore } from '@/stores/chatcounter'
import Swal from 'sweetalert2'
import joinChatRoom from '@/components/chat/SellChattingBox.vue'

type Unfollowing = {
  followingId: number
}
const router = useRouter()
const route = useRoute()
const store = profileCounterStore()
const profileUser = computed(() => {
  if (store.profileUser) {
    return store.profileUser
  } else {
    return {
      id: 9999,
      profileUrl: '',
      nickname: '기본 닉네임',
      regcode: {
        id: 0,
        si: '',
        gungu: '',
        dong: ''
      },
      introduction: '기본 설명',
      tradingCnt: 0,
      exchangeCnt: 0,
      followingsCnt: 0
    }
  }
})
const followings_list = ref<Following[]>([])
// 현재 접속자와 현재 profileuser의 id 일치 여부
// 나의 팔로잉 명단 중에서 profileuser의 id가 있는지 여부
const myFollwing = ref<boolean>(false)
const loginUserId = JSON.parse(localStorage.getItem('user_info') || '').id
const BACK_API_URL = store.BACK_API_URL
const showModal = ref(false)
const unfollow_list = ref<Unfollowing[]>([])
const isMe = computed(() => {
  return profileUser.value.id == Number(loginUserId)
})

const follow = (user: User) => {
  axiosInstance
    .value({
      headers: {
        Authorization: `${store.token}`,
        'Content-Type': 'application/json'
      },

      method: 'post',
      url: `${BACK_API_URL}/profile/${loginUserId}/follow`,
      data: {
        followingId: user.id
      }
    })
    .then((response) => {
      // console.log(response.data)
      myFollwing.value = !myFollwing.value
    })
    .catch((error) => {
      console.error(error)
    })
}

const unfollow = (user: User) => {
  axiosInstance
    .value({
      headers: {
        Authorization: `${store.token}`,
        'Content-Type': 'application/json'
      },

      method: 'delete',
      url: `${BACK_API_URL}/profile/${loginUserId}/unfollow`,
      data: {
        unfollowings: [{ followingId: profileUser.value.id }]
      }
    })
    .then((response) => {
      // console.log(response.data)
      myFollwing.value = !myFollwing.value
    })
    .catch((error) => {
      console.error(error)
    })
}

const clickModal = () => {
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
}

const handleModal = () => {
  myFollwing.value = true
  showModal.value = false
}

const updateFollowings = () => {
  Swal.fire({
    title: `팔로잉 명단을 수정합니다.`,
    text: '정말 수정하시겠습니까?',
    icon: 'question',
    showDenyButton: true,
    confirmButtonText: '수정',
    denyButtonText: `취소`
  }).then((result) => {
    if (result.isConfirmed) {
      axiosInstance
        .value({
          headers: {
            Authorization: `${store.token}`,
            'Content-Type': 'application/json'
          },

          method: 'DELETE',
          url: `${BACK_API_URL}/profile/${loginUserId}/unfollow`,
          data: {
            unfollowings: unfollow_list.value
          }
        })
        .then((response) => {
          // console.log(response.data)
          router.go(0)
          Swal.fire({
            title: '팔로잉 명단이 수정되었습니다.',
            icon: 'success'
          })
        })
        .catch((error) => {
          console.error(error)
        })
      showModal.value = false
    }
  })
  // 이후 추가
}

const handlefollow = (following: Following, follow: boolean) => {
  if (!follow) {
    unfollow_list.value.push({ followingId: following.followingId })
  } else {
    unfollow_list.value = unfollow_list.value.filter(
      (user) => user.followingId != following.followingId
    )
  }
  // console.log(unfollow_list.value)
}

// button 클릭
const gotoUpdateProfile = () => {
  router.push({ name: 'ProfileUpdatePage', params: { id: profileUser.value.id } })
}
const gotoTradeList = () => {
  router.push({ name: 'MyTradeListPage', params: { id: profileUser.value.id } })
}

const gotoExchangeList = () => {
  router.push({ name: 'MyExchangeListPage', params: { id: profileUser.value.id } })
}

const gotoMychatList = () => {
  router.push({ name: 'chat', params: { id: loginUserId } })
}

const chatStore = useChatStore()
const userIdString = ref<string>(localStorage.getItem('user_info') ?? '')
// 사용자 정보를 나타내는 인터페이스 정의
interface UserInfo {
  id?: string | null
}

// userInfoString에서 가져온 값을 파싱하여 UserInfo 타입으로 사용
let userId: UserInfo

try {
  userId = JSON.parse(userIdString.value) || {} // 빈 객체로 기본값 설정
} catch (error) {
  console.error('Error parsing user_info:', error)
  userId = {} // JSON 파싱에 실패한 경우 빈 객체로 기본값 설정
}
const senderId = ref(userId.id) // 로그인한 사용자의 ID
const receiverId = ref(route.params.id) // 프로필 페이지의 사용자 ID

const gotoTradechat = async () => {
  const tradeType = ref('PURCHASE')
  const chatRoom = await chatStore.createChatRoom(
    Number(senderId.value),
    Number(receiverId.value),
    tradeType.value
  )

  if (chatRoom) {
    console.log('채팅방 생성 성공:', chatRoom)
    console.log(chatRoom.chatroomId)
    router.push({ name: 'sellchattingbox', params: { chatroomId: chatRoom.chatroomId } })
  } else {
    console.log('채팅방 생성 실패')
    console.log(senderId)
    console.log(receiverId)
  }
}

const gotoExchangechat = async () => {
  const tradeType = ref('EXCHANGE')
  const chatRoom = await chatStore.createChatRoom(
    Number(senderId.value),
    Number(receiverId.value),
    tradeType.value
  )

  if (chatRoom) {
    console.log('채팅방 생성 성공:', chatRoom)
    console.log(chatRoom.chatroomId)
    router.push({ name: 'changechattingbox', params: { chatroomId: chatRoom.chatroomId } })
  } else {
    console.log('채팅방 생성 실패')
    console.log(senderId)
    console.log(receiverId)
  }
}

onBeforeRouteUpdate((to, from) => {
  store.getProfile(Number(to.params.id))
  store.getMybookList(to.params.id as string)
  axiosInstance
    .value({
      headers: {
        Authorization: `${store.token}`
      },
      method: 'get',
      url: `${BACK_API_URL}/profile/${loginUserId}/follow`
    })
    .then((response) => {
      // console.log(response.data)
      const res = response.data
      followings_list.value = res.data['followings']
      if (
        followings_list.value.some((following) => following.followingId == profileUser.value.id)
      ) {
        myFollwing.value = true
      }
    })
    .catch((error) => {
      console.error('요청실패: ', error)
    })
})

onMounted(() => {
  store.getProfile(Number(route.params.id))
  // console.log(isMe.value)
  // // following 명단 호출
  axiosInstance
    .value({
      headers: {
        Authorization: `${store.token}`
      },
      method: 'get',
      url: `${BACK_API_URL}/profile/${loginUserId}/follow`
    })
    .then((response) => {
      // console.log(response.data)
      const res = response.data
      followings_list.value = res.data['followings']
      if (
        followings_list.value.some((following) => following.followingId == profileUser.value.id)
      ) {
        myFollwing.value = true
      }
    })
    .catch((error) => {
      console.error('요청실패: ', error)
    })
})

// 채팅방 sse 연결시도
defineExpose({
  joinChatRoom
})
</script>

<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../public/GowunDodum-Regular.ttf');
}

#profile-image {
  @apply w-[150px] h-[150px] object-cover justify-items-center rounded-[70%] border-[3px] border-solid border-[#121212];
}

#updateFollowings,
#tradechat,
#exchangechat,
#mychat,
#follow,
#profile-update {
  @apply bg-[#323F59] border text-white m-[5px] px-2.5 py-2 rounded-[10px]  hover:bg-gray-500 font-['gowun-dodum'];
}

#closeModal {
  @apply bg-[#ffffff] border border-maintheme1 text-maintheme1 m-[5px] px-2.5 py-2 rounded-[10px]  hover:bg-gray-200;
}

#follow-toggle {
  @apply w-full m-4 bg-[#323F59] text-center text-white m-[3px] px-3 py-[3px] rounded-[10px]  hover:bg-gray-500;
}

a {
  @apply text-white;
}
</style>