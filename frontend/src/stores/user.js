
import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('user', {
    state: () => ({
        success: true,
        result_message: '',
        currentUser: null
    }),
    actions: {

        async login(userData) {
            await axios
                .post(
                    'http://localhost:8080/api/login',
                    userData,
                    {
                        headers: {'Content-Type': 'application/json'}
                    })
                .then((response) => {
                    this.result_message = response.data.message;
                    if (response.data.success)
                        this.currentUser = response.data.user
                    this.success = response.data.success;
                    sessionStorage.setItem('currentUser', JSON.stringify(response.data.user));
                })
                .catch((error) => {
                    console.error('Ошибка при входе:', error);
                    this.result_message = "Ошибка входа"
                    this.success = false;
                })
        },

        logout() {
            this.currentUser = null;
            this.result_message = "";
            sessionStorage.removeItem('currentUser');
        },

        loadFromSession() {
            const user = sessionStorage.getItem('currentUser');
            if (user) {
                this.currentUser = JSON.parse(user);
            }
        },

        registration(userData){
            axios
                .post(
                    'http://localhost:8080/api/reg',
                    userData,
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            Accept: 'application/json'
                        }
                    })
                .then((response) => {
                    this.result_message = response.data;
                    if (response.data.success) {
                        this.currentUser = response.data.user
                        sessionStorage.setItem('currentUser', JSON.stringify(response.data.user));
                    }
                    this.success = response.data.success;
                })
                .catch((error) => {
                    console.error('Ошибка при регистрации:', error);
                    this.result_message = "Ошибка при регистрации"
                    this.success = false;
                })
        }
    },
});
