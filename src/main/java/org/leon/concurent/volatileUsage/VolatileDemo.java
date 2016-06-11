package org.leon.concurent.volatileUsage;

/**
 * VolatileDemo 与 SynchronizedDemo 实现效果等价
 *
 * Created by LeonWong on 16/6/10.
 */
public class VolatileDemo {
    volatile long vl = 0L;

    public void set(long l) {
        vl = l;
    }

    public void getAndIncrement() {
        vl++;
    }

    public long get() {
        return vl;
    }
}

class SynchronizedDemo {
    long vl = 0L;

    public synchronized void set(long l) {
        vl = l;
    }

    public void getAndIncrement() {
        long temp = get();
        temp += 1L;
        set(temp);
    }

    public synchronized long get() {
        return vl;
    }
}