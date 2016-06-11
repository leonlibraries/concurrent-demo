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

    private static Lock fairLock = new ReentrantLockRev(true);
    private static Lock unfairLock = new ReentrantLockRev(false);

    @Test
    public void fair() {
        testLock(fairLock);
    }

    @Test
    public void unfair() {
        testLock(unfairLock);
    }

    /**
     * 启动十个Job
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
            lock.lock();
            try {
                // 连续多次打印当前Tread和队列中的Thread
                for (int i = 0; i < 6; i++) {
                    System.out.println("Lock by ['" + Thread.currentThread().getName() + "']");
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private static class ReentrantLockRev extends ReentrantLock {
        public ReentrantLockRev(boolean fair) {
            super(fair);
        }

        // 颠倒列表顺序
        public Collection<Thread> getQueuedThreads() {
            List<Thread> threads = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(threads);
            return threads;
        }
    }

}
