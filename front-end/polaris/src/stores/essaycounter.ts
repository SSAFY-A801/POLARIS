import { ref, computed, watch, watchEffect } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';


export type Essay = {
  id: number,
  title: string,
  content: string,
  hit: number,
  isOpened: string,
  createdAt: Date,
  updatedAt: Date,
  // 삭제 안한 경우 null인지 아니면 아예 존재하지 않는지?
  deletedAt: Date|null,
  userId: number,
  nickname: string,
  email: string,
  profileUrl: string,
  userBookId: number,
  bookCoverUrl: string,
  bookTitle: string,
  bookAuthor: string,
  // 그러면 여기에 댓글 목록도 줘야 하지 않을까요?
}

export const essayCounterStore = defineStore('counter', () => {

  return { }
},{persist: true})
