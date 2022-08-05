package com.example.thread.pattern;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description : 顺序打印
 * @Author : young
 * @Date : 2022-06-29 14:26
 * @Version : 1.0
 **/
public class OrderPrint {

    private static final Object o = new Object();
    private static boolean runFlag = false; // 即使t2先运行 也不影响t1

    public static void main(String[] args) {
        //method1();

        method2();
    }

    /**
     * @Description: wait/notify
     * @Author: young
     * @Date: 2022-06-29 14:34
     * @return: void
     * @Version: 1.0
     **/
    private static void method1() {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                synchronized (o) {
                    try {
                        while (!runFlag) {
                            o.wait();
                            System.out.println(1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                synchronized (o) {
                    System.out.println(2);
                    runFlag = true;
                    o.notify();
                }
            }
        };

        t1.start();
        t2.start();
    }

    /**
     * @Description: park
     * @Author: young
     * @Date: 2022-06-29 14:34
     * @return: void
     * @Version: 1.0
     **/
    private static void method2() {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                LockSupport.park();
                System.out.println(1);
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                System.out.println(2);
                LockSupport.unpark(t1);
            }
        };

        t1.start();
        t2.start();
    }
}
