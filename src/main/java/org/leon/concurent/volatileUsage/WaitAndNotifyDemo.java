package org.leon.concurent.volatileUsage;

import org.junit.Test;
import org.leon.concurent.SleepUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WaitAndNotifyDemo {

    // 共享内存变量
    private static volatile boolean flag = true;

    private static final Object lock = new Object();

    @Test
    public void doLauncher() throws Exception {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        SleepUtils.sleepForSecond(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
        // 防止主线程关闭后导致子线程关闭
        SleepUtils.sleepForSecond(1000000);
    }

    private static class Wait implements Runnable {
        @Override
        public void run() {
            // 加锁,拥有lock的Monitor
            synchronized (lock) {
                // 当条件不满足时,继续wait,同时释放了lock的锁
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true. wait @ "
                                + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread() + " Thread has been woke!!!!. wait @ "
                            + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }
                // 条件满足,完成工作
                System.out.println(Thread.currentThread() + " flag is false. done!!! @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    private static class Notify implements Runnable {
        @Override
        public void run() {
            // 加锁,拥有lock的Monitor
            synchronized (lock) {
                // 获取lock的锁,然后进行通知,通知时不会释放lock的锁
                // 直到当前线程释放了lock后,waitThread才能从wait方法中返回
                System.out.println(Thread.currentThread() + " hold lock. notify @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                System.out.println(Thread.currentThread() + " do notifyAll,but I wanna sleep 4 5 secs. notify @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                SleepUtils.sleepForSecond(5);
            }
            // 再次加锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock another 5 secs and. notify @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.sleepForSecond(5);
            }
        }
    }

}
