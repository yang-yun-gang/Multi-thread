package com.example.thread.jdkconcurrency;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description : ThreadLocal使用
 * @Author : young
 * @Date : 2022-10-04 16:41
 * @Version : 1.0
 **/
public class ThreadLocalDemo {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<>();
    public static class ParseDate implements Runnable {
        int i = 0;
        public ParseDate(int i) {
            this.i = i;
        }
        @Override
        public void run() {
            try {
                if (t1.get() == null) {
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date t = t1.get().parse("2015-03-29 19:29:" + i % 60);
                System.out.println(i + ":" + t);
            } catch (ParseException e) {
                e.printStackTrace();;
            }
            // 防止内存泄漏
            t1.remove();
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 2, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        for (int i = 0; i < 1000; i++) {
            threadPoolExecutor.submit(new ParseDate(i));
        }
    }
}


