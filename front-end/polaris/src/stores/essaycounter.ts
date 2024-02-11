import { ref, computed, watch, watchEffect } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';


export type CommentUser = {
  email: string,
  id: number,
  nickname: string,
  profileUrl: string,
  region: number,
  regionString: string,
}


export type Comment = {
  comment: string,
  createdAt: Date,
  id: number,
  user: CommentUser
}


export type Essay = {
  id: number,
  title: string,
  content: string,
  hit: number,
  isOpened: string,
  createdAt: Date,
  updatedAt: Date,
  deletedAt: Date|null,
  userId: number,
  nickname: string,
  email: string,
  profileUrl: string,
  userBookId: number,
  bookCoverUrl: string,
  bookTitle: string,
  bookAuthor: string,
  comments: Comment[],
  // 그러면 여기에 댓글 목록도 줘야 하지 않을까요?
}

export const essayStore = defineStore('counter', () => {
  const token = localStorage.getItem('user_token')
  const BACK_API_URL = 'https://i10a801.p.ssafy.io:8082'


  return { token, BACK_API_URL, }
},{persist: true})
