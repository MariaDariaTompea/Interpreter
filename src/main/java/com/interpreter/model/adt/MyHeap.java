package com.interpreter.model.adt;



import com.interpreter.model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap{
    private Map<Integer, IValue> heap;
    private Integer freeValue;

    public MyHeap(Map<Integer, IValue> heap) {
        this.heap = heap;
    }

    public MyHeap(){
        heap = new HashMap<>();
        freeValue = newFreeValue();
    }

    private Integer newFreeValue(){
        freeValue = 1;
        while(heap.containsKey(freeValue)){
            freeValue++;
        }
        return freeValue;
    }

    @Override
    public Integer getFreeValue() {
        return freeValue;
    }

    @Override
    public void setContent(Map<Integer, IValue> content) {
        this.heap = content;
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return heap;
    }

    @Override
    public Integer add(IValue value) {
        heap.put(freeValue, value);
        Integer lastOccupiedFreeValue = freeValue;
        freeValue = newFreeValue();
        return lastOccupiedFreeValue;
    }
    //nu exista
    @Override
    public void update(Integer address, IValue value) {
        if (!heap.containsKey(address)){
            throw new RuntimeException(String.format("%d memory access violation",address));
        }
        heap.put(address,value);
    }
    //val de la adresa gresita
    @Override
    public IValue get(Integer address) {
        if(!heap.containsKey(address)){
            throw new RuntimeException(String.format("%d memory access violation",address));
        }
        return heap.get(address);
    }
    //print
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, IValue> entry : heap.entrySet()) {
            result.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return "MyHeap contains:"+ result;
    }
}
