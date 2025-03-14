package com.interpreter.model.expresions;


import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.adt.MyIHeap;
import com.interpreter.model.types.IType;
import com.interpreter.model.value.IValue;

public interface IExpression {
    IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException;
    IExpression deepCopy();
    IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException;

}
