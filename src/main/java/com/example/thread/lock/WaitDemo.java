package com.example.thread.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Description : wait的使用
 * @Author : young
 * @Date : 2022-06-24 15:29
 * @Version : 1.0
 **/
public class WaitDemo {

    public final static Object o = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                System.out.println("t1 执行...");
                synchronized (o) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t1 唤醒...");
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                System.out.println("t2 执行...");
                synchronized (o) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 唤醒...");
            }
        };

        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(5);

        synchronized (o) {
            o.notify();
        }
    }
}
