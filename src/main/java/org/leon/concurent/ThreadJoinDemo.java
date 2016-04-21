package org.leon.concurent;

/**
 * 进程协同 join函数
 * 等某某线程执行结束,才能继续执行
 *
 * Created by LeonWong on 16/4/21.
 */
public class ThreadJoinDemo {

    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Domino(previous),String.valueOf(i));
            thread.start();
            previous = thread;
        }
        System.out.println("这是一个良好的开始");
        SleepUtils.sleepFor(5);
    }

    private static class Domino implements Runnable {

        private Thread thread;

        Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }
}
