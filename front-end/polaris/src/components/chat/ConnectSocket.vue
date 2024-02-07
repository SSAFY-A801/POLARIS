<template>

<body>
  <div class="first-container">
<div id="app">
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
import { Vue } from 'vue-class-component';
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';



interface Message {
  type: string;
  chatRoomId: number;
  userId: number;
  nickname: string;
  message: string;
}


export default class App extends Vue {
  type: string = '';
  chatRoomId!: number;
  userId!: number;
  nickname: string = '';
  message: string = '';
  recvList: Message[] = [];
  stompClient: any = null;
  connected: boolean = false;

  created() {
    this.connect();
  }

  sendMessage(e: KeyboardEvent) {
  if(e.key === 'Enter' && this.nickname !== '' && this.message !== ''){
    this.send();
    this.message = '';
  }
}

  send() {
    console.log("Send message:" + this.message);
    if (this.stompClient && this.stompClient.connected) {
      const msg: Message = {
        type : 'TEXT',
        chatRoomId : this.chatRoomId,
        userId : this.userId,
        nickname: this.nickname,
        message: this.message
        // 
      };
      this.stompClient.send(`/pub/chat/message/${this.chatRoomId}`, JSON.stringify(msg), {});
    }
  }

  connect() {
    const serverURL = "https://i10a801.p.ssafy.io:8082/stomp/chat";
    let socket = new SockJS(serverURL);
    this.stompClient = Stomp.over(socket);
    console.log(this.stompClient);
    
    console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
    this.stompClient.connect(
      {
        // 'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoamhAZ21haWwuY29tIiwiYXV0aCI6IkFVVEhPUklUWSIsImlkIjo4LCJlbWFpbCI6ImhqaEBnbWFpbC5jb20iLCJuaWNrbmFtZSI6Iu2CueynhO2VmCIsImV4cCI6MTcyNDY4Nzg5NH0.RGSg_mX4rSNrHAIIBkfHg1AowDKwyAmzhnk2b7X8xaE'
      },
      (frame: any) => {
        this.connected = true;
        console.log('소켓 연결 성공', frame);
        this.stompClient.subscribe(`/sub/chat/room/${this.chatRoomId}`, (res: { body: string }) => {
          console.log('구독으로 받은 메시지 입니다.', res.body);
          this.recvList.push(JSON.parse(res.body));
        });
      },
      (error: any) => {
        console.log('소켓 연결 실패', error);
        this.connected = false;
      }
    );        
  }
}

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