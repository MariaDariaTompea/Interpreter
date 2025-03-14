package com.interpreter.repository;

import com.interpreter.exceptions.RepoException;
import com.interpreter.model.state.PrgState;

import java.util.List;

public interface IRepository {
    List<PrgState> getStates();
    void setStates(List<PrgState> states);
    void addPrgState(PrgState state);
    void logPrgStateExec(PrgState state) throws RepoException;
    PrgState getCurrentPrg();
}
