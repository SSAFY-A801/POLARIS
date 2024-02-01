import './assets/styles/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import axios from 'axios'

const app = createApp(App)

// const api = axios.create({
//     baseURL: 'http://www.aladin.co.kr/api/ItemList.aspx?ttbkey=ttbkimsw28261657007&QueryType=Bestseller&MaxResults=20&start=1&SearchTarget=Book&output=JS&Version=20131101', // Open API의 엔드포인트로 변경
//     withCredentials: true, // CORS 오류 해결을 위해 필요한 옵션
//   });

app.use(createPinia())
app.use(router)

app.config.globalProperties.axios = axios;    

app.mount('#app')

// export default api;
