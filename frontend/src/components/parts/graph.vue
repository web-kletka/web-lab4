<script>
import * as THREE from "three";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls";
import { drawGraphic, isDynamicChecked } from "@/components/js/graph.js";
// import { reactive } from "vue";
import { compile } from "mathjs";
import {bool} from "three/tsl";

export default {
  name: "graph",
  props: {
    points: {
      type: Array,
      default: () => [],
    },
    width: {
      type: Number,
      default: 370,
    },
    formula: {
      type: String,
      default: sessionStorage.getItem("formula"),
    },
    height: {
      type: Number,
      default: 350,
    },
    isDynamicCheck:{
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      R: 1,
      // points: reactive([]),
      loading: true,
    };
  },
  setup(props) {
    return {
      props, // Доступно внутри шаблона, если нужно
    };
  },
  mounted() {
    const canvas = document.getElementById("graphCanvas");

    const getColor = (x, y, z, r) => {
      return compile(sessionStorage.getItem("formula")).evaluate({ x, y, z, r }) <= 0;
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
    this.getColor = getColor;

    // Анимация
    this.animate();

    // Вызов функции отрисовки графика
    this.loading = true
    drawGraphic(scene, this.R, this.formula)
        .then(() => {
          this.loading = false;
        })
        .catch((error) => {
          this.loading = false;
        });

    this.redrawPoints()
  },
  methods: {
    animate() {
      requestAnimationFrame(() => this.animate());
      this.controls.update(); // Обновление OrbitControls
      this.renderer.render(this.scene, this.camera);
    },
    redrawGraphic(R){
      this.R = R;
      this.loading = true
      console.log(this.formula)
      drawGraphic(this.scene, this.R, this.formula)
          .then(() => {
            this.loading = false;
          })
          .catch((error) => {
            this.loading = false;
          });
      this.redrawPoints()
    },
    redrawPoints(){
      console.log(this.points)
      isDynamicChecked(this.scene, this.getColor, this.points, this.isDynamicCheck, this.R)
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
      this.redrawGraphic(this.R)
    },
  },
};
</script>

<template>
  <div class="graph-canvas">
    <canvas id="graphCanvas" :width="width" :height="height"></canvas>
    <div v-if="loading" class="loading-overlay">
      <img src="../../assets/802.gif" alt="Loading..." class="loading-gif" />
    </div>
  </div>
</template>

<style scoped>
.graph-canvas {
  display: flex;
  position: relative;
  border: #003366 1px solid;
  margin: 0 0 6px;
  padding: 0;
}

.loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 10;
}

.loading-gif {
  width: 50px;
  height: 50px;
}
</style>
