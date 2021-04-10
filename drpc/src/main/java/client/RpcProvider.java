package client;

import com.myos.service.HelloServiceImpl;
import com.myos.test.api.IHelloService;
import service.RpcFramework;

public class RpcProvider {

    public static void main(String[] args) throws Exception {
        IHelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }

}
