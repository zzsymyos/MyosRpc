package com.myos.thread;

public class ThreadLocalTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    private static ThreadLocal<String> threadLocal1 = new ThreadLocal<String>();
    static void print(String str) {
        System.out.println(str + threadLocal.get());
        threadLocal.remove();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                threadLocal.set("t1");
                threadLocal.set("t11");
                threadLocal1.set("112");
                print("t1+");
                System.out.println(threadLocal.get());
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                threadLocal.set("t2");
                print("t2+");
                System.out.println(threadLocal.get());
            }
        });
        t1.start();
        t2.start();
    }
}
