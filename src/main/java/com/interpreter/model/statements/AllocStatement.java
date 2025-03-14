package com.interpreter.model.statements;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.expresions.IExpression;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;
import com.interpreter.model.types.RefType;
import com.interpreter.model.value.RefValue;

import java.io.IOException;
//*p=1+1
public class AllocStatement implements IStatement{
    private final String varName;
    private final IExpression expression;

    public AllocStatement(String varName, IExpression expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException, IOException {
        //get the symbol table
        var symbolTable = prgState.getSymTable();
        //get the heap
        var heap = prgState.getHeap();

        //check if the variable is in the symbol table
        if(!symbolTable.contains(varName)){
            throw new StatementException("Variable not in symbol table");
        }

        //get the value of the variable
        var value = symbolTable.getValue(varName);

        //check if the value is a reference type
        if(!(value.getType() instanceof RefType)){
            throw new StatementException("Value is not a reference value");
        }

        //evaluate the expression
        var evaluatedExpression = expression.eval(symbolTable, heap);

        IType locationType = ((RefType)value.getType()).getInner();

        //check if the types match
        if(!evaluatedExpression.getType().equals(locationType)){
            throw new StatementException("Types do not match");
        }

        //add the value to the heap
        var address = heap.add(evaluatedExpression);
        //update the value in the symbol table
        symbolTable.insert(varName, new RefValue(address, locationType));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AllocStatement(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
    try {
        IType varType = typeEnv.getValue(varName);
        IType expType = expression.typeCheck(typeEnv);

        if (!varType.equals(new RefType(expType))) {
            throw new StatementException("Types do not match");
        }

        return typeEnv;
    } catch (ADTException | ExpressionException e) {
        throw new StatementException(e.getMessage());
    }
}

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression.toString() + ")";
    }
}
