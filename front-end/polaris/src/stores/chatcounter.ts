import { ref, computed} from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios';
import axiosInstance from '@/services/axios';
import { convertTypeAcquisitionFromJson } from 'typescript';


export interface Chatroom {
  chatRoomId: number;
  receiverId: number;
  nickname: string;
  profileUrl: string;
  tradeStatus: string;
  tradeType: string;
}

export interface ResponseData {
  userId: number;
  chatRoomList: Chatroom[];
}

export interface ApiResponse {
  status: number;
  message: string;
  data: ResponseData;
}


// 채팅방 생성 시 POST 요청(상대방 프로필에서 교환채팅/구매채팅 클릭 시의 api 요청)
// 채팅방 생성 인터패이스(이때 반환되는 데이터에 상대방 닉네임, 나의 닉네임 있어야 할듯?)
export interface CreateChatroom {
  id: number;
  senderId: number;
  receiverId: number;
  tradeType: string;
  chatroomId: number;
}

export interface CreateChatroomResponse {
  status: number;
  message: string;
  data: CreateChatroom;
}

interface SimpleMessage {
  type: "TEXT";
  chatRoomId : number;
  userId : number;
  nickname: string;
  message: string;
}

export const useChatStore = defineStore('chat', () => {
  const token = ref(localStorage.getItem('user_token'))
  const chatData = ref<ResponseData | null>(null);
  // const chatRoomId = ref<number | null>(null);
  const fetchChatInfo = async () => {
    try {
      const response = await axiosInstance.value.get<ApiResponse>(`${import.meta.env.VITE_API_KEY}/chatroom`, {
        headers: {
          // 'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoamhAZ21haWwuY29tIiwiYXV0aCI6IkFVVEhPUklUWSIsImlkIjo4LCJlbWFpbCI6ImhqaEBnbWFpbC5jb20iLCJuaWNrbmFtZSI6Iu2CueynhO2VmCIsImV4cCI6MTcyNDY4Nzg5NH0.RGSg_mX4rSNrHAIIBkfHg1AowDKwyAmzhnk2b7X8xaE',
          'Authorization': token.value?.replace("\"", "")
          
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

  const createChatRoom = async (senderId: number, receiverId: number, tradeType: string) => {
    try {
      const token = ref(localStorage.getItem('user_token'))
      const response = await axiosInstance.value.post<CreateChatroomResponse>(`${import.meta.env.VITE_API_KEY}/chatroom`, {
        senderId,
        receiverId,
        tradeType,
      }, {
        headers: {
          'Authorization': token.value?.replace("\"", ""),
        }
      });
  
      if (response.status === 201) {
        console.log('채팅방 생성:', response.data);
        // 반환되는 chatroomId 값을 저장하고 싶어
        return response.data.data;
      } else {
        console.error('API 요청 실패:', response.status);
        return null;
      }
    } catch (error) {
      console.error('API 요청 중 오류 발생:', error);
      return null;
    }
  };


  return {
    token,
    chatData,
    fetchChatInfo,
    createChatRoom,
    // joinChatRoom
    // deleteChat
  };
});