package com.interpreter.model.statements;

import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.BoolType;
import com.interpreter.model.value.IValue;
import com.interpreter.model.expresions.IExpression;
import com.interpreter.model.expresions.NotExpression;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.types.IType;

public class RepeatStatement implements IStatement {
    private final IStatement stmt;
    private final IExpression exp;

    public RepeatStatement(IStatement stmt, IExpression exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        state.getExeStack().push(new CompStatement(stmt, new WhileStatement(new NotExpression(exp), stmt)));
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType expressionType = exp.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new RepeatStatement(stmt.deepCopy(), exp.deepCopy());
    }

    @Override
    public String toString() {
        return "repeat " + stmt.toString() + " until " + exp.toString();
    }
}