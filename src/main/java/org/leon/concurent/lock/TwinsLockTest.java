package org.leon.concurent.lock;

import org.junit.Test;
import org.leon.concurent.SleepUtils;

import java.util.concurrent.locks.Lock;

/**
 * Created by LeonWong on 16/4/28.
 */
public class TwinsLockTest {
    @Test
    public void test() {
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + "正在占用锁");
                        SleepUtils.sleepForSecond(10000);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }

        for (int i = 0; i < 10; i++) {
            SleepUtils.sleepForSecond(1);
            System.out.println();
        }
    }
}
