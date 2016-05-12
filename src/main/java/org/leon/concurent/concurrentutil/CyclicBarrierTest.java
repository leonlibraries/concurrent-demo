package org.leon.concurent.concurrentutil;

import java.util.concurrent.CyclicBarrier;

/**
 * 线程执行await方法,表示到达CyclicBarrier屏障,当达到指定数目后所有线程才可以往下执行;
 *
 * 与CountDownLatch 不同的是,CyclicBarrier可以使用reset方法重置
 *
 * Created by LeonWong on 16/5/10.
 */
public class CyclicBarrierTest {

    /**
     * 这里设置为3,但实际上只有2个线程调用了await方法,因此将永远阻塞
     */
    static CyclicBarrier c = new CyclicBarrier(3);

    public static void main(String args[]) {
        new Thread(() -> {
            try {
                c.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(1);
        }).start();
        try {
            c.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(2);
    }
}
