package com.interpreter.model.types;

import com.interpreter.model.value.IValue;
import com.interpreter.model.value.RefValue;

public class RefType implements IType{
    private final IType inner;

    public RefType(IType inner){
        this.inner = inner;
    }

    public IType getInner(){
        return inner;
    }

    @Override
    public boolean equals(IType obj) {
        if(obj instanceof RefType){
            return inner.equals(((RefType) obj).getInner());
        }
        return false;
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0,inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner + ")";
    }
}
