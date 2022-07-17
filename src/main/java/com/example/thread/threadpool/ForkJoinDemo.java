package com.example.thread.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Description : ForkJoin
 * @Author : young
 * @Date : 2022-07-17 16:43
 * @Version : 1.0
 **/
public class ForkJoinDemo {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        System.out.println(pool.invoke(new MyTask(1, 10)));
    }
}

class MyTask extends RecursiveTask<Integer> {

    private int begin;
    private int end;

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (begin == end) {
            return begin;
        }

        int mid = (begin + end) / 2;
        MyTask t1 = new MyTask(begin, mid);
        MyTask t2 = new MyTask(mid + 1, end);
        // 让其他线程去执行任务
        t1.fork();
        t2.fork();
        System.out.println(Thread.currentThread().getName() + ",fork");

        int res = t1.join() + t2.join();
        return res;
    }
}
