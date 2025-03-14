package com.interpreter.model.statements;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.adt.MyIStack;
import com.interpreter.model.adt.MyStack;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;

import java.io.IOException;

public class ForkStatement implements IStatement {

    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }


    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException, IOException {
        MyIStack<IStatement> newExecutionStack = new MyStack<>();
        PrgState state = new PrgState(prgState.getSymTable().clone(), newExecutionStack, prgState.getOutput(), statement, prgState.getFileTable(), prgState.getHeap());
        return state;

    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        return statement.typeCheck(typeEnv);
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}
