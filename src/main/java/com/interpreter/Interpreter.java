package com.interpreter;

import com.interpreter.controller.Controller;
import com.interpreter.model.adt.*;
import com.interpreter.model.command.ExitCommand;
import com.interpreter.model.command.RunExample;
import com.interpreter.model.expresions.*;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.statements.*;
import com.interpreter.model.types.*;
import com.interpreter.model.value.*;
import com.interpreter.repository.*;
import com.interpreter.view.TextMenu;

import java.util.List;

public class Interpreter {
    public static void main(String[] args) {
        //example 1
        try {
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

            List<Controller> ctrs = ProgramList.getProgramList();

            //menu
            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
            menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
            menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
            menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
            menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
            menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
            menu.show();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}