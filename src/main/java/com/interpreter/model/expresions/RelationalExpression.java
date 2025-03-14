package com.interpreter.model.expresions;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.adt.MyIHeap;
import com.interpreter.model.types.IType;
import com.interpreter.model.types.IntType;
import com.interpreter.model.value.BoolValue;
import com.interpreter.model.value.IValue;
import com.interpreter.model.value.IntValue;

public class RelationalExpression implements IExpression {
    private final IExpression exp1;
    private final IExpression exp2;
    private final String operator;

    public RelationalExpression(IExpression exp1, IExpression exp2, String operator) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ExpressionException, ADTException {
        IValue val1 = exp1.eval(symTbl, heap);
        if (!val1.getType().equals(new IntType())) {
            throw new ExpressionException("First operand is not an integer.");
        }

        IValue val2 = exp2.eval(symTbl, heap);
        if (!val2.getType().equals(new IntType())) {
            throw new ExpressionException("Second operand is not an integer.");
        }

        IntValue intVal1 = (IntValue) val1;
        IntValue intVal2 = (IntValue) val2;

        boolean result;
        switch (operator) {
            case "<":
                result = intVal1.getValue() < intVal2.getValue();
                break;
            case "<=":
                result = intVal1.getValue() <= intVal2.getValue();
                break;
            case "==":
                result = intVal1.getValue() == intVal2.getValue();
                break;
            case "!=":
                result = intVal1.getValue() != intVal2.getValue();
                break;
            case ">":
                result = intVal1.getValue() > intVal2.getValue();
                break;
            case ">=":
                result = intVal1.getValue() >= intVal2.getValue();
                break;
            default:
                throw new ExpressionException("Invalid operator.");
        }

        return new BoolValue(result);
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + operator + " " + exp2.toString();
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(exp1.deepCopy(), exp2.deepCopy(), operator);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type1 = exp1.typeCheck(typeEnv);
        IType type2 = exp2.typeCheck(typeEnv);

        if (!type1.equals(new IntType())) {
            throw new ExpressionException("First operand is not an integer.");
        }

        if (!type2.equals(new IntType())) {
            throw new ExpressionException("Second operand is not an integer.");
        }

        return new IntType();
    }
}