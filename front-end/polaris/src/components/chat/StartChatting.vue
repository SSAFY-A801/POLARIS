<template>
  <div>
    <div>
    <button @click="createChatRoom">채팅방 생성 및 입장</button>
    <div v-if="chatRoomId">
      <h2>현재 채팅방 ID: {{ chatRoomId }}</h2>
      <input v-model="message" type="text" @keyup.enter="sendMessage" />
      <div v-for="(item, idx) in recvList" :key="idx">
        <h4>{{ item.nickname }}:</h4>
        <p>{{ item.message }}</p>
      </div>
    </div>
  </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

interface SimpleMessage {
  id: number;
  nickname: string;
  message: string;
}

const chatRoomId = ref<string | null>(null);
const message = ref('');
const recvList = ref<SimpleMessage[]>([]);

const createChatRoom = async () => {
  const response = await fetch('http://localhost:8080/createChatRoom', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
  });

  const data = await response.json();
  const roomId = data.chatRoomId;

  joinChatRoom(roomId);
};

const joinChatRoom = (roomId: string) => {
  const eventSource = new EventSource(`http://localhost:8080/sse/chat/${roomId}`);

  chatRoomId.value = roomId;

  eventSource.addEventListener('message', (event) => {
    const data: SimpleMessage = JSON.parse(event.data);
    recvList.value.push(data);
  });
};

const sendMessage = () => {
  if (message.value.trim() !== '') {
    fetch('http://localhost:8080/sendMessage', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        roomId: chatRoomId.value,
        message: message.value,
      }),
    });
    message.value = '';
  }
};
</script>

<style scoped>

</style>