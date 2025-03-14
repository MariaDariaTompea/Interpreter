package com.interpreter.model.expresions;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.adt.MyIHeap;
import com.interpreter.model.types.BoolType;
import com.interpreter.model.types.IType;
import com.interpreter.model.value.BoolValue;
import com.interpreter.model.value.IValue;

public class NotExpression implements IExpression {
    private final IExpression exp;

    public NotExpression(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue val = exp.eval(symTbl, heap);
        if (val.getType().equals(new BoolType())) {
            BoolValue boolVal = (BoolValue) val;
            return new BoolValue(!boolVal.getValue());
        } else {
            throw new ExpressionException("Expression is not of type bool");
        }
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type = exp.typeCheck(typeEnv);
        if (type.equals(new BoolType())) {
            return new BoolType();
        } else {
            throw new ExpressionException("Expression is not of type bool");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new NotExpression(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "!" + exp.toString();
    }
}