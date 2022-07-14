package com.example.thread.threadpool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description : ThreadPoolExecutor线程池
 * @Author : young
 * @Date : 2022-07-13 11:48
 * @Version : 1.0
 **/
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 自定义线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 5, 1000,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(50));

        // 提交单个
        Future<String> future = pool.submit(() -> {
            TimeUnit.SECONDS.sleep(2);
            return "ok";
        });
        System.out.println(future.get());

        // 批量提交
        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
                () -> {
                    TimeUnit.SECONDS.sleep(1);
                    return "1";
                },
                () -> {
                    TimeUnit.SECONDS.sleep(2);
                    return "2";
                },
                () -> {
                    TimeUnit.SECONDS.sleep(3);
                    return "3";
                }
        ));

        futures.forEach(f-> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        // 批量竞争提交
        String result = pool.invokeAny(Arrays.asList(
                () -> {
                    TimeUnit.SECONDS.sleep(1);
                    return "a";
                },
                () -> {
                    TimeUnit.SECONDS.sleep(2);
                    return "b";
                },
                () -> {
                    TimeUnit.SECONDS.sleep(3);
                    return "c";
                }
        ));
        System.out.println(result);

        pool.shutdown();
    }
}
