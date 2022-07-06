package com.example.thread.jdkconcurrency;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Description : AtomicStampedReference
 * @Author : young
 * @Date : 2022-07-06 10:46
 * @Version : 1.0
 **/
public class AtomicStampedReferenceDemo {
    static AtomicStampedReference<User> ref = new AtomicStampedReference<>(new User("jojo", 17), 0);

    public static void main(String[] args) {
        // 预期引用
        User user = ref.getReference();
        // 预期版本号
        int stamp = ref.getStamp();

        User updateUser = new User("kaka", 20);
        ref.compareAndSet(user, updateUser, stamp, stamp + 1);
    }
}
