package com.example.thread.lock;

/**
 * @Description : Synchronized在面向对象思想中的使用
 * @Author : young
 * @Date : 2022-06-16 11:54
 * @Version : 1.0
 **/
public class SynchronizedObj {

    static class Room {
        private int cnt = 0;

        /*public void increment() {
            synchronized (this) {
                cnt++;
            }
        }*/

        public synchronized void increment() {
            cnt++;
        }

        public void decrement() {
            synchronized (this) {
                cnt--;
            }
        }

        public int getCnt() {
            synchronized (this) {
                return cnt;
            }
        }

        /*public static synchronized void test() {

        }*/

        public static void test() {
            synchronized (Room.class) {

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Room room = new Room();
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                for (int i = 0; i < 5000; i++) {
                    room.increment();
                }
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                for (int i = 0; i < 5000; i++) {
                    room.increment();
                }
            }
        };

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(room.getCnt());
    }
}
