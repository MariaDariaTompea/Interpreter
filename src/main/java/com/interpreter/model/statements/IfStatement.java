package com.interpreter.model.statements;


import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.expresions.IExpression;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.BoolType;
import com.interpreter.model.types.IType;
import com.interpreter.model.value.BoolValue;
import com.interpreter.model.value.IValue;

public class IfStatement implements IStatement {
    private final IStatement statementThan;
    private final IStatement statementElse;
    private final IExpression expression;

    public IfStatement(IStatement statementThan, IStatement statementElse, IExpression expression) {
        this.statementThan = statementThan;
        this.statementElse = statementElse;
        this.expression = expression;
    }

    public PrgState execute(PrgState state) throws StatementException, ADTException, ExpressionException {
        IValue value = expression.eval(state.getSymTable(), state.getHeap());
        if(!value.getType().equals(new BoolType())){
            throw new StatementException("Expression is not boolean");
        }
        if(((BoolValue) value).getValue()){
            state.getExeStack().push(statementThan);
        }
        else {
            state.getExeStack().push(statementElse);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(this.statementThan.deepCopy(),  this.statementElse.deepCopy(),  this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType expressionType = expression.typeCheck(typeEnv);
        if (!expressionType.equals(new BoolType())) {
            throw new StatementException("If statement: expression is not a boolean");
        }
        statementThan.typeCheck(typeEnv);
        statementElse.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "if(" + expression + "){" + statementThan + "}else{" + statementElse + "}\n";
    }

}
