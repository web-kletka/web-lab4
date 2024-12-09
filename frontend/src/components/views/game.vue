<script>
import Header from "@/components/parts/header.vue";
import Graph from "@/components/parts/graph.vue";
import MyTable from "@/components/parts/myTable.vue";
import {useUserStore} from "@/stores/user";
import {decimalWithComplement} from "@/components/js/resultR.js";
import {validateForm} from "@/components/js/validate.js";
import {ref, toRaw} from "vue";
import axios from "axios";

export default {
  name: 'game',
  components: {MyTable, Graph, Header},

  setup() {
    const userStore = useUserStore();
    const result = ref("Нету данных на обработку")
    const result_r = ref(0);
    const rValues = ref([false, false, false, false]);
    const graphs = ref(null);
    const myTable = ref(null);
    const point = ref({x:'', y:'', z:'',})

    const handleCheckboxChange = (index, event) => {
      const newValues = [...rValues.value];
      newValues[index] = event.target.checked;

      const binary = newValues.map((v) => (v ? "1" : "0")).join("");
      const result = parseInt(decimalWithComplement(binary));

      if (result < -5 || result > 5) {
        event.target.checked = rValues.value[index];
        console.log("BAD");
      } else {
        rValues.value = newValues;
        result_r.value = result;
        graphs.value?.redrawGraphic(result);
        console.log("OK");
      }
    };

    const addPoint = async (event) => {
      event.preventDefault();


      let [isValid, checked_result] = validateForm(point.value, result_r)

      if(!isValid){
        result.value = checked_result
        return
      }

      const pointData = {
        x: toRaw(point.value).x,
        y: toRaw(point.value).y,
        z: toRaw(point.value).z,
        r: toRaw(result_r.value),
        user: userStore.currentUser.login
      };

      await axios
          .post(
              'http://localhost:8080/api/game/check',
              pointData,
              {
                headers: {
                  'Content-Type': 'application/json',
                  Accept: 'application/json'
                }
              })
          .then((response) => {
            console.log(response.data.message)
            if(response.data.message !== "ok")
              result.value = checked_result
            else{
              let check_point = response.data
              if (myTable.value) {
                console.log("addTablePoint");
                myTable.value.addPoint(check_point);

              }
              if (graphs.value) {
                console.log("addGraphPoint");
                graphs.value.addPoint(check_point);
              }
              result.value = check_point.result? "Попадание": "Мимо"
            }
          })
          .catch((error) => {
            console.error('Ошибка подсчёта результата:', error);
            result.value = "Ошибка подсчёта результата"
          })
    };

    const setPoints = async (event) => {
      if(event) event.preventDefault(event);

      if (!myTable.value) {
        console.error("myTable is not initialized");
        result.value = "Ошибка инициализации таблицы";
        return;
      }

      if (!graphs.value) {
        console.error("graphs is not initialized");
        result.value = "Ошибка инициализации графика";
        return;
      }

      try {
        const response = await axios.get('http://localhost:8080/api/game/get_all_points');
        console.log("Полученные точки:", response.data);

        if (Array.isArray(response.data)) {
          myTable.value.clearPoints()
          myTable.value.setPoints(response.data);

          graphs.value.clearPoints()
          graphs.value.setPoints(response.data)
        } else {
          console.error("Некорректный формат данных:", response.data);
          result.value = "Некорректный формат данных";
        }
      } catch (error) {
        console.error('Ошибка получения данных:', error);
        result.value = "Ошибка получения данных";
      }
    };

    const clearPoints = async (event) => {
      event.preventDefault();

      if (myTable.value && graphs.value) {
        console.log("addTablePoint");
        await axios
            .delete('http://localhost:8080/api/game/admin/delete_all_points', {
              data: { login: userStore.currentUser.login }
            })
            .then((response) => {
              if (response.data) result.value = "Таблица успешно отчищена"
              else result.value = "произошла ошибка при отчистке бд"
              setPoints()
            })
            .catch((error) => {
              console.error('Ошибка при удалении пользователя:', error);
            });

      }
    }

    return {
      result,
      point,
      userStore,
      result_r,
      rValues,
      graphs,
      myTable,
      handleCheckboxChange,
      addPoint,
      setPoints,
      clearPoints
    };
  },
  mounted() {
    if (this.myTable && this.graphs) {
      this.setPoints();
    } else {
      console.error("myTable или graphs не инициализированы");
    }
  }

};
</script>


