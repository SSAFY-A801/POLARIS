
import './assets/styles/main.css';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import { createApp } from 'vue' 
import { createPinia } from 'pinia'
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faTwitter } from '@fortawesome/free-brands-svg-icons'
import { faArrowRightArrowLeft, faBookOpen, faBookmark, faCalendarDays, faCartShopping, faCircleCheck, faCircleUser,
   faComment, faComments,faFloppyDisk, faImages, faLocationDot, faLock, faMagnifyingGlass, faNoteSticky,
    faPenToSquare, faPlus, faTrashCan, faUser, faUserSecret, faXmark,  faChevronLeft, faChevronRight,
    faArrowRightFromBracket,
    faBold,
    faItalic,
    faStrikethrough,
    faList,
    faListOl,
    faRotateLeft,
    faRotateRight,
    faCode,
    faQuoteRight,
    faArrowsDownToLine,
    faUserPlus, faArrowRightToBracket, faCrown, faHeart, faFilter } from '@fortawesome/free-solid-svg-icons';



import SockJS from 'sockjs-client'
import Stomp from 'stompjs';
import App from './App.vue'
import router from './router'
import axios from 'axios'
// import dotenv from 'dotenv';

library.add(faArrowRightArrowLeft, faBookOpen, faBookmark, faCalendarDays, faCartShopping, faCircleCheck, faCircleUser,
  faComment,faRotateLeft, faArrowsDownToLine, faCode, faQuoteRight, faRotateRight, faBold, faItalic, faStrikethrough, faList, faListOl, faComments,faFloppyDisk, faImages, faLocationDot, faLock, faMagnifyingGlass, faNoteSticky,
   faPenToSquare, faPlus, faTrashCan, faUser, faUserSecret, faXmark,  faChevronLeft, faChevronRight, faArrowRightFromBracket,
  faComment, faComments,faFloppyDisk, faImages, faLocationDot, faLock, faMagnifyingGlass, faNoteSticky,
   faPenToSquare, faPlus, faTrashCan, faUser, faUserSecret, faXmark,  faChevronLeft, faChevronRight, faArrowRightFromBracket, faUserPlus, faArrowRightToBracket, faCrown, faPenToSquare,
   faHeart, faMagnifyingGlass, faLocationDot, faFilter )


// firebase 사용을 위한 초기화
import { initializeApp } from "firebase/app";
import { getFirestore, collection, getDocs, Firestore } from 'firebase/firestore';

//firebase SDK
const firebaseConfig = {
  apiKey: "AIzaSyBntfmsGNVOJya9XShuGuah_nmDz3rp1H0",
  authDomain: "polaris-firebase-chat.firebaseapp.com",
  projectId: "polaris-firebase-chat",
  storageBucket: "polaris-firebase-chat.appspot.com",
  messagingSenderId: "516447847977",
  appId: "1:516447847977:web:0734c7997b4c1157c8fe38"
};
const firebaseapp = initializeApp(firebaseConfig);
const db = getFirestore(firebaseapp);

// 예시
export {db}

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate);


app.use(router)
app.use(pinia)

app.config.globalProperties.axios = axios;    

const sock = new SockJS("http://localhost:8080")
const stompClient = Stomp.over(sock);

app.config.globalProperties.$sock = sock;
app.config.globalProperties.$stomp = stompClient;

app.component('font-awesome-icon',FontAwesomeIcon)
app.mount('#app')


