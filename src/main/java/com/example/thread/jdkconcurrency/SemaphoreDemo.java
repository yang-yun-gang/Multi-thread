package com.example.thread.jdkconcurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Description : 信号量
 * @Author : young
 * @Date : 2022-08-08 11:21
 * @Version : 1.0
 **/
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 高峰线程数为3
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("running...");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("end...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            }).start();
        }
    }
}
