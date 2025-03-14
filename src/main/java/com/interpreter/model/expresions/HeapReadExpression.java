package com.interpreter.model.expresions;


import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.adt.MyIHeap;
import com.interpreter.model.types.IType;
import com.interpreter.model.types.RefType;
import com.interpreter.model.value.IValue;
import com.interpreter.model.value.RefValue;

public class HeapReadExpression implements IExpression {
    private final IExpression expression;

    public HeapReadExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap hp) throws ExpressionException, ADTException {
        IValue value = expression.eval(tbl, hp);

        //check if the expression is of RefType
        if (!(value instanceof RefValue refValue)) {
            throw new ExpressionException("Expression is not of RefType");
        }
        //get the address from the RefValue
        int address = refValue.getAddress();
        //extract from the heap the value at the address
        if (!hp.getContent().containsKey(address)) {
            throw new ExpressionException("Address not found in heap");
        }

        return hp.get(((RefValue) value).getAddress());
    }

    @Override
    public IExpression deepCopy() {
        return new HeapReadExpression(expression.deepCopy());
    }


    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type = expression.typeCheck(typeEnv);
        if (type instanceof RefType refType) {
            return refType.getInner();
        } else {
            throw new ExpressionException("Expression is not of RefType");
        }
    }

    @Override
    public String toString() {
        return "HeapRead(" + expression.toString() + ")";
    }
}