package com.example.thread.jmm;

/**
 * @Description : 重排序
 * @Author : young
 * @Date : 2022-07-01 11:35
 * @Version : 1.0
 **/
public class OrderQuestion {

    public static void main(String[] args) {
        Test test = new Test();

        Thread t1 = new Thread(() -> test.actor1(), "t1");
        Thread t2 = new Thread(() -> test.actor2(), "t2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(test.r);
    }
}

class Test {
    int num = 0;
    int r = 0;
    volatile boolean ready = false;

    // 线程1
    public void actor1() {
        if (ready) {
            r = num + num;
        } else {
            r = 1;
        }
    }

    // 线程2
    public void actor2() {
        num = 2;
        ready = true;
    }
}
