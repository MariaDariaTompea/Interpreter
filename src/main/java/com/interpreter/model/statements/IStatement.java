package com.interpreter.model.statements;
import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;

import java.io.IOException;

public interface IStatement {
    PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException, IOException;
    IStatement deepCopy();
    MyIDictionary <String, IType> typeCheck(MyIDictionary <String, IType> typeEnv) throws StatementException, ExpressionException;

}
