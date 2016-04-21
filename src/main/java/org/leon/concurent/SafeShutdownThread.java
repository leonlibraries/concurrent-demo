package org.leon.concurent;

import java.util.concurrent.TimeUnit;

/**
 * 如何安全的终止线程
 * <p>
 * Created by LeonWong on 16/4/20.
 */
public class SafeShutdownThread {
    public static void main(String[] args) throws Exception {
        Thread one = new Thread(new Runner(), "CountThread");
        one.start();
        TimeUnit.SECONDS.sleep(1);
        one.interrupt();

        Runner twoRunner = new Runner();
        Thread two = new Thread(twoRunner, "CountThread");
        two.start();
        TimeUnit.SECONDS.sleep(1);
        twoRunner.cancel();
    }

    private static class Runner implements Runnable {
        private long i;
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Count i = " + i);
        }

        private void cancel() {
            on = false;
        }

    }

}
