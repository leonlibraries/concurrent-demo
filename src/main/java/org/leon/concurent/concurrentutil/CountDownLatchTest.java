package org.leon.concurent.concurrentutil;

import java.util.concurrent.CountDownLatch;

/**
 * Created by LeonWong on 16/5/10.
 */
public class CountDownLatchTest {

    static CountDownLatch c = new CountDownLatch(2);

    public static void main(String args[]) throws InterruptedException {
        new Thread(() -> {
            System.out.println(1);
            c.countDown();
        }).start();
        new Thread(() -> {
            System.out.println(2);
            c.countDown();
        }).start();
        c.await();
        System.out.println("3 finish");
    }

}
