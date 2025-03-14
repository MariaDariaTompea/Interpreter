package com.interpreter.repository;

import com.interpreter.controller.Controller;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.model.adt.MyDictionary;
import com.interpreter.model.adt.MyHeap;
import com.interpreter.model.adt.MyList;
import com.interpreter.model.adt.MyStack;
import com.interpreter.model.expresions.*;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.statements.*;
import com.interpreter.model.types.BoolType;
import com.interpreter.model.types.IntType;
import com.interpreter.model.types.RefType;
import com.interpreter.model.types.StringType;
import com.interpreter.model.value.BoolValue;
import com.interpreter.model.value.IntValue;
import com.interpreter.model.value.StringValue;

import java.util.ArrayList;
import java.util.List;

public class ProgramList {
    public static List<Controller> getProgramList() throws StatementException, ExpressionException {
        ArrayList<Controller> controllers = new ArrayList<>();
        IStatement ex1 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        ex1.typeCheck(new MyDictionary<>());
        PrgState prg1 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyList<>(), ex1, new MyDictionary<>(), new MyHeap());
        IRepository repo1 = new Repository("log1.txt");
        repo1.addPrgState(prg1);
        Controller ctr1 = new Controller((Repository) repo1);


        //example 2
        IStatement ex2 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                        new CompStatement(new AssignStatement("a", new AritmeticalExpression(new ValueExpression(new IntValue(2)), AritmeticalOperator.ADD, new
                                AritmeticalExpression(new ValueExpression(new IntValue(3)), AritmeticalOperator.MULTIPLY, new ValueExpression(new IntValue(5))))),
                                new CompStatement(new AssignStatement("b", new AritmeticalExpression(new VariableExpression("a"), AritmeticalOperator.ADD, new ValueExpression(new
                                        IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        ex2.typeCheck(new MyDictionary<>());
        PrgState prg2 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyList<>(), ex2, new MyDictionary<>(), new MyHeap());
        IRepository repo2 = new Repository("log2.txt");
        repo2.addPrgState(prg2);
        Controller ctr2 = new Controller((Repository) repo2);

        //example 3
        IStatement ex3 = new CompStatement(new VarDeclStatement("a", new BoolType()),
                new CompStatement(new VarDeclStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new AssignStatement("v", new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3))), new VariableExpression("a")), new PrintStatement(new
                                        VariableExpression("v"))))));
        ex3.typeCheck(new MyDictionary<>());
        PrgState prg3 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyList<>(), ex3, new MyDictionary<>(), new MyHeap());
        IRepository repo3 = new Repository("log3.txt");
        repo3.addPrgState(prg3);
        Controller ctr3 = new Controller((Repository) repo3);

        //example 4
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
        ex4.typeCheck(new MyDictionary<>());
        PrgState prg4 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyList<>(), ex4, new MyDictionary<>(), new MyHeap());
        IRepository repo4 = new Repository("log4.txt");
        repo4.addPrgState(prg4);
        Controller ctr4 = new Controller((Repository) repo4);

        //example 5

        IStatement ex5 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new AllocStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new AllocStatement("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));
        ex5.typeCheck(new MyDictionary<>());
        PrgState prg5 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyList<>(), ex5, new MyDictionary<>(), new MyHeap());
        IRepository repo5 = new Repository("log5.txt");
        repo5.addPrgState(prg5);
        Controller ctr5 = new Controller((Repository) repo5);

        //example 6
        IStatement ex6 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new AllocStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new AllocStatement("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                                new PrintStatement(new AritmeticalExpression(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a"))), AritmeticalOperator.ADD, new ValueExpression(new IntValue(5)))))))));
        ex6.typeCheck(new MyDictionary<>());
        PrgState prg6 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyList<>(), ex6, new MyDictionary<>(), new MyHeap());
        IRepository repo6 = new Repository("log6.txt");
        repo6.addPrgState(prg6);
        Controller ctr6 = new Controller((Repository) repo6);
        //example 7 should do a memory violation
        //garbage collector should be implemented
        IStatement ex7 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new AllocStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new AllocStatement("a", new VariableExpression("v")),
                                        new CompStatement(new AllocStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a")))))))));

        //ex7.typeCheck(new MyDictionary<>());
        PrgState prg7 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyList<>(), ex7, new MyDictionary<>(), new MyHeap());
        IRepository repo7 = new Repository("log7.txt");
        repo7.addPrgState(prg7);
        Controller ctr7 = new Controller((Repository) repo7);

        //example 8
        //example 8
        IStatement ex8 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                                new CompStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v", new AritmeticalExpression(new VariableExpression("v"), AritmeticalOperator.SUBTRACT, new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));
        ex8.typeCheck(new MyDictionary<>());
        PrgState prg8 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyList<>(), ex8, new MyDictionary<>(), new MyHeap());
        IRepository repo8 = new Repository("log8.txt");
        repo8.addPrgState(prg8);
        Controller ctr8 = new Controller((Repository) repo8);

        //example 9
        IStatement ex9 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompStatement(new AllocStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompStatement(new ForkStatement(
                                                new CompStatement(new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                                                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))),
                                                new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a")))))))));
        ex9.typeCheck(new MyDictionary<>());
        PrgState prg9 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyList<>(), ex9, new MyDictionary<>(), new MyHeap());
        IRepository repo9 = new Repository("log9.txt");
        repo9.addPrgState(prg9);
        Controller ctr9 = new Controller((Repository) repo9);

        // Add this code to the `getProgramList` method in `ProgramList.java`

        // Program 10: int v; int x; int y; v=0; (repeat (fork(print(v);v--);v=v+1) until v==3); x=1;pop();y=3;nop; print(y*10)
        IStatement ex10 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new VarDeclStatement("x", new IntType()),
                        new CompStatement(new VarDeclStatement("y", new IntType()),
                                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(0))),
                                        new CompStatement(new RepeatStatement(
                                                new CompStatement(new ForkStatement(new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                        new AssignStatement("v", new AritmeticalExpression(new VariableExpression("v"), AritmeticalOperator.SUBTRACT, new ValueExpression(new IntValue(1)))))),
                                                        new AssignStatement("v", new AritmeticalExpression(new VariableExpression("v"), AritmeticalOperator.ADD, new ValueExpression(new IntValue(1))))),
                                                new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "==")),
                                                new CompStatement(new AssignStatement("x", new ValueExpression(new IntValue(1))),
                                                        new CompStatement(new NopStatement(),
                                                                new CompStatement(new AssignStatement("y", new ValueExpression(new IntValue(3))),
                                                                        new CompStatement(new NopStatement(),
                                                                                new PrintStatement(new AritmeticalExpression(new VariableExpression("y"), AritmeticalOperator.MULTIPLY, new ValueExpression(new IntValue(10)))))))))))));
        ex10.typeCheck(new MyDictionary<>());
        PrgState prg10 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyList<>(), ex10, new MyDictionary<>(), new MyHeap());
        IRepository repo10 = new Repository("log10.txt");
        repo10.addPrgState(prg10);
        Controller ctr10 = new Controller((Repository) repo10);

        // Program 11: Ref int a; new(a,20); int v; (for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a))); print(rh(a))
        IStatement ex11 = new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                new CompStatement(new AllocStatement("a", new ValueExpression(new IntValue(20))),
                        new CompStatement(new ForStatement("v", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(3)), new AritmeticalExpression(new VariableExpression("v"), AritmeticalOperator.ADD, new ValueExpression(new IntValue(1))),
                                new ForkStatement(new CompStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v", new AritmeticalExpression(new VariableExpression("v"), AritmeticalOperator.MULTIPLY, new HeapReadExpression(new VariableExpression("a"))))))),
                                new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))));
        ex11.typeCheck(new MyDictionary<>());
        PrgState prg11 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyList<>(), ex11, new MyDictionary<>(), new MyHeap());
        IRepository repo11 = new Repository("log11.txt");
        repo11.addPrgState(prg11);
        Controller ctr11 = new Controller((Repository) repo11);


        controllers.add(ctr1);
        controllers.add(ctr2);
        controllers.add(ctr3);
        controllers.add(ctr4);
        controllers.add(ctr5);
        controllers.add(ctr6);
        controllers.add(ctr7);
        controllers.add(ctr8);
        controllers.add(ctr9);
        controllers.add(ctr10);
        controllers.add(ctr11);
        return controllers;
    }

}
