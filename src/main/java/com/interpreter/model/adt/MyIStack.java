package com.interpreter.model.adt;
import com.interpreter.exceptions.EmptyStackException;

import java.util.List;
import java.util.Stack;

public interface MyIStack <T>{
    void push(T element);
    T pop() throws EmptyStackException;
    int size();
    boolean isEmpty();
    List<T> getStack();
}
