package org.leon.concurent.lock;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁和非公平锁区别
 * 
 * Created by LeonWong on 16/4/28.
 */
public class FairAndUnfairTest {

    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    @Test
    public void fair() {
        testLock(fairLock);
    }

    @Test
    public void unfair() {
        testLock(unfairLock);
    }

    /**
     * 启动五个Job
     * 
     * @param lock
     */
    private void testLock(Lock lock) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Job(lock), "Thread-" + i).start();
        }
    }

    private static class Job extends Thread {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            // 连续多次打印当前Tread和队列中的Thread
            for (int i = 0; i < 4; i++) {
                lock.lock();
                try {
                    System.out.println("Lock by ['" + Thread.currentThread().getName() + "']");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> threads = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(threads);
            return threads;
        }
    }

}
