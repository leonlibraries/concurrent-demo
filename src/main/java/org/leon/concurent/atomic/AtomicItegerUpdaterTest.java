package org.leon.concurent.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LeonWong on 16/6/10.
 */
public class AtomicItegerUpdaterTest {
    private static AtomicInteger couter = new AtomicInteger(0);

    @Test
    public void doUpdate() {
        couter.compareAndSet(0, 1);
        System.out.println("结果为" + couter.get());// 结果为1
        couter.compareAndSet(0, 3);
        System.out.println("结果为" + couter.get());// 结果为1
    }
}
