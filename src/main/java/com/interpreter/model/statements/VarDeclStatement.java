package com.interpreter.model.statements;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;

import java.io.IOException;

public class VarDeclStatement implements IStatement {
    private final String name;
    private final IType type;

    public VarDeclStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException, IOException {
        prgState.getSymTable().insert(name, type.defaultValue());
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStatement(this.name, this.type);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
    typeEnv.insert(name, type);
    return typeEnv;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
