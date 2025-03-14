package com.interpreter.model.expresions;


import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.adt.MyIHeap;
import com.interpreter.model.types.BoolType;
import com.interpreter.model.types.IType;
import com.interpreter.model.value.BoolValue;
import com.interpreter.model.value.IValue;

public class LogicalExpression implements IExpression {
    private final IExpression left;
    private final IExpression right;
    private final LogicalOperator operator;

    public LogicalExpression(IExpression left, LogicalOperator operator, IExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue evaluatedExpressionLeft = left.eval(symTbl, heap);
        IValue evaluatedExpressionRight = right.eval(symTbl, heap);
        if (!evaluatedExpressionLeft.getType().equals(new BoolType())) {
            throw new ExpressionException("Left expression is not of type BoolType");
        }
        if (!evaluatedExpressionRight.getType().equals(new BoolType())) {
            throw new ExpressionException("Right expression is not of type BoolType");
        }
        switch (operator) {
            case AND:
                return new BoolValue(((BoolValue) evaluatedExpressionLeft).getValue() &&
                        ((BoolValue) evaluatedExpressionRight).getValue());
            case OR:
                return new BoolValue(((BoolValue) evaluatedExpressionLeft).getValue() ||
                        ((BoolValue) evaluatedExpressionRight).getValue());
            default:
                throw new ExpressionException("Unknown operator");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new LogicalExpression(left.deepCopy(), operator, right.deepCopy()).deepCopy();
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type1 = left.typeCheck(typeEnv);
        IType type2 = right.typeCheck(typeEnv);
        if (!type1.equals(new BoolType())) {
            throw new ExpressionException("First operand is not a boolean.");
        }
        if (!type2.equals(new BoolType())) {
            throw new ExpressionException("Second operand is not a boolean.");
        }
        return new BoolType();
    }

    @Override
    public String toString() {
        switch (operator){
            case AND:
                return left.toString() + " && " + right.toString();
            case OR:
                return left.toString() + " || " + right.toString();
            default:
                return "Unknown operator";
        }

    }
}
