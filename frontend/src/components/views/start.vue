<script>
import Header from "../parts/header.vue"
import "../style/body.css"
import "../style/button.css"
import {drawClock} from "../js/clock.js"
import { useUserStore } from '@/stores/user';

import route from "@/route/index.js";


export default {
  name: 'start',
  components: { Header },
  setup(){
  },
  data(){
    const userStore = useUserStore();

    return{

        disabled: false,
        user: userStore.currentUser,
        reg_login: userStore.currentUser?.login || '',
        result_message: '',
        show_login_form: false,
        show_button: true,

        login: '',
        password: '',

        buttons: userStore.currentUser == null ?
            [
              { text: 'Войти', onClick: () => this.showLogInForm() },
              { text: 'Зарегистрироваться', route: '/reg'}
            ] :  [
              { text: 'Выйти', onClick: () => this.logOut()},
            ]
    }
  },
  methods: {

    warnDisabled() {
      this.disabled = true
      setTimeout(() => {
        this.disabled = false
      }, 1500)
    },

    showLogInForm () {
      this.show_login_form = true
      this.buttons = [
        { text: '<', onClick: () => this.logOut()},
      ]
      this.reg_login = "Войти"
      this.result_message = ""
    },

    async logIn (event) {
      event.preventDefault(); //

      const userData = {
        login: this.login,
        password: this.password
      };

      const userStore = useUserStore();
      await userStore.login(userData);
      console.log(userStore.success, userStore.result_message, userStore.currentUser)


      this.user = userStore.currentUser

      if (userStore.success){
        this.buttons = [
          { text: 'Выйти', onClick: () => this.logOut()},
        ]
        this.reg_login = this.user.login

        this.show_login_form = false
        this.show_button = true
      }
      else{
        this.result_message = userStore.result_message
        this.warnDisabled()
      }
    },

    logOut(){
      const userStore = useUserStore();
      userStore.logout();
      this.user = null

      this.show_login_form = false
      this.reg_login = ""
      this.buttons = [
        { text: 'Вход', onClick: () => this.showLogInForm() },
        { text: 'Зарегистрироваться', route: '/reg'}
      ]
    },

    route() {
      return route
    },


  },
  mounted() {

    const canvas = document.getElementById("clock");
    if (!canvas) {
      console.error("Canvas с ID 'clock' не найден!");
      return;
    }
    const ctx = canvas.getContext('2d');
    const radius = canvas.width / 2;

    const clockRadius = radius * 0.9;

    ctx.translate(radius, radius);


    setInterval(() => drawClock(canvas, ctx, clockRadius), 1000);

    drawClock(canvas, ctx, clockRadius);

    const data = document.getElementById("date")
    data.textContent = (new Date()).toLocaleDateString('ru-RU', {
      day: '2-digit',
      month: 'long',
      year: 'numeric',
    })


  }
}
</script>

<template>
  <body>
    <Header
      :login="reg_login"
      :show_button="show_button"
      :buttons="buttons"/>
    <div class="center_block">
      <div class="clock" :class="{moveLeft: show_login_form}">
        <div class="box" >
          <canvas id="clock" width="400px" height="400px"/>
        </div>
        <h3 id = "date"/>
      </div>

      <form class="login_form" :class="{showForm: show_login_form}">
        <div>
          <label>Логин: </label>
          <input type="text" v-model="login" class="textField" id="login" name="login" />
        </div>

        <div>
          <label>Пароль: </label>
          <input type="password" v-model="password" class="textField" id="password" name="password" />
        </div>

        <button :class="{ shake: disabled }" @click="logIn" class="button">Войти</button>

        <label class="result_message" >{{result_message}}</label>
      </form>
    </div>

    <footer class="footer">
      <router-link class="button" to="/game">Основная игра</router-link>
    </footer>
  </body>
</template>

<style scoped>
@import "../style/form.css";
@import "../style/button.css";
@import "../style/body.css";

body {
  display: flex;
  flex-direction: column;
  background-color: #333;
  overflow: hidden; /* Прячем выходящие элементы */
  min-height: 100vh;
}

.shake {
  animation: shake 0.82s cubic-bezier(0.36, 0.07, 0.19, 0.97) both;
  transform: translate3d(0, 0, 0);
}

@keyframes shake {
  10%,
  90% {
    transform: translate3d(-1px, 0, 0);
  }

  20%,
  80% {
    transform: translate3d(2px, 0, 0);
  }

  30%,
  50%,
  70% {
    transform: translate3d(-4px, 0, 0);
  }

  40%,
  60% {
    transform: translate3d(4px, 0, 0);
  }
}

.center_block{
  margin: auto;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  //background: cornsilk;
}

.login_form{
  width: 400px;
  margin: 0;
  visibility: hidden;
  opacity: 0;
  transition: opacity 0.5s ease-in-out, visibility 0s linear 0.6s, width 0.5s ease-in-out;

}

.login_form.showForm{
  opacity: 1;
  visibility: visible;
  transition: opacity 0.3s ease-in-out, visibility 0s linear 0s;
  transform: translate(0, 0);
}

.login_form button{
  margin-left: auto;
  margin-right: auto;
  width: 240px;
}

.result_message{
  display: inline-block; /* Убедитесь, что элемент ведет себя как блочный */
  width: 310px; /* Задайте желаемую ширину */
  height: 30px;
  margin-right: 17px;
  margin-left: auto;
  color: #e0e0e0;
  text-align: center;
}

.button{
  margin: 1em;
}

.clock {
  display: flex;
  align-items: center;
  flex-direction: column;
  transform: translate(50%, 0);
  transition: transform 0.5s ease-in-out;
  //background: #444444;
}

.clock.moveLeft{
  transform: translate(0, 0);
  transition: transform 0.5s ease-in-out;
}

.box {
  width: 400px;
  font-weight: bold;
}

.footer {
  margin-top: auto;
  border-top: 1px solid #555;
  padding: 10px;
  display: flex;
  text-align: center;
}

</style>
