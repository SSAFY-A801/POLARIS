<template>
  <div class="container border mx-auto mt-24 max-w-6xl min-w-[700px] bg-backgroundgray p-5">
    <button id="back" @click="goback()" >BACK</button>
    <!-- 최상단 : 독후감 제목 & 프로필 -->
    <div class="flex justify-between items-center p-5 m-1 border-b  border-black">
      <div class="flex items-center">
        <span class="bg-maintheme1 text-white text-sm rounded-xl px-2 py-1 m-2">독후감</span>
        <div class="text-xl font-semibold">{{ essay?.title }}</div>
      </div>
      <div class="flex items-center">
        <button><img id="profile-image" :src="essay?.profileUrl" alt=""></button>
        <div id="nickname">{{ essay?.nickname }}</div>
      </div>
    </div>
    <!-- 독후감 정보 -->
    <div class="p-10">
      <!-- 도서 정보 -->
      <div class="relative grid grid-cols-12 gap-4 shadow-slate-500 shadow-md rounded-xl p-3">
        <div class="col-span-3 flex justify-center">
          <img id="book-image" :src="essay?.bookCoverUrl" alt="">
        </div>
        <div class="col-span-9">
          <div class="text-lg font-bold mb-2">{{ essay?.bookTitle }}</div>
          <div class="mb-2">
            저자 {{ essay?.bookAuthor }}
          </div>
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
      <div class="flex justify-end border-b border-gray-400 mb-4">
        <div v-if="isMe">
          <button @click="deleteEssay" id="delete-essay">삭제</button>
          <button @click="editEssay" id="edit-essay">수정</button>
        </div>
        <div v-else>
          <button id="scrap-essay">스크랩</button>
        </div>
      </div>
      <!-- 댓글 작성 -->
      <div class="flex items-center">
        <div class="font-bold mr-4">4개의 댓글</div>
        <input v-model="comment" type="text" id="book"  class="w-2/3 rounded-lg appearance-none border border-gray-500 py-2 px-4 m-2 bg-gray-50 text-maintheme1 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-gray-600 focus:border-transparent" placeholder="댓글 추가"/>
        <button id="add-comment" @click="addComment(comment)" type="button" class=" text-white bg-maintheme1 hover:bg-gray-500 py-2  px-6 ml-4 font-bold rounded-md">
          등록
        </button>
      </div>
      <!-- 댓글 목록 -->
      <div>
        이런저런 댓글들 CommentList
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { computed, onMounted, ref } from 'vue';
  import { useRouter, useRoute } from 'vue-router';
  import axios from 'axios';
  import type { Essay } from '@/stores/essaycounter';
  import { essayStore } from '@/stores/essaycounter';
  import Editor from '@/components/essay/TiptapEditor.vue';

  const scraps = ref<number>(0)
  const store = essayStore();
  const essay = ref<Essay|null>(null)
  const route = useRoute();
  const router  = useRouter();
  const comment = ref("")
  const isMe = computed(()=> {
    return essay.value?.userId ==  Number(store.loginUser.id)
  })
  const addComment = (comment:string) => {
    alert("댓글 작성")
  }

  const editEssay = () => {
    if(essay.value){
      router.push({name: 'essayedit', params:{essayId: essay.value.id} })
    }
  }
  
  const deleteEssay = () => {
    alert("정말 삭제하실건가요?")
    axios({
    headers: {
      Authorization: `${store.token}`,
      "Content-Type": 'application/json'
    },
    method: 'delete',
    url: `${store.BACK_API_URL}/essay`,
    data: {
      id: essay.value?.id
    }
  })
  .then((response)=> {
    console.log(response.data)
  })
  .catch((error)=> {
    console.error(error)
  })
    alert("독후감을 삭제합니다.")
    router.push({name: 'essaylist'})
  }

  const goback = () => {
  router.push({name: 'essaylist'})
}

onMounted(()=> {
  console.log(isMe.value)
  axios({
    headers: {
      Authorization: `${store.token}`,
      "Content-Type": 'application/json'
    },
    method: 'get',
    url: `${store.BACK_API_URL}/essay/${route.params.essayId}`
  })
  .then((response)=> {
    console.log(response.data)
    essay.value = response.data.data
  })
  .catch((error)=> {
    console.error(error);
    
  })
  // 스크랩 수 조회
  axios({
      headers: {
        "Content-Type": 'application/json'
      },
      method: 'get',
      url: `${store.BACK_API_URL}/essay/scrap_count/${route.params.essayId}`
    })
    .then((response)=> {
      console.log(response.data)
      scraps.value = response.data.data;
    })
    .catch((error)=> {
      console.error(error);
      
    })
})
</script>

<style scoped>
  #nickname {
    @apply font-bold text-lg
  }

  #profile-image {
    @apply w-[60px] h-[60px] object-cover mx-3 shadow-md justify-items-center rounded-[70%] border;
  }

  #book-image {
    width: 150px;
  }


  #delete-essay {
  @apply bg-red-600 text-white border-2 border-red-800 px-2 py-1 m-2 rounded-lg hover:bg-red-700
}

  #edit-essay,
  #scrap-essay {
    @apply bg-white font-bold text-maintheme1 border-maintheme1 border-2 px-2 py-1 m-2 rounded-lg hover:bg-slate-200
  }

  #back,
  #add-comment {
    @apply text-white bg-maintheme1 hover:bg-gray-500 py-2  px-4 ml-4 font-bold rounded-md
  }
  #editor-button {
  @apply m-1 px-2 py-1 border-2 border-maintheme1 rounded-md font-bold
}

</style>