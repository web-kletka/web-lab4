<script>
import Header from "@/components/parts/header.vue";
import { useUserStore } from "@/stores/user.js";
import Graph from "@/components/parts/graph.vue";
import { create, all } from "mathjs";

export default {
  name: "graphEdit",
  components: { Graph, Header },
  data() {
    return {
      formula: "", // Поле ввода для формулы
      result: null, // Результат вычислений
      error: null, // Сообщение об ошибке
      variables: {
        x: null, // Пример переменной
        y: null, // Пример переменной
        z: null,
      },
    };
  },
  setup() {
    const userStore = useUserStore();

    return {
      userStore,
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
        // this.result = this.evaluateFormula(this.formula, this.variables);
      } catch (err) {
        this.error = `Ошибка: ${err.message}`;
        this.result = null;
      }
    },

    // Функция для вычисления выражения
    evaluateFormula(x, y, z) {
      const variables = {
        x: x,
        y: y,
        z: z,
      }
      const math = create(all);
      // Преобразуем строку выражения, если есть пробелы
      const cleanExpression = this.formula.replace(/\s+/g, "");

      // Подставляем значения переменных в формулу
      for (const [variable, value] of Object.entries(variables)) {
        if (value !== null) {
          this.formula = this.formula.replace(new RegExp(`\\b${variable}\\b`, "g"), value);
        }
      }

      // Вычисляем выражение
      return math.evaluate(this.formula);
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
    <Graph :width="600" :height="600" />
<!--      :func = "evaluateFormula"-->
    <div class="input-func">
      <div class="formula-parser">
        <h1>Вычисление формулы</h1>
        <input
            type="text"
            v-model="formula"
            @input="parseFormula"
            placeholder="Введите формулу, например: x + y"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "../style/body.css";

.main {
  display: flex;
  padding-left: 3em;
  padding-right: 3em;
  flex-direction: row;
}

.input-func {
  margin: 3em;
  width: 100%;
  border: #003366 1px solid;
}

.formula-parser {
  margin: 20px;
  font-family: Arial, sans-serif;
}

input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  margin-bottom: 20px;
}

.error {
  color: red;
}

.result {
  color: green;
}
</style>
