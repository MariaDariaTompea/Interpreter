package com.interpreter.model.types;

import com.interpreter.model.value.BoolValue;
import com.interpreter.model.value.IValue;

public class BoolType implements IType{
    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(IType obj) {
        return obj instanceof BoolType;
    }

    public String toString() {
        return "bool";
    }
}
