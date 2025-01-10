import * as THREE from "three";
import Worker from './searchPointsWorker.js?worker'

let worker
export function drawGraphic(scene, r, equationStr) {
    return new Promise((resolve, reject) => {
        scene.clear();
        // Создание осей
        const axisX = createAxis(scene, 0xf0f0f0, new THREE.Vector3(-5, 0, 0), new THREE.Vector3(5, 0, 0)); // Красная ось X
        const axisY = createAxis(scene, 0xf0f0f0, new THREE.Vector3(0, -5, 0), new THREE.Vector3(0, 5, 0)); // Зеленая ось Y
        const axisZ = createAxis(scene, 0xf0f0f0, new THREE.Vector3(0, 0, -5), new THREE.Vector3(0, 0, 5)); // Синяя ось Z

        // Добавление осей на сцену
        scene.add(axisX);
        scene.add(axisY);
        scene.add(axisZ);

        console.log(equationStr);

        if (worker) {
            worker.terminate();
        }

        worker = new Worker();

        worker.postMessage({ equationString: equationStr, r: r });

        worker.onmessage = (event) => {
            try {
                const rawPoints = event.data;

                const vertices = rawPoints.map(([x, y, z]) => new THREE.Vector3(x, y, z));
                const geometry = new THREE.BufferGeometry().setFromPoints(vertices);
                const material = new THREE.PointsMaterial({ color: 0x003366, size: 0.05 });
                const points = new THREE.Points(geometry, material);
                scene.add(points);

                resolve(); // Уведомить, что работа завершена
            } catch (error) {
                reject(error); // Уведомить об ошибке
            }
        };

        worker.onerror = (error) => {
            console.error("Ошибка в воркере:", error.message);
            reject(error); // Уведомить об ошибке
        };
    });
}

export function isDynamicChecked(scene, getColor, points, checked, r){
    if (checked) {
        points.forEach(point => {
            drawPoint(scene, point.x, point.y, point.z,getColor(point.x, point.y, point.z, r) ? "green" : "red");
        });
    }
    else{
        points.forEach(point => {
            console.log(point.color)
            drawPoint(scene, point.x, point.y, point.z, point.result ? "green" : "red");
        });
    }
}

// Функция для создания осей
function createAxis(scene, color, start, end) {
    const geometry = new THREE.BufferGeometry().setFromPoints([start, end]);
    const material = new THREE.LineBasicMaterial({ color: color });
    const axis = new THREE.Line(geometry, material);
    return axis;
}

// Функция для добавления точки
function drawPoint(scene, x, y, z, color) {
    const geometry = new THREE.SphereGeometry(0.05, 32, 32);
    const material = new THREE.MeshBasicMaterial({color: color});
    const point = new THREE.Mesh(geometry, material);
    point.position.set(x, y, z);
    scene.add(point);
}


