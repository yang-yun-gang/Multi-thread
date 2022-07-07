package com.example.thread.unlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description : 使用cas实现锁
 * @Author : young
 * @Date : 2022-07-07 14:48
 * @Version : 1.0
 **/
public class LockCas {
    private AtomicInteger state = new AtomicInteger(0);

    public void lock() {
        // 不能获得锁线程将在这里空转
        while (true) {
            if (state.compareAndSet(0, 1)) {
                break;
            }
        }
    }

    public void unlock() {
        state.set(0);
    }

    public static void main(String[] args) {
        LockCas lock = new LockCas();
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                System.out.println("t1 begin...");
                lock.lock();
                System.out.println("t1 lock...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("t1 unlock...");
                    lock.unlock();
                }
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                System.out.println("t2 begin...");
                lock.lock();
                System.out.println("t2 lock...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("t2 unlock...");
                    lock.unlock();
                }
            }
        };

        t1.start();
        t2.start();

    }
}
