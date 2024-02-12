<template>
  <div>
    <div>
    <!-- <button @click="joinChatRoom">채팅방 생성 및 입장</button> -->
    <!-- <div v-if="chatRoomId">
      <h2>현재 채팅방 ID: {{ chatRoomId }}</h2>
      <input v-model="message" type="text" @keyup.enter="sendMessage" />
      <div v-for="(item, idx) in recvList" :key="idx">
        <h4>{{ item.nickname }}:</h4>
        <p>{{ item.message }}</p>
      </div>
    </div> -->
    <!-- <div>
      <input v-model="newmessage" type="text" @keyup.enter="sendMessage" />
      <div v-for="(item, idx) in recvList" :key="idx">
        <h4>{{ item.nickname }}:</h4>
        <p>{{ item.message }}</p>
      </div>
    </div> -->
  </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router'; 
import { useChatStore } from '@/stores/chatcounter';


interface SimpleMessage {
  type: "TEXT";
  chatRoomId : number;
  userId : number;
  nickname: string;
  message: string;
}

const route = useRoute();

// export default defineComponent({
//   setup() {
//     // const chatRoomId = ref<string | null>(null);
//     const chatRoomId = ref(route.params.chatroomId)
//     const newmessage = ref('');
//     const recvList = ref<SimpleMessage[]>([]);

//     // const createChatRoom = async () => {
//     //   // 서버에 새로운 채팅방 생성 요청
//     //   const response = await fetch('http://localhost:8080/chat/createChatRoom', {
//     //     method: 'POST',
//     //     headers: {
//     //       'Content-Type': 'application/json',
          
//     //     },
//     //   });

//     //   const data = await response.json();
//     //   const roomId = data.chatRoomId;

//     //   // 생성된 채팅방에 입장
//     //   joinChatRoom(roomId);
//     // };

//     // 이걸 스토어에 넣고, 프로필페이지에서 방 생성요청에서 호출하면 되지 않을까....? 그럼 굳이 
    

//     const joinChatRoom = () => {
//       // EventSource를 사용하여 SSE 연결
//       // const eventSource = new EventSource(`http://localhost:8080/chat/${roomId}`);
//       const eventSource = new EventSource(`https://i10a801.p.ssafy.io:8082/connect/1`);
//       // 채팅방 ID 설정
//       // chatRoomId.value = roomId;

//       // 새로운 메시지가 도착할 때마다 처리
//       eventSource.addEventListener('message', (event) => {
//         const data: SimpleMessage = JSON.parse(event.data);
//         recvList.value.push(data);
        
//       });
//     };

//     const sendMessage = () => {
      
//       if (newmessage.value.trim() !== '') {
//         const message: SimpleMessage = { type: "TEXT", chatRoomId: 1, userId: 1, nickname:"juhyun", message: newmessage.value.trim() };
//         // 서버에 메시지 전송
//         fetch('https://i10a801.p.ssafy.io:8082/send-message', {
//           method: 'POST',
//           headers: {
//             'Content-Type': 'application/json',
//           },
//           // body: JSON.stringify({
//           //   roomId: 1,
//           //   message: message.value,
//           // }),
//           body: JSON.stringify(message),
//         });
//         newmessage.value = '';
//       }
//     };

//     return {
//       // createChatRoom,
//       chatRoomId,
//       newmessage,
//       recvList,
//       sendMessage,
//       joinChatRoom
//     };
//   },
// });
</script>

<style scoped>

</style>