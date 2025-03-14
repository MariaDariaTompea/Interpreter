package com.interpreter.model.types;

import com.interpreter.model.value.IValue;
import com.interpreter.model.value.StringValue;

public class StringType implements IType{
    public StringType(){

    }
    public String toString(){
        return "String";
    }

    @Override
    public boolean equals(IType obj) {
        return obj instanceof StringType;
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }
}
