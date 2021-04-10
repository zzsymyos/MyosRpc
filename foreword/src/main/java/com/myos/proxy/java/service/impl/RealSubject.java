package com.myos.proxy.java.service.impl;

import com.myos.proxy.java.service.Subject;

public class RealSubject implements Subject {
    @Override
    public void doSomething() {
        System.out.println( "call doSomething()" );
    }
}
