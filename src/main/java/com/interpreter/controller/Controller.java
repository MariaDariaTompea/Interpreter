package com.interpreter.controller;


import com.interpreter.exceptions.RepoException;
import com.interpreter.model.state.PrgState;
import com.interpreter.model.value.IValue;
import com.interpreter.model.value.RefValue;
import com.interpreter.repository.IRepository;
import com.interpreter.repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class Controller implements IController{

    Repository repo;
    ExecutorService executor;


    public Controller(Repository repo){
        this.repo = repo;
    }


    void oneStepForAllPrg(List<PrgState> prgList) throws Exception {
        prgList.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepoException e) {
                throw new RuntimeException(e);
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
            .map((PrgState p)->(Callable<PrgState>)(()->{return p.oneStep();}))
            .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
            .map(future->{
                try{
                    return future.get();
                }
                catch(Exception e){
                    throw new RuntimeException(e);
                }
                })
            .filter(p->p!=null)
            .collect(Collectors.toList());

        prgList.addAll(newPrgList);
        prgList.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepoException e) {
                throw new RuntimeException(e);
            }
        });
        repo.setStates(prgList);
    }


    public void allStep() throws Exception {
        executor = java.util.concurrent.Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getStates());
        while (prgList.size() > 0) {
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getStates());
        }
        executor.shutdownNow();
        repo.setStates(prgList);
    }

    public void oneStep() throws Exception {
        executor = java.util.concurrent.Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getStates());
        if (prgList.size() > 0) {
            oneStepForAllPrg(prgList);
        }
        executor.shutdownNow();
        repo.setStates(prgList);
    }


///executa toate starile din program
//    @Override
//    public void allStep() throws Exception {
//        PrgState state = repo.getCurrentPrg();
//        repo.logPrgStateExec();
//
//        while(!state.getExeStack().isEmpty()){
//            oneStep(state);
//            state.getHeap().setContent(unsafeGarbageCollector(
//                    getAddrFromSymTable(state.getSymTable().getMap().values()),
//                    state.getHeap().getContent()));
//            repo.logPrgStateExec();
//        }
//
//    }

    Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,IValue> heap){
        return heap.entrySet()
                .stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {
                    RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }


    @Override
    public void displayState() throws Exception {
        System.out.println(repo.getStates().get(0).toString());
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }
    
    @Override
    public String toString(){
        return repo.toString();
    }


    public IRepository getRepo() {
        return repo;
    }
}
