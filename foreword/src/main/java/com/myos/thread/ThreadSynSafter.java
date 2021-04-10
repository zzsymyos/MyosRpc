package com.myos.thread;

import sun.instrument.InstrumentationImpl;

public class ThreadSynSafter {
    private int[] array = new int[10];
    private byte a;

    private int[] getData() {
        synchronized (array) {
            array[0] = array[0] + 1;
            return array;
        }
    }

    public static void main(String[] args) {
        int[] array3 = new int[10];
        byte[] array1 = new byte[10];
        byte[] array2 = new byte[0];
        Object object = new Object();
        //SizeOf.premain("",new InstrumentationImpl());
        /*System.out.println(SizeOf.sizeOf(array3));
        System.out.println(SizeOf.sizeOf(array1));
        System.out.println(SizeOf.sizeOf(array2));
        System.out.println(SizeOf.sizeOf(object));*/
        final ThreadSynSafter threadSynSafter = new ThreadSynSafter();
        for (int i = 0; i < 10;i++) {
            Thread threada = new Thread(new Runnable() {
                public void run() {
                   // System.out.println(threadSynSafter.getData()[0]);
                }
            });
            threada.start();
        }
    }
}
