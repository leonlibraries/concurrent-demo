package org.leon.concurent.threadpool;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by LeonWong on 16/5/15.
 */
public class ScheduleThreadPoolTest {

    static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    @Test
    public void test() {
        executorService.scheduleAtFixedRate(() -> {
            System.out.println("doSTH......");
        } , 1000, 1000, TimeUnit.MILLISECONDS);

        //由于不是后台线程,所以主线程终止的时候所有线程都会被销毁,所以这里需要睡眠
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
