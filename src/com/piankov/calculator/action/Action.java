package com.piankov.calculator.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
    void performAction(HttpServletRequest request, HttpServletResponse response);
}
