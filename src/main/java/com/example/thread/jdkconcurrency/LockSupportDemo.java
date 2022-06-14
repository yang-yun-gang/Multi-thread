package com.example.thread.jdkconcurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description : LockSupport方法演示
 * @Author : young
 * @Date : 2022-06-14 16:54
 * @Version : 1.0
 **/
public class LockSupportDemo {
    public static void main(String[] args) {
        Thread t = new Thread("t") {
            @Override
            public void run() {
                System.out.println("t park...");
                LockSupport.park();
                System.out.println("t unpark...");
            }
        };
        t.start();
        LockSupport.unpark(t);
    }
}
