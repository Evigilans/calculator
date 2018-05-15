package com.piankov.calculator.action;

import com.piankov.calculator.calculator.Calculator;
import com.piankov.calculator.constant.StringConstant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class CalculatorAction implements Action {
    @Override
    public void performAction(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String[]> parameterMap = req.getParameterMap();

        Calculator calculator = new Calculator();
        BigDecimal result = calculator.calculate(parameterMap);

        RoundingMode roundingMode = RoundingMode.valueOf(req.getParameter(StringConstant.PARAMETER_ROUNDING));

        try {
            req.setAttribute(StringConstant.PARAMETER_RESULT, result.setScale(StringConstant.PARAMETER_SCALING, roundingMode));
            req.getRequestDispatcher(StringConstant.PAGE_HOME).forward(req, resp);
        } catch (ServletException | IOException e) {
            req.setAttribute(StringConstant.PARAMETER_ERROR_MESSAGE, "Check your expression!");
        }
    }
}
