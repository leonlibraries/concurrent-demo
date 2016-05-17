package org.leon.concurent.futuretask;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 创建多个线程,确保每个线程执行完得到结果后才会执行下一个线程
 *
 * Created by LeonWong on 16/5/15.
 */
public class FutureTaskTest {

    private final ConcurrentMap<Object, Future<String>> taskCache = new ConcurrentHashMap<>();

    private String executionTask(final String taskName) throws ExecutionException, InterruptedException {
        while (true) {
            Future<String> future = taskCache.get(taskName);
            if (future == null) {
                // 定义任务
                Callable<String> task = () -> taskName;
                // 创建任务
                FutureTask<String> futureTask = new FutureTask<>(task);
                future = taskCache.putIfAbsent(taskName, futureTask);
                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }
            try {
                return future.get();
            } catch (CancellationException e) {
                e.printStackTrace();
                taskCache.remove(taskName, future);
            }
        }
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            FutureTaskTest ftt = new FutureTaskTest();
            System.out.println(ftt.executionTask("TASK-" + i));
        }
    }
}
