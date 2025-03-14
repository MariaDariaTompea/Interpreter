package com.interpreter.model.value;

import com.interpreter.model.types.IType;
import com.interpreter.model.types.IntType;

public class IntValue implements IValue{
    private final int value;

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    public boolean equals(IValue other) {
        return other instanceof IntValue && ((IntValue) other).value == this.value;
    }

    public int getValue(){
        return value;
    }

    @Override
    public String toString(){
        return Integer.toString(value);
    }
}
