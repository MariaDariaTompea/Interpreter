package com.interpreter.model.expresions;


import com.interpreter.exceptions.ExpressionException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.adt.MyIHeap;
import com.interpreter.model.types.IType;
import com.interpreter.model.value.IValue;

public class ValueExpression implements IExpression {

    private final IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) {
        return value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        return value.getType();
    }

    public String toString(){
        return value.toString();
    }
}
