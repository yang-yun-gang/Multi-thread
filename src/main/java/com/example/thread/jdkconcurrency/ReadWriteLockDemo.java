package com.example.thread.jdkconcurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description : 读写锁
 * @Author : young
 * @Date : 2022-08-05 11:40
 * @Version : 1.0
 **/
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        DataContainer dataContainer = new DataContainer();
        new Thread(dataContainer::read, "t1").start();
        new Thread(dataContainer::write, "t2").start();
    }
}

// 读写锁基本应用
class DataContainer {

    private static final Logger log = LoggerFactory.getLogger(ReadWriteLockDemo.class);
    private Object data;
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();

    public Object read() {
        log.info("获取读锁...");
        r.lock();
        try {
            log.info("读取");
            TimeUnit.SECONDS.sleep(1);
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            log.info("释放读锁...");
            r.unlock();
        }

    }

    public void write() {
        log.info("获取写锁...");
        w.lock();
        try {
            TimeUnit.SECONDS.sleep(2);
            log.info("写入");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("释放写锁...");
            w.unlock();
        }

    }
}

// 读写锁应用之缓存一致性
class CacheReadWriteLock {

    @Autowired
    private RedisTemplate redisTemplate;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // 查询
    public Object query() {

        /**
         为什么要加读锁？
         假如线程1要去读，线程2要去改
         线程1先进行if判断，进入if内，此时线程2删除了redis
         线程1get的时候就会为null

         读写锁比synchronized和ReentrantLock更加高效，读之间并不互斥
         **/
        // 读锁
        lock.readLock().lock();
        // 如果redis有，直接返回
        try {
            if (redisTemplate.hasKey("test")) {
                return redisTemplate.opsForValue().get("test");
            }
        } finally {
            lock.readLock().unlock();
        }

        // 写锁
        lock.writeLock().lock();
        try {
            // 双重检查 或许会有多个线程
            if (redisTemplate.hasKey("test")) {
                return redisTemplate.opsForValue().get("test");
            }
            // 如果redis没有，则查询数据库
            Object data = sqlOpt();
            // 放入redis
            redisTemplate.opsForValue().set("test", data);

            return data;
        } finally {
            lock.writeLock().unlock();
        }

    }

    /**
     线程1做查询操作，线程2做更新操作
     如果线程2先删除了缓存，此时线程1查库并放入缓存，线程2又修改了数据库，就会造成数据不一致
     （数据库已被修改，但缓存还是原来的）
     加入写锁之后，删除缓存和修改数据库两个操作变为原子操作，线程2删除了缓存时，线程1必须等待，就解决了一致性问题
     **/
    // 修改
    public void update() {
        // 写锁
        lock.writeLock().lock();
        try {
            // 删除redis缓存
            redisTemplate.delete("test");
            // 修改数据库
            sqlUpdate();
        } finally {
            lock.writeLock().unlock();
        }

    }

    // 查询数据库
    private Object sqlOpt() {
        return new Object();
    }

    // 修改数据库
    private void sqlUpdate() {

    }

}
