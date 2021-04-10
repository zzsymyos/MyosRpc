package com.myos.design.observe;

/**
 * @Author: wu sir
 * @Date: 2020/8/16 10:13 下午
 */
public class ObserveMain {

    public static void main(String[] args) {
        WechatServer server = new WechatServer();

        Observer userZhang = new User("ZhangSan");
        Observer userLi = new User("LiSi");
        Observer userWang = new User("WangWu");

        server.registerObserver(userZhang);
        server.registerObserver(userLi);
        server.registerObserver(userWang);
        server.notifyObserver("PHP是世界上最好用的语言！");

        System.out.println("----------------------------------------------");
        server.removeObserver(userZhang);
        server.notifyObserver("JAVA是世界上最好用的语言！");
    }
}
