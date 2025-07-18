import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';

const myapp = createApp(App);
const pinia = createPinia();
myapp.use(pinia);
myapp.mount('#app');
