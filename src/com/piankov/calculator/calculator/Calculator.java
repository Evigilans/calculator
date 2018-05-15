package com.piankov.calculator.calculator;

import java.math.BigDecimal;
import java.util.Map;

public class Calculator {
    private static final String EXPRESSION_PARAMETER = "expression";

    public BigDecimal calculate(Map<String, String[]> map) {
        ExpressionInterpreter interpreter = new ExpressionInterpreter();
        return interpreter.calculate(map.get(EXPRESSION_PARAMETER)[0]);
    }
}
