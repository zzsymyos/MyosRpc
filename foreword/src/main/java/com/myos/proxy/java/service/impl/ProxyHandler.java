package com.myos.proxy.java.service.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyHandler implements InvocationHandler {

    private Object tar;

    //绑定委托对象，并返回代理类
    public Object bind(Object tar) {
        this.tar = tar;
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //绑定该类实现的所有接口，取得代理类，注意传入的最后一个参数，是InvocationHandler的实现类
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(),tar.getClass().getInterfaces(),this);
    }

    /**
     * 第一个参数 proxy存在的意义是啥：
     * invoke 第二个参数是method 怎么来的？$Proxy 动态实例 (即第一个参数proxy) 生成后(是class 文件)，
     * 通过反向编译，可以看到里面有如下一段静态代码块(来源：http://rejoy.iteye.com/blog/1627405)，
     * 也就是说只有proxy 实例在InvocationHandler 实现类里加载才能产生第二个参数method (静态代码块是虚拟机加载类的时候执行的，
     * 而且只执行一次)，所以$Proxy 实例要把自己传给InvocationHandler 的invoke 方法。
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        //这里就可以进行所谓的AOP编程了
        //在调用具体函数方法前，执行功能处理
        result = method.invoke(tar,args);
        //在调用具体函数方法后，执行功能处理
        return null;
    }
}
