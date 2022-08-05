package com.example.thread.jdkconcurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description : AtomicInteger
 * @Author : young
 * @Date : 2022-07-05 15:08
 * @Version : 1.0
 **/
public class AtomicIntegerDemo {
    public static void main(String[] args) {
        AtomicInteger a = new AtomicInteger(0);
        // 加1
        a.incrementAndGet(); // 相当于++a
        a.getAndIncrement(); // 相当于a++
        // 加任意数
        a.getAndAdd(5);
        a.addAndGet(5);
        // 读取到 设置值
        a.updateAndGet(x -> x * 10);
        a.getAndUpdate(x -> x * 10);
    }
}
