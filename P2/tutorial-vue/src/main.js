import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import './assets/main.css'

const myapp = createApp(App)
const pinia = createPinia()
myapp.use(pinia)
myapp.mount('#app')
createApp(App).mount('#app')