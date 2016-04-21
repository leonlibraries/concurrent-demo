package org.leon.concurent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 *
 * Created by LeonWong on 16/4/21.
 */
public class WaitAndNotifyDemo {

    private static boolean flag = true;
    private static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread waitThread = new Thread(new Wait(),"WaitThread");
        waitThread.start();
        SleepUtils.sleepFor(1);
        Thread notifyThread = new Thread(new Notify(),"NotifyThread");
        notifyThread.start();
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
                    System.out.println("====lock被唤醒继续执行====");
                }
                // 条件满足,完成工作
                System.out.println(Thread.currentThread() + " flag is false. running @ "
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
                lock.notifyAll();
                flag = false;
                SleepUtils.sleepFor(5);
            }
            // 再次加锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock. notify @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.sleepFor(5);
            }
        }
    }

}
