package client;

import com.myos.test.api.IHelloService;
import service.RpcFramework;

public class RpcConsumer {
    public static void main(String[] args) throws Exception {
        IHelloService service = RpcFramework.refer(IHelloService.class, "127.0.0.1", 1234);
        for (int i = 0; i < Integer.MAX_VALUE; i ++) {
            String hello = service.sayHi("World" + i);
            System.out.println(hello);
            Thread.sleep(1000);
        }
    }
}
