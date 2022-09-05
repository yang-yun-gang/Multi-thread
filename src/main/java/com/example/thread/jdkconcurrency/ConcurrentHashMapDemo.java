package com.example.thread.jdkconcurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Description : ConcurrentHashMap
 * @Author : young
 * @Date : 2022-08-09 12:08
 * @Version : 1.0
 **/
public class ConcurrentHashMapDemo {

    public static ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, LongAdder> safeMap = new ConcurrentHashMap<>();


    public static void main(String[] args) {

        // 以下线程不安全
        if (!map.containsKey("test")) {
            map.put("test", 1l);
        } else {
            Long num = map.get("test");
            num++;
            map.put("test", num);
        }


        // 以下线程安全
        LongAdder num = safeMap.computeIfAbsent("test", (key) -> new LongAdder());
        num.increment();

    }
}
