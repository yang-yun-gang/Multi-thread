package com.example.thread.base;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description : 线程中断
 * @Author : young
 * @Date : 2022-06-13 12:31
 * @Version : 1.0
 **/
public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                while (true) {
                    System.out.println("do work1...");
                    // 检查到此时线程被中断
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("save...");
                        System.out.println(Thread.currentThread().isInterrupted());
                        break;
                    }
                }

            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                while (true) {
                    // 在sleep的时候会抛出中断异常，此时会被捕获，但中断标志位会被清除
                    try {
                        System.out.println("t2 sleep...");
                        Thread.sleep(5000);
                        System.out.println("sss"); // 中断后抛出异常，不会打印
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted when sleep");
                        System.out.println(Thread.currentThread().isInterrupted());
                    }
                }
            }
        };


        Thread t3 = new Thread("t3") {
            @Override
            public void run() {
                while (true) {
                    Thread t = Thread.currentThread();
                    if (t.isInterrupted()) {
                        System.out.println("save...");
                        break;
                    }
                    System.out.println("do work...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // 重新设置打断标记
                        e.printStackTrace();
                        t.interrupt();
                    }
                }

            }
        };

        Thread t4 = new Thread("t4") {
            @Override
            public void run() {
                System.out.println("t4 park...");
                LockSupport.park();
                System.out.println("t4 unpark...");
                System.out.println("t4 interrupted status:" + Thread.currentThread().isInterrupted());
                System.out.println("t4 park again...");
                LockSupport.park(); //不会再生效
                System.out.println("t4 unpark again...");
            }
        };

        //t1.start();
        t2.start();
        //t3.start();
        //t4.start();
        Thread.sleep(1000);
        //t1.interrupt();
        t2.interrupt();
        //t3.interrupt();
        //t4.interrupt();

    }
}
