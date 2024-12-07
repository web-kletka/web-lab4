<script>
import Header from "@/components/parts/header.vue";
import "../style/body.css";
import "../style/button.css";
import "../style/form.css";
import { useUserStore } from '@/stores/user';
import axios from 'axios';

export default {
  name: "Registration",
  components: { Header },
  data() {
    return {
      result_data: {},
      result_message: '',
      login: '',
      password: '',
      repeat_password: ''
    };
  },
  methods: {
    async register(event) {
      event.preventDefault(); // Предотвращаем стандартную отправку формы

      const userData = {
        login: this.login,
        password: this.password
      };
      const userStore = useUserStore();
      await userStore.registration(userData);
      this.result_data = userStore.result_data
      this.result_message = this.result_data.message
    },
  }
}
</script>

<template>

  <Header
      :show_button="true"
      :buttons="[
        { text: '<', route: '/'}
      ]"
      login="Регистрация"/>

  <body>
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

      <button @click="register" class="button">Зарегистрироваться</button> <!-- Используем type="submit" -->
    </form>

    <div>
      <h1 class = "result">{{ result_message }}</h1>
    </div>
  </div>
  </body>
</template>

<style>

.input_block{
  display: flex;
  margin-top: 5em;
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

form div input {
  width: 40em;
}
form div label {
  text-align: right;
  width: 10em;
}
</style>
