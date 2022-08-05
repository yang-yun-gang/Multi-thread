package com.example.thread.jmm;

/**
 * @Description : final变量的读，采用复制方式
 * @Author : young
 * @Date : 2022-07-09 16:12
 * @Version : 1.0
 **/
public class FinalReader {
    public static void main(String[] args) {
        System.out.println(TestFinal.A);
    }
}

class TestFinal {
    static final int A = 10;
}
