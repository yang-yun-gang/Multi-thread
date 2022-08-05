package com.example.thread.pattern;

/**
 * @Description : 居民
 * @Author : young
 * @Date : 2022-06-25 10:52
 * @Version : 1.0
 **/
public class People implements Runnable {

    @Override
    public void run() {
        // 创建自己的邮箱
        GuardedObjectPlus guardedObjectPlus = Mailboxes.createGuardedObjectPlus();
        System.out.println("邮箱编号：" + guardedObjectPlus.getId());
        String letter = guardedObjectPlus.get();
        System.out.println("收到信[" + guardedObjectPlus.getId() + "]内容[" + letter + "]");
    }

}
