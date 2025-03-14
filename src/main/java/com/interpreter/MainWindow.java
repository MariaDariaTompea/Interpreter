package com.interpreter;

import com.interpreter.controller.Controller;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.value.IValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainWindow {

    private Controller selectedProgram;
    @FXML
    private TextField prgStateCount;
    @FXML
    private TableView<Map.Entry<String, IValue>> heapTableView;
    @FXML
    private ListView<String> outListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private ListView<Integer> prgStateIdListView;
    @FXML
    private TableView<Map.Entry<String, IValue>> symTableView;
    @FXML
    private ListView<String> exeStackListView;
    @FXML
    private Button runOneStepButton;

    public MainWindow(Controller selectedProgram) {
        this.selectedProgram = selectedProgram;
    }

    public void show() {
        Stage mainStage = new Stage();
        mainStage.setTitle("Interpreter Main Window");

        prgStateCount = new TextField();
        prgStateCount.setEditable(false);

        heapTableView = new TableView<>();

        outListView = new ListView<>();

        fileTableListView = new ListView<>();

        prgStateIdListView = new ListView<>();

        symTableView = new TableView<>();

        exeStackListView = new ListView<>();

        runOneStepButton = new Button("Run one step");
        runOneStepButton.setOnAction(event -> runOneStep());

        heapTableView.setPrefHeight(100);
        ScrollPane heapScrollPane = new ScrollPane(heapTableView);
        heapScrollPane.setFitToWidth(true);

        outListView.setPrefHeight(100);
        ScrollPane outScrollPane = new ScrollPane(outListView);
        outScrollPane.setFitToWidth(true);

        fileTableListView.setPrefHeight(100);
        ScrollPane fileTableScrollPane = new ScrollPane(fileTableListView);
        fileTableScrollPane.setFitToWidth(true);

        prgStateIdListView.setPrefHeight(100);
        ScrollPane prgStateIdScrollPane = new ScrollPane(prgStateIdListView);
        prgStateIdScrollPane.setFitToWidth(true);

        symTableView.setPrefHeight(100);
        ScrollPane symTableScrollPane = new ScrollPane(symTableView);
        symTableScrollPane.setFitToWidth(true);

        exeStackListView.setPrefHeight(100);
        ScrollPane exeStackScrollPane = new ScrollPane(exeStackListView);
        exeStackScrollPane.setFitToWidth(true);


        VBox vbox = new VBox(10, prgStateCount, heapScrollPane, outScrollPane, fileTableScrollPane, prgStateIdScrollPane, symTableScrollPane, exeStackScrollPane, runOneStepButton);
        vbox.setPadding(new Insets(10));
        vbox.setPrefSize(800, 600);



        Scene scene = new Scene(vbox);
        mainStage.setScene(scene);
        mainStage.show();
        updateUI();
    }

    @FXML
    private void runOneStep() {
        try {
            selectedProgram.oneStep();
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI() {
        try {
            PrgState currentPrgState = selectedProgram.getRepo().getStates().get(0);
            prgStateCount.setText("Number of program states: " + selectedProgram.getRepo().getStates().size());

            outListView.getItems().setAll(currentPrgState.getOutput().getAll());
            fileTableListView.getItems().setAll(currentPrgState.getFileTable().getKeys().stream().map(Object::toString).collect(Collectors.toList()));
            prgStateIdListView.getItems().setAll(selectedProgram.getRepo().getStates().stream().map(prgState -> prgState.getId()).collect(Collectors.toList()));
            //print the stack in reverse order
            printStackInReverseOrder();
            //populate the symTable
            populateSymTable();
            populateHeapTable();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printStackInReverseOrder() {
        PrgState currentPrgState = selectedProgram.getRepo().getStates().get(0);
        List<String> stack = currentPrgState.getExeStack().getStack().stream().map(Object::toString).collect(Collectors.toList());
        Collections.reverse(stack);
        exeStackListView.getItems().setAll(stack);
    }

    private void populateSymTable() {
        PrgState currentPrgState = selectedProgram.getRepo().getStates().get(0);

        // Create table columns only once
        if (symTableView.getColumns().isEmpty()) {
            TableColumn<Map.Entry<String, IValue>, String> keyColumn = new TableColumn<>("Key");
            keyColumn.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKey())
            );

            TableColumn<Map.Entry<String, IValue>, String> valueColumn = new TableColumn<>("Value");
            valueColumn.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(cellData.getValue().getValue().toString())
            );

            symTableView.getColumns().addAll(keyColumn, valueColumn);
        }

        // Clear the existing items
        symTableView.getItems().clear();

        // Populate the table with the current data
        ObservableList<Map.Entry<String, IValue>> symTableEntries = FXCollections.observableArrayList(
                currentPrgState.getSymTable().getMap().entrySet()
        );

        symTableView.setItems(symTableEntries);
    }

    private void populateHeapTable() {
        PrgState currentPrgState = selectedProgram.getRepo().getStates().get(0);

        // Create table columns only once for the heap table
        if (heapTableView.getColumns().isEmpty()) {
            TableColumn<Map.Entry<String, IValue>, String> addressColumn = new TableColumn<>("Address");
            addressColumn.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKey())
            );

            TableColumn<Map.Entry<String, IValue>, String> valueColumn = new TableColumn<>("Value");
            valueColumn.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(cellData.getValue().getValue().toString())
            );

            heapTableView.getColumns().addAll(addressColumn, valueColumn);
        }

        // Clear the existing items in the heap table
        heapTableView.getItems().clear();

        // Populate the table with the current heap data
        ObservableList<Map.Entry<String, IValue>> heapEntries = FXCollections.observableArrayList(
                currentPrgState.getHeap().getContent().entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toString(),
                                Map.Entry::getValue
                        )).entrySet()
        );

        heapTableView.setItems(heapEntries);
    }
}