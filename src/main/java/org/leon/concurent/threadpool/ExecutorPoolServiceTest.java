package org.leon.concurent.threadpool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by LeonWong on 16/5/11.
 */
public class ExecutorPoolServiceTest {

    static ExecutorService executorService = Executors.newFixedThreadPool(2);

    /**
     * submit 方法和 execute 区别在于,submit用于需要返回值的任务
     */
    @Test
    public void testSubmit() {
        Future futureNoRet = executorService.submit(() -> System.out.println("OK"));

        Future futureRet = executorService.submit(() -> {
            System.out.println("OK");
            return ">>>线程的返回值";
        });

        try {
            System.out.println(futureNoRet.get());
            System.out.println(futureRet.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
