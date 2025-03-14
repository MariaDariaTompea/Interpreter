package com.interpreter;

import com.interpreter.controller.Controller;
import com.interpreter.exceptions.ExpressionException;
import com.interpreter.exceptions.StatementException;
import com.interpreter.repository.ProgramList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class InterpreterGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Interpreter Program Selection");

        ListView<String> programListView = new ListView<>();
        // Add program strings to the ListView
        programListView.getItems().addAll(
                "Program 1: v=2;print(v)",
                "Program 2: a=2+3*5;b=a+1;print(b)",
                "Program 3: a=true;v=2;if a then v=3 else v=2;print(v)",
                "Program 4: openRFile(varf,\"test.in\");readFile(varf,varc);print(varc);closeRFile(varf)",
                "Program 5: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)",
                "Program 6: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)",
                "Program 7: Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))",
                "Program 8: int v; v=4; (while (v>0) print(v);v=v-1);print(v)",
                "Program 9: int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))",
                "Program 10 int v; int x; int y; v=0;(repeat (fork(print(v);v--);v=v+1)until v==3);1;pop();y=3;nop;print(y*10)",
                "Program 11: Ref int a; new(a,20); (for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a))); print(rh(a))"
        );

       programListView.setOnMouseClicked(event -> {
    if (event.getClickCount() == 2) {
        try {
            List<Controller> programs = ProgramList.getProgramList();
            int index = programListView.getSelectionModel().getSelectedIndex();
            Controller selectedProgram = programs.get(index);
            MainWindow mainWindow = new MainWindow(selectedProgram);
            mainWindow.show();
        } catch (StatementException e) {
            throw new RuntimeException("Statement error: " + e.getMessage(), e);
        } catch (ExpressionException e) {
            throw new RuntimeException("Expression error: " + e.getMessage(), e);
        }
    }
});

        VBox vbox = new VBox(programListView);
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}