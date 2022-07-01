package com.example.thread.jmm;

import java.util.concurrent.TimeUnit;

/**
 * @Description : 可见性问题
 * @Author : young
 * @Date : 2022-06-30 12:12
 * @Version : 1.0
 **/
public class VisibleQuestion {
    static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            while (flag) {
                //..
            }
        }, "t");
        t.start();
        TimeUnit.SECONDS.sleep(1);
        // 想让线程停下来
        flag = false;
    }
}
