package com.interpreter.model.state;

import com.interpreter.model.adt.MyIDictionary;
import com.interpreter.model.adt.MyIHeap;
import com.interpreter.model.adt.MyIList;
import com.interpreter.model.adt.MyIStack;
import com.interpreter.model.statements.IStatement;
import com.interpreter.model.value.IValue;
import com.interpreter.model.value.StringValue;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.io.BufferedReader;

public class PrgState {
    private static final Map<Integer,Boolean> ids = new HashMap<>();
    private static int id;

    private static synchronized int generateId() {
        Random random = new Random();
        Integer generatedId;
        do {
            generatedId = random.nextInt(0, 100);
        }
        while(ids.containsKey(generatedId));

        ids.put(generatedId,true);
        return generatedId;
    }

    private MyIDictionary<String, IValue> symTable;
    private MyIStack<IStatement> exeStack;
    private MyIList<String> output;
    private final IStatement initialState;
    private MyIHeap heap;
    private final MyIDictionary<StringValue, BufferedReader> fileTable;

    public PrgState(MyIDictionary<String, IValue> symTable,
                    MyIStack<IStatement> exeStack,
                    MyIList<String> output,
                    IStatement initialState,
                    MyIDictionary<StringValue, BufferedReader> fileTable,
                    MyIHeap heap) {
        id = generateId();
        this.symTable = symTable;
        this.exeStack = exeStack;
        this.output = output;
        this.initialState = initialState.deepCopy();
        this.exeStack.push(this.initialState);
        this.fileTable = fileTable;
        this.heap = heap;
    }

    public static synchronized int getId() {
        return id;
    }


    public static synchronized void setId(int id) {
        PrgState.id = id;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }


    public PrgState oneStep() throws Exception {
        if (exeStack.isEmpty()) {
            throw new Exception("Execution stack is empty");
        }
        IStatement currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public MyIStack<IStatement> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap heap) {
        this.heap = heap;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIList<String> getOutput() {
        return output;
    }

    public void setOutput(MyIList<String> output) {
        this.output = output;
    }

    public String fileTableToString() {
        StringBuilder text = new StringBuilder();
        text.append("FileTable: \n");
        for (StringValue key : this.fileTable.getKeys()) {
            text.append(key).append("\n");
        }
        return text.toString();
    }

    @Override
    public String toString() {
        return "Program ID:" + id + "\n" + symTable.toString() + "\n" + exeStack.toString() + "\n" + output.toString() + "\n" + fileTableToString() + "\n" + heap.toString();
    }
}