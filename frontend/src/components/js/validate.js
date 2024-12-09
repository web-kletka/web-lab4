
const validate = (x, y, z, r) => {
    let result = "";
    console.log(x, y, r);


    if (x == null) {
        result = "ERROR: x = null";
    }
    else if (y == null) {
        result = "ERROR: y = null";
    }
    else if (y === '') {
        result = "ERROR: y = ''";
    }
    else if (x === '') {
        result = "ERROR: x = ''";
    }
    else if (Number.isNaN(Number(y))) {
        result = "ERROR: y not num";
    }
    else if (Number.isNaN(Number(x))) {
        result = "ERROR: x not num";
    }
    else if (r == null) {
        result = "ERROR: r = null";
    }
    if (result === "")
        return [true, result];
    else {
        return [false, result];
    }
}

export function validateForm(point, result_r) {
    let [isValid, result] = validate(point.x, point.y, point.z, result_r)
    if (isValid) {
        return [isValid, result];
    }
    return [isValid, result];
}

