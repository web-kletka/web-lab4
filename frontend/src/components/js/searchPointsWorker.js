import { compile } from 'mathjs';

self.onmessage = (event) => {
    const { equationString, r } = event.data;
    const equation = compile(equationString);

    const points = [];
    const size = 5.5; // Границы поиска (от -size до size)
    const step = 0.07; // Шаг поиска
    const threshold = 0.3; // Точность попадания

    for (let x = -size; x <= size; x += step) {
        for (let y = -size; y <= size; y += step) {
            for (let z = -size; z <= size; z += step) {
                const value = equation.evaluate({ x, y, z, r });
                if (Math.abs(value) < threshold) {
                    points.push([x, y, z]); // Массив координат
                }
            }
        }
    }

    // Возвращаем массив точек
    self.postMessage(points);
};
