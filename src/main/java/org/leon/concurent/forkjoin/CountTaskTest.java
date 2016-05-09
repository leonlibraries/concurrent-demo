package org.leon.concurent.forkjoin;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by LeonWong on 16/5/9.
 */
public class CountTaskTest {
    @Test
    public void testCount() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 生成计算任务,负责计算 1 +2 +3 +4
        CountTask task = new CountTask(1,10);
        // 执行一个任务
        Future<Integer> result = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
