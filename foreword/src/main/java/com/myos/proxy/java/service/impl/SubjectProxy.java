package com.myos.proxy.java.service.impl;

import com.myos.proxy.java.service.Subject;

public class SubjectProxy implements Subject {

    Subject subimpl = new RealSubject();
    @Override
    public void doSomething() {
        subimpl.doSomething();
    }
}
