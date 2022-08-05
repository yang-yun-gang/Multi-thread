package com.example.thread.pattern;

/**
 * @author yang
 * @create 2021-04-14-20:21
 */
public class Singleton {
    private volatile static Singleton uniqueInstance;

    private Singleton() {
    }

    //同步方法 缺点是每次都要同步 拖垮性能
    /*public static synchronized Singleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }*/

    //同步代码块 双重检查加锁
    public static Singleton getInstance() {
        if (uniqueInstance == null) {
            synchronized (Singleton.class) {
                if (uniqueInstance == null)
                    uniqueInstance = new Singleton();
            }
        }
        return uniqueInstance;
    }
}
