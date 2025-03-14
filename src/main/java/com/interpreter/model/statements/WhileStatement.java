package com.interpreter.model.statements;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.expresions.IExpression;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;
import com.interpreter.model.value.BoolValue;
import com.interpreter.model.value.IValue;

import java.io.IOException;

public class WhileStatement implements IStatement {
    private final IExpression expression;
    private final IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException, IOException {
        IValue condition = expression.eval(prgState.getSymTable(), prgState.getHeap());

        if (condition instanceof BoolValue boolCondition) {
            if (boolCondition.getValue()) {
                prgState.getExeStack().push(this);
                prgState.getExeStack().push(statement);
            }
        } else {
            throw new StatementException("Condition expression is not a boolean");
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType expressionType = expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ") " + statement.toString();
    }
}
