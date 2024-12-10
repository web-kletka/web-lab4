<script>
import * as THREE from "three";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls";
import {drawGraphic, isDynamicChecked} from "@/components/js/graph.js";
import {reactive} from "vue";
import route from "@/route/index.js";

export default {
  name: "graph",
  props: {
    points: {
      type: Array,
      default: () => [],
    },
    func: {
      type: Function,
      default: () => () => {},
    },
    width: {
      type: Number,
      default: 370
    },
    height: {
      type: Number,
      default: 350
    }
  },
  data(){
    return{
      R: 0,
      points: reactive([])
    }
  },
  mounted() {
    const canvas = document.getElementById("graphCanvas");

    // Функция для построения графика
    const implicitFunction = (x, y, z) => {
      // console.log(x, y, z)
      return x ** 2 + y ** 2 + z ** 2 + Math.sin(4 * x) + Math.sin(4 * y) + Math.sin(4 * z) - this.R;
    };

    // const implicitFunction = this.func

    const getColor = (x, y, z) => {
      return implicitFunction(x, y, z) <= 0
    };

    // Создаем сцену, камеру и рендерер
    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(75, canvas.width / canvas.height, 0.1, 1000);
    const renderer = new THREE.WebGLRenderer({ canvas: canvas });
    renderer.setSize(canvas.width, canvas.height);

    // Установка фона
    scene.background = new THREE.Color(0x333333);

    // Управление камерой
    const controls = new OrbitControls(camera, renderer.domElement);
    controls.enableDamping = true;
    controls.dampingFactor = 0.05;

    // Настройка камеры
    camera.position.set(4, 4, 4);
    camera.lookAt(0, 0, 0);

    // Сохраняем объекты в this
    this.scene = scene;
    this.camera = camera;
    this.renderer = renderer;
    this.controls = controls;
    this.implicitFunction = implicitFunction;
    this.getColor = getColor;

    // Анимация
    this.animate();

    // Вызов функции отрисовки графика
    drawGraphic(scene, this.R, this.implicitFunction);
  },
  methods: {
    animate() {
      requestAnimationFrame(() => this.animate());
      this.controls.update(); // Обновление OrbitControls
      this.renderer.render(this.scene, this.camera);
    },
    redrawGraphic(R){
      this.R = R;
      drawGraphic(this.scene, this.R, this.implicitFunction);
      this.redrawPoints()
    },
    redrawPoints(){
      console.log(this.points)
      isDynamicChecked(this.scene, this.getColor, this.points, true)
    },
    addPoint(point){
      this.points.push(point)
      this.redrawPoints()
    },
    setPoints(points){
      for (const point of points){
        this.addPoint(point)
      }
    },
    clearPoints(){
      this.points.splice(0, this.points.length)
    },
  },
};
</script>

<template>
    <div class="graph-canvas">
      <canvas id="graphCanvas" :width="width" :height="height"></canvas>
    </div>
</template>

<style scoped>

.graph-canvas {
  display: flex;
  border: #003366 1px solid;
  margin: 0 0 6px;
  padding: 0;
}

</style>
