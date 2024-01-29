import './assets/styles/main.css';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faTwitter } from '@fortawesome/free-brands-svg-icons'
import { faCartShopping, faMagnifyingGlass, faPenToSquare, faPlus, faTrashCan, faUserSecret } from '@fortawesome/free-solid-svg-icons';

import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';

import App from './App.vue'
import router from './router'

library.add(faTwitter, faTrashCan, faCartShopping, faUserSecret,faPenToSquare, faPlus, faMagnifyingGlass)



const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)


app.use(router)
app.use(pinia)

app.component('font-awesome-icon',FontAwesomeIcon)
app.use(VueSweetalert2)
app.mount('#app')
