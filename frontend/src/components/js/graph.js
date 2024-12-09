

// Управление камерой

// Настройка камеры




import * as THREE from "three";


export function drawGraphic(scene, r, implicitFunction){

    scene.clear()
    // Создание осей
    const axisX = createAxis(scene,0xf0f0f0, new THREE.Vector3(-5, 0, 0), new THREE.Vector3(5, 0, 0)); // Красная ось X
    const axisY = createAxis(scene,0xf0f0f0, new THREE.Vector3(0, -5, 0), new THREE.Vector3(0, 5, 0)); // Зеленая ось Y
    const axisZ = createAxis(scene,0xf0f0f0, new THREE.Vector3(0, 0, -5), new THREE.Vector3(0, 0, 5)); // Синяя ось Z

    // Добавление осей на сцену
    scene.add(axisX);
    scene.add(axisY);
    scene.add(axisZ);

    // Параметры сетки
    const size = 5.5; // Границы поиска (от -size до size)
    const step = 0.07; // Шаг поиска
    const threshold = 0.3; // Точность попадания

    // Уравнение поверхности

    // Создание точек поверхности
    const vertices = [];
    for (let x = -size; x <= size; x += step) {
        for (let y = -size; y <= size; y += step) {
            for (let z = -size; z <= size; z += step) {
                const value = implicitFunction(x, y, z);
                if (Math.abs(value) < threshold) {
                    vertices.push(new THREE.Vector3(x, y, z));
                }
            }
        }
    }

    const geometry = new THREE.BufferGeometry().setFromPoints(vertices);
    const material = new THREE.PointsMaterial({ color: 0x003366, size: 0.05 });
    const points = new THREE.Points(geometry, material);
    scene.add(points);
}

export function isDynamicChecked(scene, getColor, points, checked){
    if (checked) {
        points.forEach(point => {
            drawPoint(scene, point.x, point.y, point.z,getColor(point.x, point.y, point.z, Number(points.result)) ? "green" : "red");
        });
    }
    else{
        points.forEach(point => {
            drawPoint(scene, point.x, point.y, point.z, point.color);
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


