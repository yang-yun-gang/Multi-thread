package com.example.thread.pattern;

/**
 * @Description : 邮递员
 * @Author : young
 * @Date : 2022-06-25 11:00
 * @Version : 1.0
 **/
public class Postman implements Runnable {

    private int id;

    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObjectPlus guardedObjectPlus = Mailboxes.getGuardedObjectPlusById(id);
        System.out.println("送信[" + id + "]内容[" + mail + "]");
        guardedObjectPlus.set(mail);
    }
}
