<template>
<body>
  <div class="first-container">
<div>
    유저이름:
    <input v-model="nickname" type="text" />
    내용: <input v-model="message" type="text" @keyup="sendMessage" />
    <div v-for="(item, idx) in recvList" :key="idx">
      <h3>유저이름: {{ item.nickname }}</h3>
      <h3>내용: {{ item.message }}</h3>
    </div>
  </div>
</div>
</body>
</template>

<script lang="ts">
// import { Vue } from 'vue-class-component';
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';
// import { useChatStore } from '@/stores/chatcounter';
import { ref, onMounted } from 'vue';

interface Message {
  type: string;
  chatRoomId: number;
  userId: number;
  nickname: string;
  message: string;
}

export default {
  setup() {
    const type = ref('');
    const chatRoomId = ref(0);
    const userId = ref(0);
    const nickname = ref('');
    const message = ref('');
    const recvList = ref<Message[]>([]);
    let stompClient : any = null;
    const connected = ref(false);

    const connect = () => {
      const serverURL = `${import.meta.env.VITE_API_KEY}/stomp/chat`;
      let socket = new SockJS(serverURL);
      stompClient = Stomp.over(socket);
      console.log(stompClient)

      stompClient.connect(
        {},
        (frame: any) => {
          connected.value = true;
          console.log('connected:' + frame);
          stompClient.subscribe(`/sub/chat/room/${chatRoomId.value}`, (res: { body: string }) => {
            recvList.value.push(JSON.parse(res.body));
          });
        },
        (error: any) => {
          console.log('연결 실패' + JSON.stringify(error))
          connected.value = false;
          
        }
      );        
    };

    const send = () => {
      if (stompClient && connected.value) {
        const msg: Message = {
          type : 'TEXT',
          chatRoomId : chatRoomId.value,
          userId : userId.value,
          nickname: nickname.value,
          message: message.value
        };
        stompClient.send(`/pub/chat/message/${chatRoomId.value}`, JSON.stringify(msg), {});
      }
    };

    const sendMessage = (e: KeyboardEvent) => {
      if(e.key === 'Enter' && nickname.value !== '' && message.value !== ''){
        send();
        message.value = '';
      }
    };

    onMounted(connect);

    return {
      type,
      chatRoomId,
      userId,
      nickname,
      message,
      recvList,
      connected,
      sendMessage
    };
  }
};

// interface Message {
//   type: string;
//   chatRoomId: number;
//   userId: number;
//   nickname: string;
//   message: string;
// }


// export default class App extends Vue {
//   type: string = '';
//   chatRoomId!: number;
//   userId!: number;
//   nickname: string = '';
//   message: string = '';
//   recvList: Message[] = [];
//   stompClient: any = null;
//   connected: boolean = false;

//   created() {
//     this.connect();
//   }

//   sendMessage(e: KeyboardEvent) {
//   if(e.key === 'Enter' && this.nickname !== '' && this.message !== ''){
//     this.send();
//     this.message = '';
//   }
// }

//   send() {
//     console.log("Send message:" + this.message);
//     if (this.stompClient && this.stompClient.connected) {
//       const msg: Message = {
//         type : 'TEXT',
//         chatRoomId : this.chatRoomId,
//         userId : this.userId,
//         nickname: this.nickname,
//         message: this.message
//         // 
//       };
//       this.stompClient.send(`/pub/chat/message/${this.chatRoomId}`, JSON.stringify(msg), {});
//     }
//   }

//   connect() {
//     const serverURL = "https://i10a801.p.ssafy.io:8082/stomp/chat";
//     let socket = new SockJS(serverURL);
//     this.stompClient = Stomp.over(socket);
//     console.log(this.stompClient);
    
//     console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
//     this.stompClient.connect(
//       {
//         // 'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoamhAZ21haWwuY29tIiwiYXV0aCI6IkFVVEhPUklUWSIsImlkIjo4LCJlbWFpbCI6ImhqaEBnbWFpbC5jb20iLCJuaWNrbmFtZSI6Iu2CueynhO2VmCIsImV4cCI6MTcyNDY4Nzg5NH0.RGSg_mX4rSNrHAIIBkfHg1AowDKwyAmzhnk2b7X8xaE'
//       },
//       (frame: any) => {
//         this.connected = true;
//         console.log('소켓 연결 성공', frame);
//         this.stompClient.subscribe(`/sub/chat/room/${this.chatRoomId}`, (res: { body: string }) => {
//           console.log('구독으로 받은 메시지 입니다.', res.body);
//           this.recvList.push(JSON.parse(res.body));
//         });
//       },
//       (error: any) => {
//         console.log('소켓 연결 실패', error);
//         this.connected = false;
//       }
//     );        
//   }
// }

// 
// interface Message {
//   type: string;
//   chatRoomId: number;
//   userId: number;
//   nickname: string;
//   message: string;
// }

// export default class App extends Vue {
//   type: string = '';
//   chatRoomId!: number;
//   userId!: number;
//   nickname: string = '';
//   message: string = '';
//   recvList: Message[] = [];
//   stompClient: any = null;
//   connected: boolean = false;

//   mounted() {
//     this.connect();
//   }

//   sendMessage(e: KeyboardEvent) {
//     if (e.key === 'Enter' && this.nickname !== '' && this.message !== '') {
//       this.send();
//       this.message = '';
//     }
//   }

//   send() {
//     console.log("Send message:" + this.message);
//     if (this.stompClient && this.stompClient.connected) {
//       const msg: Message = {
//         type: 'TEXT',
//         chatRoomId: this.chatRoomId,
//         userId: this.userId,
//         nickname: this.nickname,
//         message: this.message
//       };
//       this.stompClient.send(`/pub/chat/message/${this.chatRoomId}`, JSON.stringify(msg), {});
//     }
//   }

//   connect() {
//     const serverURL = "https://i10a801.p.ssafy.io:8082/stomp/chat";
//     let socket = new SockJS(serverURL);
//     this.stompClient = Stomp.over(socket);
//     console.log(this.stompClient);
    
//     console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
//     this.stompClient.connect(
//       {},
//       (frame: any) => {
//         this.connected = true;
//         console.log('소켓 연결 성공', frame);
//         this.stompClient.subscribe(`/sub/chat/room/${this.chatRoomId}`, (res: { body: string }) => {
//           console.log('구독으로 받은 메시지 입니다.', res.body);
//           this.recvList.push(JSON.parse(res.body));
//         });
//       },
//       (error: any) => {
//         console.log('소켓 연결 실패', error);
//         this.connected = false;
//       }
//     );        
//   }
// }
</script>


<style scoped>
.first-container {
  padding-top: 50px;
}
</style>