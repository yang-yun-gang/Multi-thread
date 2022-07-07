package com.example.thread.unlock;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Description : unsafe使用
 * @Author : young
 * @Date : 2022-07-07 15:24
 * @Version : 1.0
 **/
public class UnsafeDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 获得类中的指定字段theUnsafe
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        // 由于theUnsafe私有，需要覆盖访问控制
        theUnsafe.setAccessible(true);
        // 获得这个字段的值 由于是静态字段 所以参数为null
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        System.out.println(unsafe);

        // 1.获取域的偏移地址
        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher t = new Teacher();
        // 2.执行cas操作
        unsafe.compareAndSwapInt(t, idOffset, 0, 1);
        unsafe.compareAndSwapObject(t, nameOffset, null, "lihua");

        // 3.验证
        System.out.println(t);
    }
}

@Data
class Teacher {
    volatile int id;
    volatile String name;
}
