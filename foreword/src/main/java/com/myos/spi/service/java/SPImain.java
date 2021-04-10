package com.myos.spi.service.java;

import com.myos.spi.service.IShout;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ServiceLoader;

public class SPImain {
    public static void main(String[] args) throws IOException {
        dubbospi();
        //javaospi();
    }
    private static void javaospi() {
        ServiceLoader<IShout> serviceLoader = ServiceLoader.load(IShout.class);
        for (IShout shout : serviceLoader) {
            shout.shout();
        }
    }
    private static void dubbospi() {
        //Protocol myProtocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("myProtocol");
        IShout shout = ExtensionLoader.getExtensionLoader(IShout.class).getExtension("dog");
        IShout cat = ExtensionLoader.getExtensionLoader(IShout.class).getExtension("cat");
        System.out.println();
        shout.shout();
        cat.shout();
    }

}
