package com.example.thread.lock;

/**
 * @Description : JIT 即时编译器会对不可能出现竞争的进行锁消除
 * @Author : young
 * @Date : 2022-06-24 15:12
 * @Version : 1.0
 **/
public class LockEliminate {

    static int x = 0;

    public void b() {
        Object o = new Object();
        synchronized (o) {
            x++;
        }
    }
}
