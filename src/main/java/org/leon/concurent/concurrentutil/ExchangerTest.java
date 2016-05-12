package org.leon.concurent.concurrentutil;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数据协同
 *
 * Created by LeonWong on 16/5/11.
 */
public class ExchangerTest {

    private static final Exchanger<String> exgr = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String args[]) {
        threadPool.execute(() -> {
            String A = "AAAAA";
            try {
                String B = exgr.exchange(A);
                System.out.println("A交换B后得到的值为" + B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            String B = "BBBBB";
            try {
                String A = exgr.exchange(B);
                System.out.println("B交换A后得到的值为" + A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
