package com.myos.design.observe;

/**
 * @Author: wu sir
 * @Date: 2020/8/16 10:07 下午
 * 被观察者，有消息就通知观察者
 */
public interface Observerable {

    public void registerObserver(Observer observer);

    public void notifyObserver(String message);

    public void removeObserver(Observer o);
}
