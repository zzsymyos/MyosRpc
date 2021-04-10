package com.myos.proxy.cglib.service;

import com.myos.proxy.cglib.service.impl.HelloConcrete;
import com.myos.proxy.cglib.service.impl.MyMethodInterceptor;
import com.myos.proxy.cglib.service.impl.User;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

import java.io.File;
import java.io.IOException;

public class Testcglib {

    //好文章：http://www.throwable.club/2018/12/16/cglib-dynamic-proxy-analyze/#CGLIB%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86%E5%8E%9F%E7%90%86%E5%88%86%E6%9E%90-1
    public static void main(String[] args) throws IOException {
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();

        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, courseFile + "/foreword/src/test/proxy");

        MyMethodInterceptor myMethodInterceptor = new MyMethodInterceptor();
        HelloConcrete user = (HelloConcrete) myMethodInterceptor.getProxy(HelloConcrete.class);
        user.sayHello("12");
        //user.methodPublic2("2");
        //user.defaultMethod2();
        //user.defaultMethod1(1);
    }
}

