package com.myos.spi.service.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.RpcException;

public class DefineProtocol implements Protocol {


    public int getDefaultPort() {
        return 8888;
    }

    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        return null;
    }

    public <T> Invoker<T> refer(Class<T> aClass, URL url) throws RpcException {
        return null;
    }

    public void destroy() {

    }
}
