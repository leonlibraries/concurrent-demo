package org.leon.concurent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 原子更新字段类(比起更新对象,这个粒度更小)
 *
 * Created by LeonWong on 16/5/9.
 */
public class AtomicIntegerFieldUpdaterTest {
    /** 一个类中的一个字段对应一个updater **/
    private static AtomicIntegerFieldUpdater<User> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater
            .newUpdater(User.class, "old");

    static class User {
        private String name;
        // 此处必须public
        public volatile int old;

        public User(String name, int old) {
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

    public static void main(String args[]) {
        // 设置柯南年龄为10岁
        User conan = new User("Conan", 10);
        // 柯南长大一岁,但这里先Get后Inc,所以返回10
        System.out.println(atomicIntegerFieldUpdater.getAndIncrement(conan));
        // 新岁数 11
        System.out.println(conan.getOld());
    }
}
