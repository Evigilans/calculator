package com.piankov.calculator.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.function.BiFunction;

public class ExpressionInterpreter {
    private static Logger LOGGER = LogManager.getLogger(ExpressionInterpreter.class);

    private static final int INPUT_SCALE = 3;
    private static final int ROUNDING_SCALE = 6;

    private void interpret(ArrayDeque<BigDecimal> numbers, BiFunction<BigDecimal, BigDecimal, BigDecimal> operation) {
        LOGGER.debug("Adding data to array.");
        numbers.push(operation.apply(numbers.pop(), numbers.pop()));
    }

    public BigDecimal calculate(String input) {
        LOGGER.debug("Calculating expression: " + input);
        ArrayDeque<BigDecimal> numbers = new ArrayDeque<>();
        ExpressionConverter expressionConverter = new ExpressionConverter();
        String convertedPolandNotation = expressionConverter.convertToReversePolandNotation(input);

        System.out.println(convertedPolandNotation);

        Arrays.stream(convertedPolandNotation.split(" ")).forEach(number -> {
            if (!number.isEmpty()) {
                switch (number) {
                    case "+":
                        interpret(numbers, (n1, n2) -> n2.add(n1));
                        break;
                    case "-":
                        interpret(numbers, (n1, n2) -> n2.subtract(n1));
                        break;
                    case "*":
                        interpret(numbers, (n1, n2) -> n2.multiply(n1));
                        break;
                    case "/":
                        interpret(numbers, (n1, n2) -> n2.divide(n1, ROUNDING_SCALE, RoundingMode.HALF_UP));
                        break;
                    default:
                        try {
                            numbers.push(new BigDecimal(number));
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                        }
                }
            }
        });

        BigDecimal result = numbers.pop();
        LOGGER.debug("Expression calculating result: " + result);
        return result;
    }
}