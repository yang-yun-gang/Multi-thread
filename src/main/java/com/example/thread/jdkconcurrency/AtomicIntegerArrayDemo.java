package com.example.thread.jdkconcurrency;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Description : AtomicIntegerArray
 * @Author : young
 * @Date : 2022-07-06 11:11
 * @Version : 1.0
 **/
public class AtomicIntegerArrayDemo {
    static int[] value = new int[]{1, 2};

    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        // 对0号位做更改
        ai.getAndSet(0, 3);
        System.out.println(ai.get(0));
        System.out.println(value[0]);

    }
}
