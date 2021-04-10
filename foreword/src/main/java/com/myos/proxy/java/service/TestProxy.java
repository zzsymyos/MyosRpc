package com.myos.proxy.java.service;

import com.myos.proxy.java.service.Subject;
import com.myos.proxy.java.service.impl.ProxyHandler;
import com.myos.proxy.java.service.impl.RealSubject;
import com.myos.proxy.java.service.impl.SubjectProxy;

public class TestProxy {
    /**
     *  jdk动态代理局限于接口是因为java只支持单继承
     * jdk动态代理之所以只能代理接口是因为代理类本身已经extends了Proxy，
     * 而java是不允许多重继承的，但是允许实现多个接口，因此才有cglib的需要吧
     * @param args
     */
    public static void main(String[] args) {
        //刚开始我会觉得SubjectProxy定义出来纯属多余，直接实例化实现类完成操作不就结了吗？
        // 后来随着业务庞大，你就会知道，实现proxy类对真实类的封装对于粒度的控制有着重要的意义。
        // 但是静态代理这个模式本身有个大问题，如果类方法数量越来越多的时候，代理类的代码量是十分庞大的
        Subject subject = new SubjectProxy();
        subject.doSomething();

        ProxyHandler proxyHandler = new ProxyHandler();
        Subject subject1 = (Subject) proxyHandler.bind(new RealSubject());
        subject1.doSomething();

    }

}
