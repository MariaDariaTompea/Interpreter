package com.interpreter.model.adt;



import com.interpreter.exceptions.EmptyStackException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    private final Stack<T> stack;

    public MyStack()
    {
        this.stack = new Stack<>();
    }

    public List<T> getStack() {
        ArrayList<T> list = new ArrayList<>(this.stack);
        return list;
    }

    @Override
    public void push(T element) {
        this.stack.push(element);
    }

    @Override
    public T pop() throws EmptyStackException {
        if(this.stack.isEmpty())
            throw new EmptyStackException("Stack is empty");
        return this.stack.pop();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(T element: this.stack){
            str.append(element).append("\n");
        }
        return "MyStack contains:\n" + str;
    }
}
