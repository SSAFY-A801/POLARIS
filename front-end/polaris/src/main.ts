
import './assets/styles/main.css';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faTwitter } from '@fortawesome/free-brands-svg-icons'
import { faArrowRightArrowLeft, faBookOpen, faBookmark, faCalendarDays, faCartShopping, faCircleCheck,
   faComment, faComments,faImages, faLocationDot, faLock, faMagnifyingGlass, faNoteSticky,
    faPenToSquare, faPlus, faTrashCan, faUser, faUserSecret, faXmark } from '@fortawesome/free-solid-svg-icons';

// import VueSweetalert2 from 'vue-sweetalert2';
// import 'sweetalert2/dist/sweetalert2.min.css';

import App from './App.vue'
import router from './router'
import axios from 'axios'

library.add(faTwitter, faComment, faComments, faTrashCan, faCalendarDays,faUser, faImages,
  faXmark, faCircleCheck,faBookOpen, faCartShopping, faUserSecret, faLock, faLocationDot,
  faPenToSquare, faPlus, faMagnifyingGlass, faNoteSticky, faBookmark, faArrowRightArrowLeft)



const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)


app.use(router)
app.use(pinia)

app.config.globalProperties.axios = axios;    


app.component('font-awesome-icon',FontAwesomeIcon)
// app.use(VueSweetalert2)

app.mount('#app')


