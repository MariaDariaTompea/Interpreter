package com.interpreter.model.statements;

import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;

public class NopStatement implements IStatement {

    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}
