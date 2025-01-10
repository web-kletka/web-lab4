package org.example.backend.services;

import org.springframework.stereotype.Service;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class CalculateResultService {
    public boolean calculate(Float x, Float y, Float z, Float r, String formula) {

        System.out.println("============== " + x + " : " + y + " : " + z + " : " + r + " : " + formula);
        String modifiedFormula = formula
                .replace("x", "(" + x.toString() + ")")
                .replace("y", "(" + y.toString() + ")")
                .replace("z", "(" + z.toString() + ")")
                .replace("r", "(" + r.toString() + ")");

        Expression expression = new ExpressionBuilder(modifiedFormula).build();

        double result = expression.evaluate();
        System.out.println("============== " + result);
        return result <= 0;
    }
}
