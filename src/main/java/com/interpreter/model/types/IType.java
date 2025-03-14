package com.interpreter.model.types;
import com.interpreter.model.value.IValue;

public interface IType {
    boolean equals(IType obj);
    IValue defaultValue();
}
