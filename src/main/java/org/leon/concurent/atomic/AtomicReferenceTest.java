package org.leon.concurent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子更新对象
 *
 * Created by LeonWong on 16/5/9.
 */
public class AtomicReferenceTest {

    public static AtomicReference<User> atomicReference = new AtomicReference<>();

    static class User {
        private String name;
        private int old;

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
        User user = new User("conan", 15);
        atomicReference.set(user);
        User updateUser = new User("shinichi", 17);
        atomicReference.compareAndSet(user, updateUser);
        System.out.println(atomicReference.get().getName());
        System.out.println(atomicReference.get().getOld());
    }
}
