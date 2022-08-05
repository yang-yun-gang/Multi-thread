package com.example.thread.jdkconcurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description : ReentrantLock
 * @Author : young
 * @Date : 2022-06-28 17:23
 * @Version : 1.0
 **/
public class ReentrantLockDemo {
    private static ReentrantLock lock = new ReentrantLock(true);
    private static boolean cigerate = false;
    private static boolean coffee = false;
    private static Condition waitCigerate = lock.newCondition();
    private static Condition waitCoffee = lock.newCondition();

    public static void main(String[] args) {
        /*lock.lock();
        try {
            // 临界区代码
        } finally {
            lock.unlock();
        }*/

        //interruptTest();
        //tryLockTest();
        conditionTest();
    }

    /**
     * @Description: 中断阻塞
     * @Author: young
     * @Date: 2022-06-28 18:00
     * @return: void
     * @Version: 1.0
     **/
    private static void interruptTest() {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    System.out.println("t1 尝试获取锁");
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("t1 被中断");
                    return;
                }

                try {
                    System.out.println("t1 获取到锁");
                } finally {
                    lock.unlock();
                }
            }
        };

        lock.lock();
        t1.start();
        t1.interrupt();
    }

    /**
     * @Description: 锁超时
     * @Author: young
     * @Date: 2022-06-28 18:08
     * @return: void
     * @Version: 1.0
     **/
    private static void tryLockTest() {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    System.out.println("t1 尝试去获得锁");
                    if (!lock.tryLock(1, TimeUnit.SECONDS)) {
                        System.out.println("t1 没有获取到锁 时间到了退出");
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("t1 获取到锁了");
                } finally {
                    lock.unlock();
                }
            }
        };

        lock.lock();
        t1.start();
    }

    /**
     * @Description: 条件变量
     * @Author: young
     * @Date: 2022-06-28 18:41
     * @return: void
     * @Version: 1.0
     **/
    private static void conditionTest() {

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                lock.lock();
                try {
                    while (!cigerate) {
                        System.out.println("t1 香烟还没到 等会儿");
                        waitCigerate.await();
                    }

                    System.out.println("t1 香烟到了 工作");
                    TimeUnit.SECONDS.sleep(1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                lock.lock();
                try {
                    while (!coffee) {
                        System.out.println("t2 咖啡还没到 等会儿");
                        waitCoffee.await();
                    }

                    System.out.println("t2 咖啡到了 工作");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t3 = new Thread("t3") {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("t3 开始送烟");
                    cigerate = true;
                    waitCigerate.signal();
                } finally {
                    lock.unlock();
                }
            }
        };
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t4 = new Thread("t4") {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("t4 开始送咖啡");
                    coffee = true;
                    waitCoffee.signal();
                } finally {
                    lock.unlock();
                }
            }
        };

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
