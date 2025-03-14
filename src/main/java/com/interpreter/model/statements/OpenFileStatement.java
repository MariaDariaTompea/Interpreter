package com.interpreter.model.statements;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.expresions.IExpression;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;
import com.interpreter.model.types.StringType;
import com.interpreter.model.value.IValue;
import com.interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenFileStatement implements IStatement{
    private final IExpression expression;

    public OpenFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException, ExpressionException, IOException {
        IValue value = expression.eval(state.getSymTable(), state.getHeap());

        if (!value.getType().equals(new StringType())) {
            throw new StatementException("The expression is not a string");
        }

        StringValue fileName = (StringValue) value;

        if (state.getFileTable().contains(fileName)) {
            throw new StatementException("The file already exists");
        }

        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(fileName.getValue()));
        } catch (IOException ioException) {
            throw new StatementException(String.format("File %s could not be opened", fileName));
        }

        state.getFileTable().insert(fileName, bufferedReader);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenFileStatement(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType expressionType = null;
        try {
            expressionType = expression.typeCheck(typeEnv);
        } catch (ExpressionException e) {
            throw new RuntimeException(e);
        }
        if (!expressionType.equals(new StringType())) {
            throw new StatementException("Open file: expression is not a string");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "OpenFile{" +
                "expression=" + expression +
                '}';
    }
}
