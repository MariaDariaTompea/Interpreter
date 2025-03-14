package com.interpreter.model.statements;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.expresions.IExpression;
import com.interpreter.model.expresions.RelationalExpression;
import com.interpreter.model.expresions.VariableExpression;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IntType;
import com.interpreter.model.types.IType;
import com.interpreter.model.value.IValue;
import com.interpreter.model.value.IntValue;

public class ForStatement implements IStatement {
    private final String varName;
    private final IExpression exp1;
    private final IExpression exp2;
    private final IExpression exp3;
    private final IStatement stmt;

    public ForStatement(String varName, IExpression exp1, IExpression exp2, IExpression exp3, IStatement stmt) {
        this.varName = varName;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException, ExpressionException {
        if (!state.getSymTable().contains(varName)) {
            state.getSymTable().insert(varName, new IntValue(0));
        }

        IValue val1 = exp1.eval(state.getSymTable(), state.getHeap());
        if (!(val1.getType() instanceof IntType)) {
            throw new StatementException("exp1 is not of type int");
        }

        IValue val2 = exp2.eval(state.getSymTable(), state.getHeap());
        if (!(val2.getType() instanceof IntType)) {
            throw new StatementException("exp2 is not of type int");
        }

        IValue val3 = exp3.eval(state.getSymTable(), state.getHeap());
        if (!(val3.getType() instanceof IntType)) {
            throw new StatementException("exp3 is not of type int");
        }

        IStatement newStmt = new CompStatement(
                new AssignStatement(varName, exp1),
                new WhileStatement(
                        new RelationalExpression(new VariableExpression(varName), exp2, "<"),
                        new CompStatement(stmt, new AssignStatement(varName, exp3))
                )
        );

        state.getExeStack().push(newStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        if (!typeEnv.contains(varName)) {
            typeEnv.insert(varName, new IntType()); // Declare the variable in the type environment if not already declared
        }

        IType type1 = exp1.typeCheck(typeEnv);
        IType type2 = exp2.typeCheck(typeEnv);
        IType type3 = exp3.typeCheck(typeEnv);

        if (!type1.equals(new IntType())) {
            throw new StatementException("exp1 is not of type int");
        }
        if (!type2.equals(new IntType())) {
            throw new StatementException("exp2 is not of type int");
        }
        if (!type3.equals(new IntType())) {
            throw new StatementException("exp3 is not of type int");
        }

        stmt.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new ForStatement(varName, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "for(" + varName + "=" + exp1.toString() + ";" + varName + "<" + exp2.toString() + ";" + varName + "=" + exp3.toString() + ") " + stmt.toString();
    }
}