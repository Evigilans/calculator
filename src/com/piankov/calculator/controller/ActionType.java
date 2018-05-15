package com.piankov.calculator.controller;

import com.piankov.calculator.action.Action;
import com.piankov.calculator.action.CalculatorAction;
import com.piankov.calculator.action.CreditAction;

public enum ActionType {
    CALCULATOR(new CalculatorAction()),
    CREDIT(new CreditAction());

    private final Action action;

    ActionType(Action action) {
        this.action = action;
    }

    public Action getCommand() {
        return action;
    }
}
