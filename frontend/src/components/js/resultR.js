export function decimalWithComplement(code) {
    let num = parseInt(code, 2);
    if (num >= 2 ** (code.length - 1)) {
        num = num - 2 ** code.length;
    }
    return num;
}