<template>
  <body>
  <Header
      :login="userStore.currentUser?.login || 'Гость'"
      :show_button="true"
      :buttons="[
        { text: '<', route: '/'}
      ]"
  />

  <div class="form-container">
    <form class="dots_form" id="dots_form">
      <input type="hidden" name="action" value="areaCheck"/>


      <div class="input-group">
        <label>Изменение X:</label>
        <input type="text" v-model="point.x" class="textField" id="x" name="x" placeholder="-4 ... 4"/>
      </div>

      <div class="input-group">
        <label>Изменение Y:</label>
        <input type="text" v-model="point.y" class="textField" id="y" name="y" placeholder="-4 ... 4"/>
      </div>

      <div class="input-group">
        <label>Изменение Z:</label>
        <input type="text" v-model="point.z" class="textField" id="z" name="z" placeholder="-4 ... 4"/>
      </div>

      <div class="input-group">
        <label>Изменение R:</label>
        <div class="checkbox-group">
          <label v-for="(r, index) in rValues" :key="index">
            <input
                type="checkbox"
                :checked="r"
                @change="handleCheckboxChange(index, $event)"
            />
          </label>
        </div>

        <label id="resultCheckBox">{{result_r}}</label>

      </div>
      <div class="input-group">
        <label>Динамичная проверка: </label>
        <div class="checkbox-group">
          <label><input type="checkbox" name="dynamic_check_box"  class="check_dynamic"/></label>
        </div>
      </div>

      <button value="Отправить" class="button" id="responseButton" @click="addPoint">Проверить</button>

      <div id="out_put_text" class="out_put_text" >
        <label>{{result}}</label>
      </div>

    </form>

    <!-- Canvas для отрисовки фигуры -->
    <div class="graph">
      <Graph ref="graphs"/>
      <router-link class="button" to="/graph" style="padding: 1px">изменение функции</router-link>
    </div>

  </div>

  <MyTable ref="myTable"/>

  <div v-if="userStore.currentUser.status === 1" class="controller">
    <button class="button" @click="clearPoints">Отчистить</button>
  </div>
  </body>
</template>

<style scoped>
@import "../style/body.css";
@import "../style/button.css";

body {
  display: flex;
  flex-direction: column;
  background-color: #333;
  min-height: 100vh;
}

.graph{
  display: flex;
  flex-direction: column;
}

.controller{
  width: 10em;
  margin-left: auto;
  margin-top: 5px;
  margin-right: 25px;
}

.form-container {
  display: flex;
  justify-content: space-around;
  align-items: flex-start;
  margin-bottom: 30px;
}

form {
  display: flex;
  justify-content: center;
  align-content: center;
  flex-direction: column;
  width: 65%;
}

#resultCheckBox {
  padding: 0;
  margin: 5px 0 0 20px;
}


.input-group {
  display: flex;
  justify-content: start;
  align-content: center;
  margin-bottom: 15px;
}



label {
  display: inline-block; /* или block, если нужно */
  height: 30px; /* Задаем фиксированную высоту */
  line-height: 30px; /* Делаем line-height равным высоте */
  text-align: center; /* Выравнивание по горизонтали */
  font-size: 1.1em;
  color: #e0e0e0;
  margin: 5px;
}

input[type="text"] {
  padding: 10px;
  font-size: 1em;
  border: 1px solid #555;
  border-radius: 8px;
  background-color: #444;
  color: #f0f0f0;
  transition: background-color 0.3s ease;
}

input[type="text"]:focus {
  background-color: #555;
  border-color: #003366; /* Темно-синий цвет */
  outline: none;
}

input[type="checkbox"]{
  height: 30px;
  width: 30px;
}

#resultCheckBox{
  align-content: center;
  height: 100%;
}

.checkbox-group{
}




.radio-group label{
  margin-right: 12px;
  color: #e0e0e0;
}

button, .button{
  padding: 10px;
  font-size: 1.1em;
  background-color: #003366; /* Тёмно-синий цвет кнопки */
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover, .button:hover {
  background-color: #001f4d;
}

.out_put_text {
  color: white;
  padding: 10px;
  text-align: center;
}

</style>
