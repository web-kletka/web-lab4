import { createApp } from 'vue';
import App from './App.vue';
import route from "./route";
import {createPinia} from "pinia";
import { useUserStore } from './stores/user.js'; // Подключение хранилища

const pinia = createPinia();

createApp(App).use(route).use(pinia).mount('#app');
useUserStore().loadFromSession();
