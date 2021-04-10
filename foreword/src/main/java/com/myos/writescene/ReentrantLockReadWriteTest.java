package com.myos.writescene;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 读写锁
 */
public class ReentrantLockReadWriteTest {

    private AtomicInteger writecount = new AtomicInteger(0);
    private AtomicInteger readcount = new AtomicInteger(0);

    public void lockread() throws InterruptedException {
        System.out.println("readcount = " + readcount);
        while (writecount.get() > 0) {
            synchronized (this) {
                wait();
            }
        }
        readcount.incrementAndGet();
        System.out.println("read lock");
    }

    public void unlockread() {
        readcount.decrementAndGet();
        synchronized (this) {
            notifyAll();
        }
    }

    public void lockwrite() throws InterruptedException {
        System.out.println("writecount = " + writecount);
        while (writecount.get() > 0) {
            synchronized (this) {
                wait();
            }
        }
        writecount.incrementAndGet();
        while (readcount.get() > 0) {
            synchronized (this) {
                wait();
            }
        }
        System.out.println("write lock");
    }

    public void unlockwrite() {
        writecount.decrementAndGet();
        synchronized (this) {
            notifyAll();
        }
    }

    public static void main(String[] args) {
        ReentrantLockReadWriteTest readWriteLock = new ReentrantLockReadWriteTest();
        for(int i = 0; i < 2; i++){
            Thread2 thread2 = new Thread2(i, readWriteLock);
            thread2.start();
        }

        for (int i = 0; i < 10; i++) {
            Thread1 thread1 = new Thread1(i, readWriteLock);
            thread1.start();
        }
    }

    static class Thread1 extends Thread{
        public int i;
        public ReentrantLockReadWriteTest readWriteLock;

        public Thread1(int i, ReentrantLockReadWriteTest readWriteLock) {
            this.i = i;
            this.readWriteLock = readWriteLock;
        }

        @Override
        public void run() {
            try {
                readWriteLock.lockread();
                Thread.sleep(1000);//模拟耗时
                System.out.println("第"+i+"个读任务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.unlockread();
            }
        }
    }


    static class Thread2 extends Thread {
        public int i;
        public ReentrantLockReadWriteTest readWriteLock;

        public Thread2(int i, ReentrantLockReadWriteTest readWriteLock) {
            this.i = i;
            this.readWriteLock = readWriteLock;
        }

        @Override
        public void run() {
            try {
                readWriteLock.lockwrite();
                Thread.sleep(1000);
                System.out.println("第" + i + "个写任务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.unlockwrite();
            }
        }
    }


}
