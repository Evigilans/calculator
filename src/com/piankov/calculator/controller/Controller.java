package com.piankov.calculator.controller;

import com.piankov.calculator.action.Action;
import com.piankov.calculator.constant.StringConstant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String modeName = req.getParameter(StringConstant.MODE_PARAMETER);

        ActionDefiner actionDefiner = new ActionDefiner();
        Action action = actionDefiner.defineAction(modeName);

        action.performAction(req, resp);
    }
}
