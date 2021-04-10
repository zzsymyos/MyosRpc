package com.myos.thread;

/**
 * @Author: wu sir
 * @Date: 2020/7/20 3:25 下午
 */
public class StopThread {

    private static boolean stopRequestedd;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int i = 0;
            //if (!stopRequested)
            //    while (true)
            //     i++;  jvm 优化提升，导致活性失败 所以会导致该程序不会停止，需要对stopRequestedd同步
            while (!stopRequestedd) {
                i++;
            }
        });

        t1.start();
        Thread.sleep(1000);
        stopRequestedd = true;
    }
}
