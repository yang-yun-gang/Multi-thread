package com.example.thread.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Description : 死锁
 * @Author : young
 * @Date : 2022-06-27 15:45
 * @Version : 1.0
 **/
public class DeadLockDemo {

    public static void main(String[] args) {
        Object A = new Object();
        Object B = new Object();

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                synchronized (A) {
                    System.out.println("t1 lock A");
                    synchronized (B) {
                        System.out.println("t1 lock B");
                        System.out.println("t1 work...");
                    }
                }
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                synchronized (B) {
                    System.out.println("t2 lock B");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (A) {
                        System.out.println("t2 lock A");
                        System.out.println("t2 work...");
                    }
                }
            }
        };

        t1.start();
        t2.start();
    }
}
