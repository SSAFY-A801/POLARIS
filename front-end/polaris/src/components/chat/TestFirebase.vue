<template>
  <div>
    <div style="padding-top: 100px;">test firebase rtc</div>
  </div>
  <div>
    <div v-for="message in messages" :key="message.id">
      <p>{{ message.text }}</p>
    </div>
    <div class="input">
      <input type="text" style="border: 1px solid black;" v-model="newMessage" @keyup.enter="sendMessage">
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { db } from '@/main'
// import { collection, addDoc, serverTimestamp } from 'firebase/firestore'
// import { QuerySnapshot } from 'firebase/firestore'
import { collection, onSnapshot, orderBy, query, addDoc, serverTimestamp } from 'firebase/firestore'

const messages = ref<any[]>([])
const newMessage = ref('')

onMounted(async () => {
  const messagesRef = query(collection(db, 'messages'), orderBy('createdAt'))
  onSnapshot(messagesRef, (snapshot) => {
    messages.value = snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }))
    console.log(messages.value)
  })
})

const sendMessage = async () => {
  if (newMessage.value.trim() !== '') {
    await addDoc(collection(db, 'messages'), {
      text: newMessage.value,
      createdAt: serverTimestamp()
    })
    newMessage.value = ''
  }
}

</script>


<style scoped></style>