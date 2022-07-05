package com.example.thread.jdkconcurrency;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description : AtomicReference
 * @Author : young
 * @Date : 2022-07-05 16:14
 * @Version : 1.0
 **/
public class AtomicReferenceDemo {

    public static AtomicReference<User> atomicUserRef = new AtomicReference<>();

    public static void main(String[] args) {
        User user = new User("jojo", 17);
        atomicUserRef.set(user);
        User updateUser = new User("kaka", 20);
        atomicUserRef.compareAndSet(user, updateUser);
        System.out.println(atomicUserRef.get().getName() + ": " + atomicUserRef.get().getOld());
    }
}

class User {
    private String name;
    private int old;

    public User(String name, int old) {
        this.name = name;
        this.old = old;
    }

    public String getName() {
        return name;
    }

    public int getOld() {
        return old;
    }
}
