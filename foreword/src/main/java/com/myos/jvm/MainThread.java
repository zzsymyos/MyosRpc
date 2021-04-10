package com.myos.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @Author: wu sir
 * @Date: 2020/7/21 10:21 下午
 */
public class MainThread {

    static Object object1 = new Object();
    static Object object2 = new Object();

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object1) {
                    System.out.println("t1 o1");
                    synchronized (object2) {
                        System.out.println("t1 o2");
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object2) {
                    System.out.println("t2 o1");
                    synchronized (object1) {
                        System.out.println("t2 o2");
                    }
                }
            }
        });

        t1.start();
        t2.start();


        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false,false);
        for (ThreadInfo info : threadInfos ) {
            System.out.println("[" + info.getThreadId() + "]" + info.getThreadName());
        }

    }
}
