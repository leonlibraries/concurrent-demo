package org.leon.concurent.lock;

import org.leon.concurent.SleepUtils;

/**
 * Created by LeonWong on 16/4/26.
 */
public class MutexTest {

    public static final Mutex lock = new Mutex();

    public static void main(String args[]) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runner(), "Runner" + i).start();
        }
    }

    static class Runner implements Runnable {
        @Override
        public void run() {
            lock.lock();
            System.out.println("简易测试锁程序 开始");
            SleepUtils.sleepForSecond(5);
            System.out.println("简易测试锁程序 结束");
            lock.unlock();
        }
    }
}
