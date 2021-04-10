package com.myos.design.observe;

/**
 * @Author: wu sir
 * @Date: 2020/8/16 10:12 下午
 */
public class User implements Observer {

    private String name;
    private String message;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " receive " + message);
    }
}
