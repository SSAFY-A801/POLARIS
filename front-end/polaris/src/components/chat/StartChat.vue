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

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';

interface SimpleMessage {
  id: number;
  nickname: string;
  message: string;
}

export default defineComponent({
  setup() {
    const chatRoomId = ref<string | null>(null);
    const message = ref('');
    const recvList = ref<SimpleMessage[]>([]);

    const createChatRoom = async () => {
      // 서버에 새로운 채팅방 생성 요청
      const response = await fetch('http://localhost:8080/createChatRoom', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const data = await response.json();
      const roomId = data.chatRoomId;

      // 생성된 채팅방에 입장
      joinChatRoom(roomId);
    };

    const joinChatRoom = (roomId: string) => {
      // EventSource를 사용하여 SSE 연결
      const eventSource = new EventSource(`http://localhost:8080/sse/chat/${roomId}`);

      // 채팅방 ID 설정
      chatRoomId.value = roomId;

      // 새로운 메시지가 도착할 때마다 처리
      eventSource.addEventListener('message', (event) => {
        const data: SimpleMessage = JSON.parse(event.data);
        recvList.value.push(data);
      });
    };

    const sendMessage = () => {
      if (message.value.trim() !== '') {
        // 서버에 메시지 전송
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

    return {
      createChatRoom,
      chatRoomId,
      message,
      recvList,
      sendMessage,
    };
  },
});
</script>

<style scoped>

</style>