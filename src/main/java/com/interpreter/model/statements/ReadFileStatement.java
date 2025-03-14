package com.interpreter.model.statements;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.expresions.IExpression;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;
import com.interpreter.model.types.IntType;
import com.interpreter.model.types.StringType;
import com.interpreter.model.value.IntValue;
import com.interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{

    private final IExpression exp;
    private final String varName;

    public ReadFileStatement(IExpression exp, String varName){
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException, IOException {
        var table = prgState.getSymTable();

        if(!table.contains(varName)){
            throw new StatementException("The variable was not defined");
        }

        if(!table.getValue(varName).getType().equals(new IntType())){
            throw new StatementException("The type is incorrect");
        }

        var res = exp.eval(table, prgState.getHeap());

        if(!res.getType().equals(new StringType())){
            throw new StatementException("The result is not a String Type!");
        }

        BufferedReader reader = prgState.getFileTable().getValue((StringValue)res);

        String read = reader.readLine();

        if(read.equals("")){
            read = "0";
        }

        int parser = Integer.parseInt(read);

        table.insert(varName, new IntValue(parser));

        return null;

    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType type = null;
        try {
            type = exp.typeCheck(typeEnv);
        } catch (ExpressionException e) {
            throw new RuntimeException(e);
        }

        if(!type.equals(new StringType())){
            throw new StatementException("The expression is not a string");
        }

        return typeEnv;
    }

    @Override
    public String toString(){
        return "ReadFile(" + exp.toString() + ", " + varName + ")";
    }
}
