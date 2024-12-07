import { createRouter, createWebHistory } from 'vue-router';
import Game from '@/components/views/game.vue';
import Start from '@/components/views/start.vue';
import Registration from "@/components/views/registration.vue";

const routes = [

    { path: '/', name: 'Start', component: Start },
    { path: '/reg', name: 'Registration', component: Registration },
    { path: '/game', name: 'Test', component: Game },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
