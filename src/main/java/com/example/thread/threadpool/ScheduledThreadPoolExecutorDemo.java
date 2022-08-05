package com.example.thread.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description : 任务调度线程池
 * @Author : young
 * @Date : 2022-07-15 16:21
 * @Version : 1.0
 **/
public class ScheduledThreadPoolExecutorDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(2);

        // 延时执行 两个接口
        //pool.schedule(()-> System.out.println("hello"), 1, TimeUnit.SECONDS);
        ScheduledFuture<String> future = pool.schedule(() -> {
            System.out.println("hello");
            return "s";
        }, 1, TimeUnit.SECONDS);
        System.out.println(future.get());

        // 周期执行，以上个任务开始计算 总共等待2秒
        pool.scheduleAtFixedRate(() -> {
            System.out.println("running1");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);


        // 周期执行，以上个任务结束计算 总共等待3秒
        /*pool.scheduleWithFixedDelay(()->{
                    System.out.println("running1");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, 0, 2, TimeUnit.SECONDS
        );*/
        //pool.shutdown();
    }
}
