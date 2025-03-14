package com.interpreter.model.value;

import com.interpreter.model.types.IType;
import com.interpreter.model.types.RefType;

public class RefValue implements IValue {
    int address;
    IType locationType;


    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }


    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(IValue other) {
        if (other instanceof RefValue) {
            return address == ((RefValue) other).getAddress() && locationType.equals(((RefValue) other).getLocationType());
        }
        return false;
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }
}
