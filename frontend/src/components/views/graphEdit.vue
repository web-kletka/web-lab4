<script>
import Header from "@/components/parts/header.vue";
import { useUserStore } from "@/stores/user.js";
import Graph from "@/components/parts/graph.vue";
import { create, all } from "mathjs";
import {ref} from "vue";

export default {
  name: "graphEdit",
  components: { Graph, Header },
  data() {
    return {
      formula: sessionStorage.getItem("func"), // Поле ввода для формулы
      math: create(all),
      result: null, // Результат вычислений
      error: null, // Сообщение об ошибке
      variables: {
        x: null, // Пример переменной
        y: null, // Пример переменной
        z: null,
      },
      showGraph: true,
    };
  },
  setup() {
    const userStore = useUserStore();
    const graphs = ref(null);
    return {
      userStore,
      graphs
    };
  },
  methods: {
    parseFormula() {
      if (!this.formula.trim()) {
        this.error = "Введите формулу для обработки.";
        this.result = null;
        return;
      }
      try {
        this.error = null;
      } catch (err) {
        this.error = `Ошибка: ${err.message}`;
        this.result = null;
      }
    },

    preview(){
      console.log(this.formula)
      this.showGraph = false;
      this.$nextTick(() => {
        this.showGraph = true;
      });
    },
    sendToGraph() {
      console.log(this.formula)

      sessionStorage.setItem("func", this.formula)

    },

  },
};
</script>

<template>
  <Header
      :login="userStore.currentUser?.login || 'Гость'"
      :show_button="true"
      :buttons="[{ text: '<', route: '/game' }]"
  />

  <div class="main">
    <div v-if="showGraph">
      <Graph :width="600" :height="600" :func="formula" ref="graphs"/>
    </div>
    <div class="input-func">
      <div class="formula-parser">
        <h1>Вычисление формулы</h1>
        <input
            type="text"
            v-model="formula"
            @input="parseFormula"
            placeholder="Введите формулу, например: x + y"
        />

        <div class="buttons">
          <button class="button" @click="preview">Предпросмотр</button>
          <button class="button" @click="sendToGraph">Сохранить</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "../style/body.css";
@import "../style/button.css";

.main {
  display: flex;
  padding-left: 3em;
  padding-right: 3em;
  flex-direction: row;
}

.input-func {
  margin: 3em;
  width: 100%;
  align-content: center;
}

.formula-parser {
  margin: 20px;
  font-family: Arial, sans-serif;
}

.formula-parser h1{
  text-align: center;
  padding: 20px;
}
input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  margin-bottom: 20px;
}

.buttons{
  width: 100%;
  margin: auto;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

button {
  padding: 10px 20px;
  margin: 10px;
}


</style>
