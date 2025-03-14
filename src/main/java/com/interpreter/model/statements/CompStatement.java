package com.interpreter.model.statements;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;

public class CompStatement implements IStatement{
    private final IStatement statement1;
    private final IStatement statement2;

    public CompStatement(IStatement statement1, IStatement statement2) {
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ADTException {
        prgState.getExeStack().push(statement2);
        prgState.getExeStack().push(statement1);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompStatement(this.statement1, this.statement2);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        return statement2.typeCheck(statement1.typeCheck(typeEnv));
    }

    @Override
    public String toString() {
        return statement1.toString() + ";" + statement2.toString();
    }

}
