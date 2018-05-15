package com.piankov.calculator.calculator;

import com.piankov.calculator.exception.WrongOperatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

class ExpressionConverter {
    private static Logger LOGGER = LogManager.getLogger(ExpressionConverter.class);

    private static final String EXPRESSION_DELIMITER = "(?<=[()\\s])|(?=[()\\s+])";
    private static final int LEFT_ASSOC = 0;
    private static final int RIGHT_ASSOC = 1;
    private static Map<String, int[]> operators = new HashMap<>();

    static {
        operators.put("+", new int[]{0, LEFT_ASSOC});
        operators.put("-", new int[]{0, LEFT_ASSOC});
        operators.put("*", new int[]{1, LEFT_ASSOC});
        operators.put("/", new int[]{1, LEFT_ASSOC});
    }

    private static boolean isDefinedOperator(String token) {
        return operators.containsKey(token);
    }

    private static boolean isAssociative(String token, int type) throws WrongOperatorException {
        if (!isDefinedOperator(token)) {
            throw new WrongOperatorException("Invalid token: " + token);
        }
        return operators.get(token)[1] == type;
    }

    private static int comparePrecedence(String left, String right) throws WrongOperatorException {
        if (!isDefinedOperator(left) || !isDefinedOperator(right)) {
            throw new WrongOperatorException("Invalid tokens: " + left + " " + right);
        }
        return operators.get(left)[0] - operators.get(right)[0];
    }

    private String buildString(List<String> list) {
        LOGGER.debug("Building final string.");
        StringBuilder result = new StringBuilder();
        for (String token : list) {
            result.append(token).append(" ");
        }
        String output = result.toString().trim();

        LOGGER.debug("Output reverse polish expression: " + output);
        return output;
    }

    String convertToReversePolandNotation(String expression) {
        LOGGER.debug("Parsing to polish notation.");
        String[] inputTokens = expression.split(EXPRESSION_DELIMITER);
        ArrayList<String> out = new ArrayList<>();
        ArrayDeque<String> arrayDeque = new ArrayDeque<>();

        try {
            for (String token : inputTokens) {
                if (isDefinedOperator(token)) {
                    while (!arrayDeque.isEmpty() && isDefinedOperator(arrayDeque.peek())) {
                        if ((isAssociative(token, LEFT_ASSOC) && comparePrecedence(token, arrayDeque.peek()) <= 0) || (isAssociative(token, RIGHT_ASSOC) && comparePrecedence(token, arrayDeque.peek()) < 0)) {
                            out.add(arrayDeque.pop());
                            continue;
                        }
                        break;
                    }
                    arrayDeque.push(token);
                } else if (token.equals("(")) {
                    arrayDeque.push(token);
                } else if (token.equals(")")) {
                    while (!arrayDeque.isEmpty() && !arrayDeque.peek().equals("(")) {
                        out.add(arrayDeque.pop());
                    }
                    arrayDeque.pop();
                } else {
                    out.add(token);
                }
            }
        } catch (WrongOperatorException e) {
            LOGGER.error("Invalid operator token.");
        }
        while (!arrayDeque.isEmpty()) {
            out.add(arrayDeque.pop());
        }

        LOGGER.debug("Expression parsed successfully.");
        return buildString(out);
    }
}