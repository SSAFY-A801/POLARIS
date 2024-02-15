declare module 'sockjs-client';

declare module "global" {
  export interface ResponseData {
    userId: number;
    chatRoomList: Chatroom[];
  }

  export interface Chatroom {
    chatRoomId: number;
    receiverId: number;
    nickname: string;
    profileUrl: string;
    tradeStatus: string;
    tradeType: string;
  }
}

