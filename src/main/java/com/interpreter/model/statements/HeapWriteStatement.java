package com.interpreter.model.statements;

import com.interpreter.exceptions.ADTException;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.adt.MyIHeap;
import com.interpreter.model.expresions.IExpression;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.types.IType;
import com.interpreter.model.types.RefType;
import com.interpreter.model.value.IValue;
import com.interpreter.model.value.RefValue;

import java.io.IOException;

public class HeapWriteStatement implements IStatement{
    private final String varName;
    private final IExpression expression;

    public HeapWriteStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }


    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException, IOException {
        //check if the variable is in the symbol table
        if(!prgState.getSymTable().contains(varName)){
            throw new StatementException("Variable not found");
        }

        //check if the variable is a reference type
        IValue varValue = prgState.getSymTable().getValue(varName);

        if(!(varValue.getType() instanceof RefType)){
            throw new StatementException("Variable is not a reference type");
        }


        //try to get the value from the heap at the address
        //check if the address is valid
        MyIHeap heap = prgState.getHeap();
        int address;
        try{
            address = ((RefValue)varValue).getAddress();
            heap.get(address);
        }
        catch (Exception e){
            throw new StatementException("Invalid address");
        }

        //evaluate the expression
        IValue expressionValue = expression.eval(prgState.getSymTable(), heap);
        //check if the exoression type matches the location type
        IType locationType = expressionValue.getType();

        if(!locationType.equals(((RefType)varValue.getType()).getInner())){
            throw new StatementException("Types do not match");
        }

        //put in the heap the value of the expression
        heap.update(address, expressionValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
    try {
        IType varType = typeEnv.getValue(varName);
        IType expType = expression.typeCheck(typeEnv);

        if (!varType.equals(new RefType(expType))) {
            throw new StatementException("Heap write: right hand side and left hand side have different types.");
        }

        return typeEnv;
    } catch (ADTException | ExpressionException e) {
        throw new StatementException(e.getMessage());
    }
    }

    @Override
    public String toString() {
        return "HeapWriteStatement{" +
                "varName='" + varName + '\'' +
                ", expression=" + expression +
                '}';
    }


}
