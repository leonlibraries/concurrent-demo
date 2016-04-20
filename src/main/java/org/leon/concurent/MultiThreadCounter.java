package org.leon.concurent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS 实现线程安全的计数器 Created by LeonWong on 16/4/19.
 */
public class MultiThreadCounter {

    private int i = 0;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        final MultiThreadCounter counter = new MultiThreadCounter();
        List<Thread> threads = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    counter.count();
                    counter.safeCount();
                }
            });
            threads.add(t);
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("非线程安全环境计数:" + counter.i);
        System.out.println("线程安全环境计数:" + counter.atomicInteger.get());
        System.out.println("共耗时:" + (System.currentTimeMillis() - start));
    }

    private void safeCount() {
        //自带原子自增方法 atomicInteger.incrementAndGet();
        while (true) {
            int i = atomicInteger.get();
            boolean suc = atomicInteger.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    private void count() {
        i++;
    }
}
