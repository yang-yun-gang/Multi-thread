package com.example.thread.pattern;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @Description : 消息队列模型
 * @Author : young
 * @Date : 2022-06-25 11:54
 * @Version : 1.0
 **/
public class MessageQueue {

    private LinkedList<Msg> list = new LinkedList<>();
    // 容量
    private int cap;

    public MessageQueue(int cap) {
        this.cap = cap;
    }

    // 获取消息
    public Msg take() {
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    System.out.println("队列为空，消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Msg msg = list.removeFirst();
            System.out.println("已消费消息：" + msg);
            list.notifyAll();
            return msg;
        }
    }

    // 存入消息
    public void put(Msg msg) {
        synchronized (list) {
            while (list.size() == cap) {
                try {
                    System.out.println("队列已满，生产者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.addLast(msg);
            System.out.println("已生产消息：" + msg);
            list.notifyAll();
        }
    }

    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);

        // 模拟生产者
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> queue.put(new Msg(id, "值"+id)), "生产者" + i).start();
        }

        // 模拟消费者
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Msg take = queue.take();
            }

        }).start();
    }
}

final class Msg {
    private int id;
    private Object val;

    public Msg(int id, Object val) {
        this.id = id;
        this.val = val;
    }

    public int getId() {
        return id;
    }

    public Object getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "id=" + id +
                ", val=" + val +
                '}';
    }
}
