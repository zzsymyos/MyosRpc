package com.myos.writescene;

import sun.jvm.hotspot.opto.Block;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: wu sir
 * @Date: 2020/8/11 3:38 下午
 */
public class SwapThreadPrint {

    private static int count = 0;
    private static Lock lock = new ReentrantLock();
    private static Condition c1 = lock.newCondition();
    private static Condition c2 = lock.newCondition();
    private static Condition c3 = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        /*new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 0) {
                        try {
                            c1.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    c2.signal();
                    System.out.println("a");
                }
            } finally {
                lock.unlock();
            }

        }).start();
        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 1) {
                        try {
                            c2.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    c3.signal();
                    System.out.println("b");
                }
            } finally {
                lock.unlock();
            }

        }).start();
        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 2) {
                        try {
                            c3.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    c1.signal();
                    System.out.println("c");
                }
            } finally {
                lock.unlock();
            }

        }).start();
*/

        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        Thread A = new MyThread("A",c,a);
        Thread B = new MyThread("B",a,b);
        Thread C = new MyThread("C",b,c);

        A.start();
        Thread.sleep(10);
        B.start();
        Thread.sleep(10);
        C.start();
    }



    static class MyThread extends Thread{

        private String name;
        private Object prev;
        private Object self;

        public MyThread(String name, Object prev, Object self) {
            this.name = name;
            this.prev = prev;
            this.self = self;
        }


         /* wait() 与 notify/notifyAll() 是Object类的方法，在执行两个方法时，要先获得锁。
                * 当线程执行wait()时，会把当前的锁释放，然后让出CPU，进入等待状态。
                * 当执行notify/notifyAll方法时，会唤醒一个处于等待该 对象锁 的线程，然后继续往下执行，直到执行完退出对象锁锁住的区域（synchronized修饰的代码块）后再释放锁。
                * 从这里可以看出，notify/notifyAll()执行后，并不立即释放锁，而是要等到执行完临界区中代码后，再释放。
                * 所以在实际编程中，我们应该尽量在线程调用notify/notifyAll()后，立即退出临界区。即不要在notify/notifyAll()后面再写一些耗时的代码。
                */

        public void run() {
            int count = 10 ;
            while (count > 0 ){
                synchronized (prev) {
                    synchronized (self){
                        System.out.println(name);
                        count-- ;
                        self.notifyAll();   // 唤醒其他线程竞争self锁，注意此时self锁并未立即释放。
                    }
                    try {
                        if (count == 0){    // 如果count==0,表示这是最后一次打印操作，通过notifyAll操作释放对象锁。
                            prev.notifyAll();
                        }else{              // 立即释放 prev锁，当前线程休眠，等待唤醒
                            prev.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
