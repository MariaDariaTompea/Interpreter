package com.interpreter.model.types;

import com.interpreter.model.value.IValue;
import com.interpreter.model.value.IntValue;

public class IntType implements IType{
    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(IType obj) {
        return obj instanceof IntType;
    }

    public String toString(){
        return "int";
    }
}
