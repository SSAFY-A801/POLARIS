<template>
  <div class="grid grid-cols-12 p-2 border-b items-center">
    <div class="col-span-1">
      <button v-if="token && loginUserId" @click="gotoProfile">
        <img id="profile-image" :src="props.comment.user.profileUrl" alt="" />
      </button>
      <div v-else>
        <img id="profile-image" :src="props.comment.user.profileUrl" alt="" />
      </div>
    </div>
    <div class="col-span-11">
      <div id="nickname">{{ props.comment.user.nickname }}</div>
      <div v-if="isEdited">
        <input type="text" v-model="commentContext" class="border p-1 rounded-md border-gray-300" />
      </div>
      <div v-else>{{ props.comment.comment }}</div>
      <div id="createdAt">
        {{ props.comment.createdAt.toString().split('T')[0] }}
        <div v-if="token && loginUserId">
          <button
            @click="clicktoEdit"
            id="edit-comment"
            v-if="!isEdited && props.comment.user.id == Number(loginUserId)"
          >
            수정
          </button>
          <button
            @click="editComment"
            id="edit-comment"
            v-if="isEdited && props.comment.user.id == Number(loginUserId)"
          >
            수정 완료
          </button>
          <button
            @click="cancelEdit"
            id="edit-comment"
            v-if="isEdited && props.comment.user.id == Number(loginUserId)"
          >
            취소
          </button>
          <button
            @click="deleteComment"
            id="delete-comment"
            v-if="!isEdited && props.comment.user.id == Number(loginUserId)"
          >
            삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { profileCounterStore } from '@/stores/profilecounter'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const token = localStorage.getItem('user_token')
const store = profileCounterStore()
const loginUserId = JSON.parse(localStorage.getItem('user_info') || '{}').id || null
const props = defineProps(['comment'])
const isEdited = ref(false)
const commentContext = ref(props.comment.comment)
const emit = defineEmits<{
  (e: 'editComment', id: number, user: number): void
  (e: 'deleteComment', id: number, user: number): void
}>()

const gotoProfile = () => {
  router.push({ name: 'ProfilePage', params: { id: props.comment.user.id } })
}

const clicktoEdit = () => {
  isEdited.value = !isEdited.value
}

const cancelEdit = () => {
  isEdited.value = !isEdited.value
}

const editComment = () => {
  // console.log(commentContext.value)
  axios({
    headers: {
      Authorization: `${store.token}`,
      'Content-Type': 'application/json'
    },
    method: 'patch',
    url: `${store.BACK_API_URL}/comment`,
    data: {
      id: props.comment.id,
      content: commentContext.value
    }
  })
    .then((response) => {
      // console.log(response.data)
      emit('editComment', props.comment.id, props.comment.user.id)
      isEdited.value = !isEdited.value
    })
    .catch((error) => {
      console.error(error)
    })
}

const deleteComment = () => {
  alert('댓글을 삭제합니다.')
  axios({
    headers: {
      Authorization: `${store.token}`,
      'Content-Type': 'application/json'
    },
    method: 'delete',
    url: `${store.BACK_API_URL}/comment`,
    data: {
      id: props.comment.id
    }
  })
    .then((response) => {
      // console.log(response.data)
      emit('deleteComment', props.comment.id, props.comment.user.id)
    })
    .catch((error) => {
      console.error(error)
    })
}
</script>

<style scoped>
#profile-image {
  @apply w-[50px] h-[50px] object-cover rounded-full border-2 border-gray-500;
}

#nickname,
#createdAt {
  @apply text-xs py-1;
}

#edit-comment,
#delete-comment {
  @apply text-indigo-600 ml-2;
}

button {
  height: auto;
}
</style>