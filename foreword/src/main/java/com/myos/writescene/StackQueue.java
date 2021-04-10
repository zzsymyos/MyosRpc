package com.myos.writescene;

import java.util.Stack;

/**
 * @Author: wu sir
 * @Date: 2020/8/10 8:50 下午
 */
public class StackQueue {

    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();

    private void push(int data) {
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        stack1.push(data);
        System.out.println("push data:" + data);
    }

    private Integer pop() {
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        System.out.println("pop data:" + stack2.peek());
        return stack2.pop();
    }

    public static void main(String[] args) {
        StackQueue stackQueue = new StackQueue();
        stackQueue.push(1);
        stackQueue.push(2);
        stackQueue.push(3);
        stackQueue.pop();
        stackQueue.push(4);
        stackQueue.push(5);


        stackQueue.pop();
        stackQueue.pop();
        stackQueue.pop();
    }
}
