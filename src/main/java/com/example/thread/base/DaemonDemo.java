package com.example.thread.base;

import java.util.concurrent.TimeUnit;

/**
 * @Description : 守护线程
 * @Author : young
 * @Date : 2022-06-14 17:20
 * @Version : 1.0
 **/
public class DaemonDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("t1 work...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                try {
                    System.out.println("t2 work...");
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("t2 out...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.setDaemon(true);
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("main out...");

    }
}
