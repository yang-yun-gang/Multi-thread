package com.example.thread.jmm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @Description : final类线程安全
 * @Author : young
 * @Date : 2022-07-08 10:31
 * @Version : 1.0
 **/
public class FinalDemo {
    public static void main(String[] args) {

        dateTimeFormatterTest();

    }

    // 线程不安全
    private static void simpleDateFormatTest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (sdf) {
                    try {
                        System.out.println(sdf.parse("1951-04-21"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
    }

    // 线程安全
    private static void dateTimeFormatterTest() {
        DateTimeFormatter stf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                TemporalAccessor parse = stf.parse("1951-04-21");
                System.out.println(parse);
            }).start();
        }
    }
}
