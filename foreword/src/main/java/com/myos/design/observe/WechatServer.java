package com.myos.design.observe;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wu sir
 * @Date: 2020/8/16 10:09 下午
 */
public class WechatServer implements Observerable {

    private List<Observer> list;
    private String message;

    public WechatServer(){
        list = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        list.add(observer);
    }

    @Override
    public void removeObserver(Observer o) {
        if (!list.isEmpty()) {
            list.remove(o);
        }
    }


    @Override
    public void notifyObserver(String message) {
        this.message = message;
        for (Observer o : list) {
            o.update(message);
        }
    }


}
