package com.myos.writescene;

/**
 * @Author: wu sir
 * @Date: 2020/5/21 11:34 上午
 */
public class DeadLockTest {

    private static Object object1 = new Object();
    private static Object object2 = new Object();
    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();
        int num = runtime.availableProcessors();
        System.out.println("Number of processors available to the Java Virtual Machine: " + num);

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                synchronized (object1) {
                    System.out.println("get object1 and object2 before");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (object2) {
                        System.out.println("get object2 end");
                    }
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                synchronized (object2) {
                    System.out.println("get object2 and object1 before");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (object1) {
                        System.out.println("get object1 end");
                    }
                }
            }
        });

        t2.start();


    }

}
