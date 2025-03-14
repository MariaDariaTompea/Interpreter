package com.interpreter.model.value;

import com.interpreter.model.types.IType;

public interface IValue {
    IType getType();
    boolean equals(IValue other);

    IValue defaultValue();
}
