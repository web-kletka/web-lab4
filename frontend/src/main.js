import { createApp } from 'vue';
import App from './App.vue';
import route from "./route";
import {createPinia} from "pinia";

const pinia = createPinia();

createApp(App).use(route).use(pinia).mount('#app');
