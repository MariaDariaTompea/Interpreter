package com.interpreter.model.expresions;


import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.adt.MyIHeap;
import com.interpreter.model.types.IType;
import com.interpreter.model.value.IValue;

public class VariableExpression implements IExpression {

    private final String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException {
        return symTbl.getValue(variable);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(variable);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        try {
            return typeEnv.getValue(variable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toString(){
        return variable;
    }
}
