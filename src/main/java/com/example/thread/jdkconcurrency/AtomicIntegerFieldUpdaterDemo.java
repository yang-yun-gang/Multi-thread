package com.example.thread.jdkconcurrency;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Description : AtomicIntegerFieldUpdater
 * @Author : young
 * @Date : 2022-07-06 11:40
 * @Version : 1.0
 **/
public class AtomicIntegerFieldUpdaterDemo {
    private static AtomicIntegerFieldUpdater<Client> a = AtomicIntegerFieldUpdater.newUpdater(Client.class, "old");

    public static void main(String[] args) {
        Client client = new Client("lihua", 8);
        a.compareAndSet(client, a.get(client), 10);
        System.out.println(a.get(client));
    }
}

class Client {
    private String name;
    public volatile int old;

    public Client(String name, int old) {
        this.name = name;
        this.old = old;
    }

    public String getName() {
        return name;
    }

    public int getOld() {
        return old;
    }
}


