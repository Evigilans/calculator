package com.piankov.calculator.action;

import com.piankov.calculator.constant.StringConstant;
import com.piankov.calculator.credit.CreditComputation;
import com.piankov.calculator.credit.Period;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class CreditAction implements Action {
    @Override
    public void performAction(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> map = request.getParameterMap();

        CreditComputation creditComputation = new CreditComputation();
        List<Period> periods = creditComputation.compute(map);

        try {
            request.setAttribute(StringConstant.PARAMETER_PERIODS, periods);
            request.getRequestDispatcher(StringConstant.PAGE_HOME).forward(request, response);
        } catch (Exception e) {
            request.setAttribute(StringConstant.PARAMETER_ERROR_MESSAGE, "Check your credit data!");
        }
    }
}
