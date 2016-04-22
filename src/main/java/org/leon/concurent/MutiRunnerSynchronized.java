package org.leon.concurent;

/**
 * 锁的排他性
 *
 * Created by LeonWong on 16/4/21.
 */
public class MutiRunnerSynchronized {

    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner1(),"Runner1");
        Thread t2 = new Thread(new Runner2(),"Runner2");
        t1.start();
        t2.start();
    }

    private static class Runner1 implements Runnable {
        @Override
        public void run() {
            synchronized (lock){
                System.out.println("Runner1 start");
                SleepUtils.sleepForSecond(5);
                System.out.println("Runner1 end");
            }
        }
    }

    private static class Runner2 implements Runnable {
        @Override
        public void run() {
            synchronized (lock){
                System.out.println("Runner2 start");
                SleepUtils.sleepForSecond(5);
                System.out.println("Runner2 end");
            }
        }
    }


}
