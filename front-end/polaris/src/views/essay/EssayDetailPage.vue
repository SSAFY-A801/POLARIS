<template>
  <Navvar></Navvar>
  <div class="container mx-auto mt-64 max-w-6xl min-w-[700px] p-5 font-[gowun-dodum]">
    <button id="back" @click="goback()">BACK</button>
    <!-- 최상단 : 독후감 제목 & 프로필 -->
    <div class="flex justify-between items-center p-5 m-1 border-b-2 border-gray-300">
      <div class="flex items-center">
        <span class="bg-red-600 text-white text-sm rounded-xl px-2 py-1 m-2">독후감</span>
        <div class="text-xl font-semibold">{{ essay?.title }}</div>
      </div>
      <div class="flex items-center">
        <button @click="gotoProfile">
          <img id="profile-image" :src="essay?.profileUrl" alt="" />
        </button>
        <div id="nickname">{{ essay?.nickname }}</div>
      </div>
    </div>
    <!-- 독후감 정보 -->
    <div class="p-10">
      <!-- 도서 정보 -->
      <div class="relative grid grid-cols-12 gap-4 shadow-slate-500 shadow-md rounded-xl p-3">
        <div class="col-span-3 flex justify-center">
          <img id="book-image" :src="essay?.bookCoverUrl" alt="" />
        </div>
        <div class="col-span-9">
          <div class="text-lg font-bold mb-2">{{ essay?.bookTitle }}</div>
          <div class="mb-2">저자 {{ essay?.bookAuthor }}</div>
          <!-- 조회수 & 스크랩 & 작성일 -->
          <div class="absolute bottom-1 right-2 flex justify-end gap-5 p-3">
            <div>조회수 {{ essay?.hit }}</div>
            <div>스크랩 {{ scraps }}</div>
            <div>{{ essay?.createdAt.toString().split('T')[0] }}</div>
          </div>
        </div>
      </div>
      <!-- 독후감 내용 -->
      <div v-html="essay?.content" class="mt-10 prose"></div>
      <div v-if="token" class="flex justify-end border-b border-gray-400 mb-4">
        <div v-if="isMe">
          <button @click="deleteEssay" id="delete-essay">삭제</button>
          <button @click="editEssay" id="edit-essay">수정</button>
        </div>
        <div v-else>
          <button v-if="scrap" id="scrap-essay" @click="scrapEssay">
            <font-awesome-icon icon="fa-solid fa-bookmark" style="color: #323f59" />
            스크랩 취소
          </button>
          <button v-else id="scrap-essay" @click="scrapEssay">
            <font-awesome-icon icon="fa-solid fa-bookmark" style="color: #323f59" />
            스크랩
          </button>
        </div>
      </div>
      <!-- 댓글 작성 -->
      <div class="flex items-center">
        <div class="font-bold mr-4">{{ essay?.comments.length || 0 }} 개의 댓글</div>
        <input
          v-if="token"
          v-model="commentContext"
          type="text"
          id="book"
          class="w-2/3 rounded-lg appearance-none border border-gray-500 py-2 px-4 m-2 bg-gray-50 text-maintheme1 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-gray-600 focus:border-transparent"
          placeholder="댓글 추가"
        />
        <button
          v-if="token"
          id="add-comment"
          @click="addComment(commentContext)"
          type="button"
          class="text-white bg-maintheme1 hover:bg-gray-500 py-2 px-6 ml-4 font-bold rounded-md"
        >
          등록
        </button>
      </div>
      <!-- 댓글 목록 -->
      <div>
        <CommentListItem
          v-for="(comment, index) in essay?.comments"
          :key="index"
          :comment="comment"
          @edit-comment="getEssayInfo()"
          @delete-comment="getEssayInfo()"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { computed, onMounted, ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import axiosInstance from '@/services/axios'
import type { Essay, ScrapPost } from '@/stores/essaycounter'
import { profileCounterStore } from '@/stores/profilecounter'
import CommentListItem from '@/components/essay/comment/CommentListItem.vue'
import Swal from 'sweetalert2'

const scraps = ref<number>(0)
const profileStore = profileCounterStore()
const token = ref(localStorage.getItem('user_token'))
const BACK_API_URL = profileStore.BACK_API_URL
const essay = ref<Essay | null>(null)
const route = useRoute()
const router = useRouter()
const loginUserId = JSON.parse(localStorage.getItem('user_info') || '{}').id || null
const myscraps = ref<ScrapPost[]>([])
const scrap = ref(false)

const commentContext = ref('')
const isMe = computed(() => {
  return essay.value?.userId == Number(loginUserId)
})

// 프로필로 가기
const gotoProfile = () => {
  router.push({ name: 'ProfilePage', params: { id: essay.value?.userId } })
}

watch(
  () => essay.value?.comments,
  (newComments) => {
    // 댓글이 변경될 때마다 수행할 로직 작성
  }
)

// 댓글추가
const addComment = (comment: string) => {
  if (essay.value && comment.length) {
    axiosInstance
      .value({
        headers: {
          Authorization: `${token.value}`,
          'Content-Type': 'application/json'
        },
        method: 'post',
        url: `${BACK_API_URL}/comment`,
        data: {
          essayId: essay.value.id,
          content: comment
        }
      })
      .then((response) => {
        // console.log(response.data)
        commentContext.value = ''
        // 독후감 정보 갱신
        axiosInstance
          .value({
            headers: {
              Authorization: `${token.value}`,
              'Content-Type': 'application/json'
            },
            method: 'get',
            url: `${BACK_API_URL}/essay/${route.params.essayId}`
          })
          .then((response) => {
            essay.value = response.data.data
          })
          .catch((error) => {
            console.error(error)
          })
      })
      .catch((error) => {
        console.error(error)
      })
  } else {
    Swal.fire({
      title: '내용을 작성해 주세요.',
      icon: 'error'
    })
  }
}

// 스크랩
const scrapEssay = () => {
  if (essay.value) {
    axiosInstance
      .value({
        headers: {
          Authorization: `${token.value}`,
          'Content-Type': 'application/json'
        },
        method: 'put',
        url: `${BACK_API_URL}/essay/${essay.value.id}`
      })
      .then((response) => {
        // console.log(response.data)
        scrap.value = !scrap.value
      })
      .catch((error) => {
        console.error(error)
      })
  }
}

// 독후감 수정
const editEssay = () => {
  if (essay.value) {
    router.push({ name: 'essayedit', params: { essayId: essay.value.id } })
  }
}

// 독후감 삭제
const deleteEssay = () => {
  Swal.fire({
    title: `독후감을 삭제합니다.`,
    text: '정말 삭제하시겠습니까?',
    icon: 'question',
    showDenyButton: true,
    confirmButtonText: '삭제',
    denyButtonText: `취소`
  }).then((result) => {
    if (result.isConfirmed) {
      axiosInstance
        .value({
          headers: {
            Authorization: `${token.value}`,
            'Content-Type': 'application/json'
          },
          method: 'delete',
          url: `${BACK_API_URL}/essay`,
          data: {
            id: essay.value?.id
          }
        })
        .then((response) => {
          // console.log(response.data)
          Swal.fire({
            title: '독후감이 삭제되었습니다.',
            icon: 'success'
          })
          router.push({ name: 'essaylist' })
        })
        .catch((error) => {
          console.error(error)
        })
    }
  })
}

const goback = () => {
  router.push({ name: 'essaylist' })
}

const getEssayInfo = async () => {
  try {
    // 1. essay 정보 가져오기
    const essayResponse = await axiosInstance.value({
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'get',
      url: `${BACK_API_URL}/essay/${route.params.essayId}`
    })
    // console.log(essayResponse.data);
    essay.value = essayResponse.data.data

    // 2. loginUserId가 있다면 scraps 정보 가져오기
    if (loginUserId) {
      const scrapsResponse = await axiosInstance.value({
        headers: {
          Authorization: `${token.value}`,
          'Content-Type': 'application/json'
        },
        method: 'get',
        url: `${BACK_API_URL}/essay/${loginUserId}/scraps`
      })
      // console.log(scrapsResponse.data);
      const res = scrapsResponse.data.data
      myscraps.value = res ? res.scrapPosts : []
    }
    // 3. essay 정보와 scraps 정보 비교
    if (myscraps.value.some((scrap) => scrap.essayId == essay.value?.id)) {
      scrap.value = true
    }
    // console.log('스크랩 여부:', scrap.value);
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  getEssayInfo()

  // 스크랩 수 조회
  axiosInstance
    .value({
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'get',
      url: `${BACK_API_URL}/essay/scrap_count/${route.params.essayId}`
    })
    .then((response) => {
      // console.log(response.data)
      scraps.value = response.data.data
    })
    .catch((error) => {
      console.error(error)
    })
})
</script>

<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../../public/GowunDodum-Regular.ttf');
}

#nickname {
  @apply font-bold text-lg;
}

#profile-image {
  @apply w-[60px] h-[60px] object-cover mx-3 shadow-md justify-items-center rounded-[70%] border;
}

#book-image {
  width: 150px;
}

#delete-essay {
  @apply bg-red-600 text-white border-2 border-red-800 px-2 py-1 m-2 rounded-lg hover:bg-red-700;
}

#edit-essay,
#scrap-essay {
  @apply bg-white font-bold text-maintheme1 border-maintheme1 border-2 px-2 py-1 m-2 rounded-lg hover:bg-slate-200;
}

#back,
#add-comment {
  @apply text-white bg-maintheme1 hover:bg-gray-500 py-2  px-4 ml-4 font-bold rounded-md;
}
#editor-button {
  @apply m-1 px-2 py-1 border-2 border-maintheme1 rounded-md font-bold;
}
</style>