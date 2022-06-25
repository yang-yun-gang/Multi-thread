package com.example.thread.pattern;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description : 邮箱
 * @Author : young
 * @Date : 2022-06-25 10:45
 * @Version : 1.0
 **/
public class Mailboxes {
    private static Map<Integer, GuardedObjectPlus> boxes = new ConcurrentHashMap<>();

    private static int id = 1;

    private static int generateId() {
        return id++;
    }

    public static GuardedObjectPlus getGuardedObjectPlusById(int id) {
        return boxes.remove(id);
    }

    public static GuardedObjectPlus createGuardedObjectPlus() {
        GuardedObjectPlus gop = new GuardedObjectPlus(generateId());
        boxes.put(gop.getId(), gop);
        return gop;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }
}
