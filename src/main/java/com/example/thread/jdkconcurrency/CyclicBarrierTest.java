package com.example.thread.jdkconcurrency;

import java.util.concurrent.CyclicBarrier;

/**
 * @Description : 循环栅栏测试
 * @Author : young
 * @Date : 2022-03-26 11:55
 * @Version : 1.0
 **/
public class CyclicBarrierTest {
    public static class Soldier implements Runnable {

        private String soldierName;
        private final CyclicBarrier cyclic;

        Soldier(CyclicBarrier cyclic, String soldierName) {
            this.cyclic = cyclic;
            this.soldierName = soldierName;
        }

        @Override
        public void run() {
            try {
                //等待士兵集结
                cyclic.await();
                doWork();
                //第二次循环
                cyclic.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void doWork() {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(soldierName + ":任务完成");
        }
    }

    // 计数完成要启动的线程
    public static class BarrierRun implements Runnable {

        boolean flag;
        int N;

        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("士兵" + N + "个，任务完成！");
            } else {
                System.out.println("士兵" + N + "个，集结完毕！");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));
        System.out.println("集合队伍！");
        for (int i = 0; i < N; i++) {
            System.out.println("士兵" + i + "报道！");
            allSoldier[i] = new Thread(new Soldier(cyclic, "士兵" + i));
            allSoldier[i].start();
        }
    }
}
