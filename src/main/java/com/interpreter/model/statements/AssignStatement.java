package com.interpreter.model.statements;
import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.expresions.IExpression;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;
import com.interpreter.model.value.IValue;

public class AssignStatement implements IStatement{
    private final String variableName;
    private final IExpression expression;
    public AssignStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException {
        if (!prgState.getSymTable().contains(this.variableName)) {
            throw new StatementException("Variable was not found");
        }
        IValue value = prgState.getSymTable().getValue(this.variableName);
        IValue evalValue = this.expression.eval(prgState.getSymTable(), prgState.getHeap());
        if (!value.getType().equals(evalValue.getType())){
            throw new StatementException("Value type mismatch");
        }
        prgState.getSymTable().insert(this.variableName, evalValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(this.variableName,  this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
    try {
        IType typeVar = typeEnv.getValue(this.variableName);
        IType typeExp = this.expression.typeCheck(typeEnv);
        if (!typeVar.equals(typeExp)) {
            throw new StatementException("Assignment: right hand side and left hand side have different types");
        }
        return typeEnv;
    } catch (ADTException | ExpressionException e) {
        throw new StatementException(e.getMessage());
    }
    }

    @Override
    public String toString(){
        return this.variableName + " = " + this.expression.toString();
    }
}
