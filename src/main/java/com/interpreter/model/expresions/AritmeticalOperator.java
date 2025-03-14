package com.interpreter.model.expresions;

public enum AritmeticalOperator { ADD, SUBTRACT, MULTIPLY, DIVIDE;

    @Override
    public String toString() {
        switch (this) {
            case ADD:
                return "+";
            case SUBTRACT:
                return "-";
            case MULTIPLY:
                return "*";
            case DIVIDE:
                return "/";
            default:
                return "";
        }
    }
}
