package com.interpreter.model.value;

import com.interpreter.model.types.IType;
import com.interpreter.model.types.StringType;

public class StringValue implements IValue{

    private String value;
    public StringValue(String val){ value = val; }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(IValue val){
        return val.getType().equals(new StringType()) && ((StringValue) val).getValue().equals(this.value) ;
    }

    public String toString(){
        return value;
    }
}
