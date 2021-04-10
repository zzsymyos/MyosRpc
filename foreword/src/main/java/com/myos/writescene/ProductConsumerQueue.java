package com.myos.writescene;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模型
 */
public class ProductConsumerQueue {

    private int maxcount = 100;
    private Lock lock;
    private Condition emptyCondition;
    private Condition fullCondition;
    private ArrayList<Integer> storeList = new ArrayList<Integer>();

    public ProductConsumerQueue() {
        lock = new ReentrantLock();
        emptyCondition = lock.newCondition();
        fullCondition = lock.newCondition();
    }
/*
    public void put(Integer value) throws InterruptedException {
        lock.lock();
        try {
            while (storeList.size() == maxcount) {
                fullCondition.await();
            }
            storeList.add(value);
            emptyCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void remove(Integer value) throws InterruptedException {
        lock.lock();
        try {
            while (storeList.size() == 0) {
                emptyCondition.await();
            }
            storeList.remove(value);
            fullCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }*/


    public synchronized void put(Integer value) throws InterruptedException {
        while (storeList.size() == maxcount) {
            wait();
        }
        storeList.add(value);
        notifyAll();
    }

    public synchronized void remove(Integer value) throws InterruptedException {
        while (storeList.size() == 0) {
            emptyCondition.await();
        }
        storeList.remove(value);
        notifyAll();
    }

}
