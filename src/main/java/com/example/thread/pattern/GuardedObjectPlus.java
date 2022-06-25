package com.example.thread.pattern;

import java.util.concurrent.TimeUnit;

/**
 * @Description : 解耦等待和生产
 * @Author : young
 * @Date : 2022-06-25 10:44
 * @Version : 1.0
 **/
public class GuardedObjectPlus {

    private int id;

    public GuardedObjectPlus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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

    // 产生结果
    public void set(String s) {
        synchronized (this) {
            res = s;
            this.notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new Thread(new People()).start();
        }
        TimeUnit.SECONDS.sleep(1);
        for (Integer id : Mailboxes.getIds()) {
            new Thread(new Postman(id, "邮件" + id)).start();
        }
    }
}
