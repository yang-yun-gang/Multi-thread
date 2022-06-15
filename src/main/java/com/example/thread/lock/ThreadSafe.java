package com.example.thread.lock;

/**
 * @Description : 线程安全问题
 * @Author : young
 * @Date : 2022-06-15 15:23
 * @Version : 1.0
 **/
public class ThreadSafe {

    public static int cnt = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                for (int i = 0; i < 5000; i++) {
                    cnt++;
                }
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                for (int i = 0; i < 5000; i++) {
                    cnt++;
                }
            }
        };

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(cnt);
    }
}
