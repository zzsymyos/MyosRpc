package com.myos.netty.threadmodule.reactor;

import com.myos.netty.threadmodule.reactor.master.MasterReactor;
import com.myos.netty.threadmodule.reactor.mul.MulReactor;
import com.myos.netty.threadmodule.reactor.single.SingleReactor;

/**
 * @Author: wu sir
 * @Date: 2020/6/2 11:46 上午
 */
public class ReactorServer {


    public static void main(String[] args) {
        //SingleReactor reactor = new SingleReactor(9090);
        //MulReactor reactor = new MulReactor(9090);
        MasterReactor reactor = new MasterReactor(9090);
        reactor.run();
    }

}
