package com.example.thread.pattern;

/**
 * @Description : 保护性暂停
 * @Author : young
 * @Date : 2022-06-24 16:43
 * @Version : 1.0
 **/
public class GuardedObject {
    // 结果
    private String res;

    public String get() {
        synchronized (this) {
            // 没有结果 等待结果
            while (res == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return res;
        }
    }

    public String get(long timeout) {
        synchronized (this) {
            // 没有结果 等待结果
            // 开始时间
            long begin = System.currentTimeMillis();
            // 经历的时间
            long passTime = 0;
            while (res == null) {
                // 这一轮循环应该等待的时间
                long waitTime = timeout - passTime;
                if (waitTime <= 0) {
                    break;
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passTime = System.currentTimeMillis() - begin;
            }
            return res;
        }
    }

    // 产生结果
    public void set(String s) {
        synchronized (this) {
            res = s;
            this.notifyAll();
        }
    }

    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                // 获取结果
                System.out.println("t1 等待结果");
                String res = guardedObject.get();
                System.out.println("t1 拿到结果啦:" + res);
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                System.out.println("t2 正在产生结果");
                // 产生结果
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String res = "nihao";
                guardedObject.set(res);
            }
        };

        t1.start();
        t2.start();
    }
}
