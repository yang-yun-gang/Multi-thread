package com.example.thread.base;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description : 1.线程创建的三种方式 2.让步、休眠、等待线程结束
 * @Author : young
 * @Date : 2022-06-09 15:56
 * @Version : 1.0
 **/
public class ThreadBase {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 方式一：直接重写Thread的run方法
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                System.out.println("I am t1");
            }
        };
        t1.start();

        // 方式二：实现Runnable接口，调用Thread的构造器
        Runnable runnable = () -> System.out.println("I am t2");
        Thread t2 = new Thread(runnable, "t2");
        t2.start();

        // 方式三：使用Future模式
        FutureTask<Integer> ft = new FutureTask<>(() -> {
            System.out.println("I am t3");
            return 1;
        });
        Thread t3 = new Thread(ft, "t3");
        t3.start();
        Integer integer = ft.get();
        System.out.println(integer);

        // 线程让步
        Thread.yield();

        // 线程休眠
        Thread.sleep(1000);

        // 等待线程结束
        t1.join();
        t1.join(1000);

        // 线程中断
        t1.interrupt();
    }
}
