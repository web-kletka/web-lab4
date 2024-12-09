<script>
import Header from "@/components/parts/header.vue";

import { useUserStore } from '@/stores/user';
import axios from 'axios';

export default {
  name: "registration",
  components: { Header },
  data() {
    return {
      result_data: {},
      result_message: '',
      login: '',
      password: '',
      repeat_password: '',
      code_word: ''
    };
  },
  methods: {
    async register(event) {
      event.preventDefault(); // Предотвращаем стандартную отправку формы


      if (this.password !== this.repeat_password){
        this.result_message = "Пароли не совпадают";
        return
      }

      const userData = {
        login: this.login,
        password: this.password,
        code_word: this.code_word
      };
      const userStore = useUserStore();
      await userStore.registration(userData);
      this.result_message = userStore.result_message
    },
  }
}
</script>

<template>

  <body>
  <Header
      :show_button="true"
      :buttons="[
        { text: '<', route: '/'}
      ]"
      login="Регистрация"/>

  <div class="input_block">
    <form @submit="register"> <!-- Используем @submit вместо @click -->
      <div>
        <label>Логин: </label>
        <input type="text" v-model="login" class="textField" id="login" name="login" />
      </div>

      <div>
        <label>Пароль: </label>
        <input type="password" v-model="password" class="textField" id="password" name="password" />
      </div>

      <div>
        <label>Повторите пароль: </label>
        <input type="password" v-model="repeat_password" class="textField" id="repeat_password" name="repeat_password" />
      </div>

      <div>
        <label>Кодовое слово для получения прав администратора:</label>
        <input type="password" v-model="code_word" class="textField" id="repeat_password" name="repeat_password" />
      </div>


      <button @click="register" class="button">Зарегистрироваться</button> <!-- Используем type="submit" -->
    </form>

    <div>
      <h1 class = "result">{{ result_message }}</h1>
    </div>
  </div>


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

.input_block{
  display: flex;
  flex-direction: column; /* Основная ось формы — вертикальная */
  justify-content: center; /* Центрирование содержимого */
  align-items: stretch; /* Растянуть по ширине */
}

.result {
  display: block;
  text-align: center;

  margin: 1.5em;
  //background: bisque;
}

.input_block div input {
  width: 40em;
}
</style>
