package com.interpreter.repository;

import com.interpreter.exceptions.RepoException;
import com.interpreter.model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> prgStateList;
    private final String filename;
    private final int currentIndex;

    public Repository(String filename) {
        this.filename = filename;
        this.prgStateList = new ArrayList<PrgState>();
        this.currentIndex = 0;
    }

    @Override
    public List<PrgState> getStates() {
        return this.prgStateList;
    }

    @Override
    public void setStates(List<PrgState> states) {
        this.prgStateList = states;
    }

    @Override
    public void addPrgState(PrgState state) {
        this.prgStateList.add(state);
    }

    @Override
    public void logPrgStateExec(PrgState state) throws RepoException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.filename, true)));
            logFile.println("ID:"+state.getId());
            logFile.println(state.toString());
            logFile.close();
        } catch(IOException err) {
            throw new RepoException("Error writing to file");
        }
    }

    @Override
    public String toString() {
        return "Repository{" +
                "prgStateList=" + prgStateList +
                ", filename='" + filename + '\'' +
                ", currentIndex=" + currentIndex +
                '}';
    }

    @Override
    public PrgState getCurrentPrg() {
        return prgStateList.get(0);
    }
}
