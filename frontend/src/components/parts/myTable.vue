<script>
import {reactive} from "vue";

export default {
  name: "myTable",
  data(){
    return{
      points: reactive([])
    }
  },
  methods:{
    addPoint(point){
      this.points.push(point)
    },
    clearPoints(){
      this.points.splice(0, this.points.length)
    },
    setPoints(points){
      for (const point of points ){
        this.points.push(point)
      }
    }
  },
}
</script>

<template>

  <div class="large-rectangle">
    <table>
      <thead>
      <tr>
        <th>X, Y, R</th>
        <th>Результат</th>
        <th>Время реальное</th>
        <th>Время работы скрипта (мс)</th>
      </tr>
      </thead>
      <tbody id="myTBody">
        <tr v-for="(point, index) in points" :key="index">
          <td>{{ point.x }}, {{ point.y }}, {{ point.z }}</td>
          <td>{{point.result ? "попадание": "мимо"}}</td>
          <td>{{point.time}}</td>
          <td>{{point.date}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>

@import "../style/button.css";




.large-rectangle {
  margin-right: 5px;
  margin-left: 5px;
  padding: 20px;
  border: 2px dashed #003366; /* Темно-синий цвет рамки */
  border-radius: 12px;
  background-color: #333;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
}

table {
  width: 100%;
  border-collapse: collapse;
  color: #f0f0f0;
  height: auto;
}

thead {
  background-color: #003366;
  color: white;
}

th, td {
  padding: 12px;
  width: 23%;
  text-align: center;
  border-bottom: 1px solid #555;
}

tbody tr:nth-child(even) {
  background-color: #444;
}

tbody tr:hover {
  background-color: #555;
}

tbody td:first-child{
  width: 6%;
}
thead th:first-child{
  width: 6%;
}

th {
  font-weight: bold;
}
</style>