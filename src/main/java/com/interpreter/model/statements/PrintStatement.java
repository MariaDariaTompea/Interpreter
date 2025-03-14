package com.interpreter.model.statements;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.expresions.IExpression;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;
import com.interpreter.model.value.IValue;

public class PrintStatement implements IStatement{
    private final IExpression expression;
    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws ADTException, ExpressionException {
        IValue result = this.expression.eval(prgState.getSymTable(), prgState.getHeap());
        prgState.getOutput().add(result.toString());
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(this.expression);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        this.expression.typeCheck(typeEnv);
        return typeEnv;
    }

    public String toString(){
        return "print(" + expression.toString() + ")";
    }
}
