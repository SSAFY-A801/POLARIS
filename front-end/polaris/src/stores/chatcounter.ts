import { ref, computed} from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios';
import { convertTypeAcquisitionFromJson } from 'typescript';



// export interface Chatroom {
//   chatRoomId: number;
//   receiverId: number;
//   nickname: string;
//   profileUrl: string;
//   tradeStatus: string;
//   tradeType: string;
// }

// export interface ResponseData {
//   userId: number;
//   chatRoomList: Chatroom[];
// }

// export interface ApiResponse {
//   status: number;
//   message: string;
//   data: ResponseData;
// }


// export const useChatStore = defineStore('chat', {
//   state: (): { chatData: ResponseData | null } => ({
//     chatData: null,
//   }),
//   actions: {
//     async fetchChatInfo() {
//       try {
//         const response = await axios.get<ResponseData>('http://i10a801.p.ssafy.io:8082/chat', {
//           headers: {
//             'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoamhAZ21haWwuY29tIiwiYXV0aCI6IkFVVEhPUklUWSIsImlkIjo4LCJlbWFpbCI6ImhqaEBnbWFpbC5jb20iLCJuaWNrbmFtZSI6Iu2CueynhO2VmCIsImV4cCI6MTcyNDY4Nzg5NH0.RGSg_mX4rSNrHAIIBkfHg1AowDKwyAmzhnk2b7X8xaE',
//           }
//         });
//         if (response.status === 200) {
//           this.chatData = response.data;
//           console.log(response.data);
//         } else {
//           console.error('API 요청 실패:', response.status);
//         }
//       } catch (error) {
//         console.error('API 요청 중 오류 발생:', error);
//       }
//     }
//   },
// });

interface Chatroom {
  chatRoomId: number;
  receiverId: number;
  nickname: string;
  profileUrl: string;
  tradeStatus: string;
  tradeType: string;
}

interface ResponseData {
  userId: number;
  chatRoomList: Chatroom[];
}

interface ApiResponse {
  status: number;
  message: string;
  data: ResponseData;
}

export const useChatStore = defineStore('chat', () => {

  const chatData = ref<ResponseData | null>(null);

  const fetchChatInfo = async () => {
    try {
      const response = await axios.get<ApiResponse>('https://i10a801.p.ssafy.io:8082/chat', {
        headers: {
          'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoamhAZ21haWwuY29tIiwiYXV0aCI6IkFVVEhPUklUWSIsImlkIjo4LCJlbWFpbCI6ImhqaEBnbWFpbC5jb20iLCJuaWNrbmFtZSI6Iu2CueynhO2VmCIsImV4cCI6MTcyNDY4Nzg5NH0.RGSg_mX4rSNrHAIIBkfHg1AowDKwyAmzhnk2b7X8xaE',
        }
      });
      if (response.status === 200) {
        chatData.value = response.data.data;
        console.log(response.data.data)
      } else {
        console.error('API 요청 실패:', response.status);
      }
    } catch (error) {
      console.error('API 요청 중 오류 발생:', error);
    }
  };

  return {
    chatData,
    fetchChatInfo
  };
});