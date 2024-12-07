
import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('user', {
    state: () => ({
        success: true,
        result_data: null,
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
                    this.result_data = response.data;
                    if (this.result_data.success)
                        this.currentUser = this.result_data.user
                    this.success = true;
                    // sessionStorage.setItem('currentUser', JSON.stringify(response.data));
                })
                .catch((error) => {
                    console.error('Ошибка при регистрации:', error);
                    this.success = false;
                })
        },
        logout() {
            this.currentUser = null;
            this.result_data = null
            // sessionStorage.removeItem('currentUser');
        },
        loadFromSession() {
            // const user = sessionStorage.getItem('currentUser');
            // if (user) {
            //     this.currentUser = JSON.parse(user);
            // }
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
                    this.result_data = response.data;
                    if (this.result_data.success)
                        this.currentUser = this.result_data.user
                    this.success = true;
                })
                .catch((error) => {
                    console.error('Ошибка при регистрации:', error);
                    this.success = false;
                })
        }
    },
});
