package com.example.thread.unlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description : CAS无锁
 * @Author : young
 * @Date : 2022-07-03 17:51
 * @Version : 1.0
 **/
public class UnlockDemo {
    public static void main(String[] args) {
        CountGame.game(new CountGameForLock(10000));
        CountGame.game(new CountGameForUnlock(10000));
    }
}

interface CountGame {

    int getCnt();

    void decrement(int num);

    static void game(CountGame game) {
        List<Thread> list = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            list.add(new Thread(()->game.decrement(10), "t" + i));
        }

        long start = System.nanoTime();
        // 启动线程
        list.forEach(Thread::start);
        // 等待线程执行结束
        list.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(game.getCnt() + " cost:" + (end - start)/1000_000 + "ms");
    }
}

// 使用锁的方式保证原子性
class CountGameForLock implements CountGame{
    private int cnt;

    public CountGameForLock(int cnt) {
        this.cnt = cnt;
    }

    public int getCnt() {
        return cnt;
    }

    @Override
    public void decrement(int num) {
        synchronized (this) {
            cnt -= num;
        }
    }

}

// 使用cas保证原子性
class CountGameForUnlock implements CountGame{
    private AtomicInteger cnt;

    public CountGameForUnlock(Integer cnt) {
        this.cnt = new AtomicInteger(cnt);
    }

    public int getCnt() {
        return cnt.get();
    }

    @Override
    public void decrement(int num) {
        // 不断进行比较和交换
        while (true) {
            // 预期值
            int prev = cnt.get();
            // 修改值
            int next = prev - num;
            // cas 如果cas成功 返回true
            if (cnt.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}
