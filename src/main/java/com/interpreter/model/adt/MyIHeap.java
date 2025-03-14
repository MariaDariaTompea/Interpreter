package com.interpreter.model.adt;

import com.interpreter.model.value.IValue;

import java.util.Map;

public interface MyIHeap {
    Integer getFreeValue();
    void setContent(Map<Integer, IValue> content);
    Map<Integer, IValue> getContent();
    Integer add(IValue value);
    void update(Integer address, IValue value);
    IValue get(Integer address);

}
