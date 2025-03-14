package com.interpreter.view;
import com.interpreter.controller.Controller;
import com.interpreter.model.adt.*;
import com.interpreter.model.expresions.AritmeticalExpression;
import com.interpreter.model.expresions.AritmeticalOperator;
import com.interpreter.model.expresions.ValueExpression;
import com.interpreter.model.expresions.VariableExpression;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.statements.*;
import com.interpreter.model.types.BoolType;
import com.interpreter.model.types.IntType;
import com.interpreter.model.types.StringType;
import com.interpreter.model.value.BoolValue;
import com.interpreter.model.value.IValue;
import com.interpreter.model.value.IntValue;
import com.interpreter.model.value.StringValue;
import com.interpreter.repository.Repository;

import java.io.BufferedReader;
import java.util.Scanner;

public class View implements IView {

    public View() {
        super();
    }

    @Override
    public void run() {
        //get user input to chose the program to run
        int option;
        System.out.println("Choose the program you want to run: ");
        Scanner scanner = new Scanner(System.in);
        option = scanner.nextInt();
        switch (option) {
            case 1:
                testProgram1();
                break;

            case 2:
                testProgram2();
                break;

            case 3:
                testProgram3();
                break;

            case 4:
                testProgram4();
                break;

            default:
                System.out.println("Invalid option");
                break;
        }


    }

    public void testProgram1() {
        //create a new program state,stack symbol table etc
        Repository repo = new Repository("log1.txt");
        ///v=2;print(v)
        IStatement ex1 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        MyIStack<IStatement> executionStack = new MyStack<IStatement>(); ///stack de executie gol
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();///tabela cu valori si variabile
        MyIList<String> out = new MyList<>();///lista de output
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();///tabela de fisiere pentru stringuri
        MyHeap heap = new MyHeap();
        PrgState prg = new PrgState(symTable, executionStack, out, ex1, fileTable,heap);///program state
        //add the program state to the repository
        repo.addPrgState(prg);
        Controller ctrl = new Controller(repo);
        try {
            ctrl.allStep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void testProgram2() {
        //create a new program state,stack symbol table etc
        Repository repo = new Repository("log2.txt");
        IStatement ex2 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                        new CompStatement(new AssignStatement("a", new AritmeticalExpression(new ValueExpression(new IntValue(2)), AritmeticalOperator.ADD, new
                                AritmeticalExpression(new ValueExpression(new IntValue(3)), AritmeticalOperator.MULTIPLY, new ValueExpression(new IntValue(5))))),
                                new CompStatement(new AssignStatement("b", new AritmeticalExpression(new VariableExpression("a"), AritmeticalOperator.ADD, new ValueExpression(new
                                        IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        MyIStack<IStatement> executionStack = new MyStack<IStatement>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<String> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyHeap heap = new MyHeap();
        PrgState prg = new PrgState(symTable, executionStack, out, ex2, fileTable,heap);
        //add the program state to the repository
        repo.addPrgState(prg);
        Controller ctrl = new Controller(repo);
        try {
            ctrl.allStep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void testProgram3() {
        //create a new program state,stack symbol table etc
        Repository repo = new Repository("log3.txt");
        IStatement ex3 = new CompStatement(new VarDeclStatement("a", new BoolType()),
                new CompStatement(new VarDeclStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new AssignStatement("v", new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3))), new VariableExpression("a")), new PrintStatement(new
                                        VariableExpression("v"))))));
        MyIStack<IStatement> executionStack = new MyStack<IStatement>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<String> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyHeap heap = new MyHeap();
        PrgState prg = new PrgState(symTable, executionStack, out, ex3, fileTable,heap);
        //add the program state to the repository
        repo.addPrgState(prg);
        Controller ctrl = new Controller(repo);
        try {
            ctrl.allStep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void testProgram4() {
    String fileName = "test.in";
    String stringWithFileNameVariable = "varf";
    String intVariableName = "varc";

    IStatement ex4 = new CompStatement(new VarDeclStatement(stringWithFileNameVariable, new StringType()),
            new CompStatement(new AssignStatement(stringWithFileNameVariable, new ValueExpression(new StringValue(fileName))),
                    new CompStatement(new OpenFileStatement(new VariableExpression(stringWithFileNameVariable)),
                            new CompStatement(new VarDeclStatement(intVariableName, new IntType()),
                                    new CompStatement(new ReadFileStatement(new VariableExpression(stringWithFileNameVariable), intVariableName),
                                            new CompStatement(new PrintStatement(new VariableExpression(intVariableName)),
                                                    new CompStatement(new ReadFileStatement(new VariableExpression(stringWithFileNameVariable), intVariableName),
                                                            new CompStatement(new PrintStatement(new VariableExpression(intVariableName)),
                                                                    new CloseFileStatement(new VariableExpression(stringWithFileNameVariable))))))))));

    MyIStack<IStatement> executionStack = new MyStack<IStatement>();
    MyIDictionary<String, IValue> symTable = new MyDictionary<>();
    MyIList<String> out = new MyList<>();
    MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
    MyHeap heap = new MyHeap();
    PrgState prg = new PrgState(symTable, executionStack, out, ex4, fileTable,heap);
    Repository repo = new Repository("log4.txt");
    repo.addPrgState(prg);
    Controller ctrl = new Controller(repo);
    try {
        ctrl.allStep();
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}

}
