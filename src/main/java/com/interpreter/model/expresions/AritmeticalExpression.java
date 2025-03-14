package com.interpreter.model.expresions;


import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.adt.MyIHeap;
import com.interpreter.model.types.IType;
import com.interpreter.model.types.IntType;
import com.interpreter.model.value.IValue;
import com.interpreter.model.value.IntValue;

public class AritmeticalExpression implements IExpression {
    private final IExpression left;
    private final IExpression right;
    private final AritmeticalOperator operator;

    public AritmeticalExpression(IExpression left, AritmeticalOperator operator, IExpression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue value1 = left.eval(symTbl, heap);
        IValue value2 = right.eval(symTbl, heap);
        if (!value1.getType().equals(new IntType())) {
            throw new ExpressionException("First value is not int");
        }
        if (!value2.getType().equals(new IntType())) {
            throw new ExpressionException("Second value is not int");
        }

        IntValue int1 = (IntValue) value1;
        IntValue int2 = (IntValue) value2;

        switch (operator) {
            case ADD:
                return new IntValue(int1.getValue() + int2.getValue());
            case SUBTRACT:
                return new IntValue(int1.getValue() - int2.getValue());
            case MULTIPLY:
                return new IntValue(int1.getValue() * int2.getValue());
            case DIVIDE:
            {
                if(int2.getValue() == 0)
                    throw new ExpressionException("Divide by zero");
                return new IntValue(int1.getValue() / int2.getValue());
            }
            default:
                throw new ExpressionException("Unknown operator");

        }

    }

    @Override
    public IExpression deepCopy() {
        return new AritmeticalExpression(this.left.deepCopy(),  this.operator,  this.right.deepCopy()).deepCopy();
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type1, type2;
        type1 = left.typeCheck(typeEnv);
        type2 = right.typeCheck(typeEnv);

        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else {
                throw new ExpressionException("Second operand is not an integer.");
            }
        } else {
            throw new ExpressionException("First operand is not an integer.");
        }
    }

    @Override
    public String toString() {
        switch (operator) {
            case ADD:
                return this.left.toString() + " + " + this.right.toString();
            case SUBTRACT:
                return this.left.toString() + " - " + this.right.toString();
            case MULTIPLY:
                return this.left.toString() + " * " + this.right.toString();
            case DIVIDE:
                return this.left.toString() + " / " + this.right.toString();
            default:
                return "Unknown operator";
        }
    }
}