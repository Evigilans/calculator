package com.piankov.calculator.controller;

import com.piankov.calculator.action.Action;

class ActionDefiner {
    Action defineAction(String commandName) {
        return commandName == null ? null : ActionType.valueOf(commandName.toUpperCase()).getCommand();
    }
}
