package com.example.thread.pattern;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description : 交替打印
 * @Author : young
 * @Date : 2022-06-29 14:40
 * @Version : 1.0
 **/
public class AlternatePrint {

    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) throws InterruptedException {

        //method1();

        //method2();

        method3();
    }

    /**
      * @Description: wait/notify
      * @Author: young
      * @Date: 2022-06-29 15:01
      * @return: void
      * @Version: 1.0
      **/
    private static void method1() {
        WaitNotify waitNotify = new WaitNotify(1, 5);
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                waitNotify.print("a", 1, 2);
            }
        };
        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                waitNotify.print("b", 2, 3);
            }
        };
        Thread t3 = new Thread("t3") {
            @Override
            public void run() {
                waitNotify.print("c", 3, 1);
            }
        };

        t1.start();
        t2.start();
        t3.start();
    }

    /**
      * @Description: ReentrantLock
      * @Author: young
      * @Date: 2022-06-29 17:38
      * @return: void
      * @Version: 1.0
      **/
    private static void method2() throws InterruptedException {

        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition a = awaitSignal.newCondition();
        Condition b = awaitSignal.newCondition();
        Condition c = awaitSignal.newCondition();

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                awaitSignal.print("a", a, b);
            }
        };
        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                awaitSignal.print("b", b, c);
            }
        };
        Thread t3 = new Thread("t3") {
            @Override
            public void run() {
                awaitSignal.print("c", c, a);
            }
        };

        t1.start();
        t2.start();
        t3.start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("begin...");
        awaitSignal.lock();
        try {
            a.signal();
        } finally {
            awaitSignal.unlock();
        }

    }

    /**
      * @Description: LockSupport
      * @Author: young
      * @Date: 2022-06-30 11:53
      * @return: void
      * @Version: 1.0
      **/
    private static void method3() {
        Park park = new Park(5);
        t1 = new Thread(() -> park.print("a", t2), "t1");
        t2 = new Thread(() -> park.print("b", t3), "t2");
        t3 = new Thread(() -> park.print("c", t1), "t3");
        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }
}

class WaitNotify{
    // 当前标记 1 2 3, 表示当前该那个线程执行
    private int flag;
    // 循环次数
    private int loopNum;

    public WaitNotify(int flag, int loopNum) {
        this.flag = flag;
        this.loopNum = loopNum;
    }

    public void print(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNum; i++) {
            synchronized (this) {
                // 当前标记和进入执行线程的标记不一致
                while(waitFlag != flag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}

class AwaitSignal extends ReentrantLock {
    private int loopNum;

    public AwaitSignal(int loopNum) {
        this.loopNum = loopNum;
    }

    /**
      * @Description: 打印
      * @Author: young
      * @Date: 2022-06-29 17:33
      * @Param str: 打印的内容
      * @Param cur: 当前条件
      * @Param next: 下一个条件
      * @return: void
      * @Version: 1.0
      **/
    public void print(String str, Condition cur, Condition next) {
        for (int i = 0; i < loopNum; i++) {
            lock();
            try {
                cur.await();
                System.out.print(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}

class Park {
    private int loopNum;

    public Park(int loopNum) {
        this.loopNum = loopNum;
    }

    public void print(String str, Thread next) {
        for (int i = 0; i < loopNum; i++) {
            // 当前线程等待
            LockSupport.park();
            // 线程被唤醒
            System.out.print(str);
            // 通知下一个线程
            LockSupport.unpark(next);
        }
    }
}
