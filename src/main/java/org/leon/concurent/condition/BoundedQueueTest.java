package org.leon.concurent.condition;

import org.junit.Test;
import org.leon.concurent.SleepUtils;

public class BoundedQueueTest {

    /**
     * 队列初始化size为10
     */
    private BoundedQueue<String> boundedQueue = new BoundedQueue<>(10);

    @Test
    public void testBoundedQueue() throws InterruptedException {
        // 删除队列,起初队列为空,务必陷入等待
        new Thread(new DoRemove()).start();

        SleepUtils.sleepForSecond(2);

        // 添加11条数据入队,队列“有可能”会满,并陷入等待
        for (int i = 0; i < 11; i++) {
            new Thread(new DoAdd()).start();
        }

        System.out.println("添加队列完毕");
        SleepUtils.sleepForSecond(4);

        // 再删一次
        boundedQueue.remove();

        SleepUtils.sleepForSecond(2);

        System.out.println("操作完毕,其中addIndex为" + boundedQueue.getAddIndex() + ",removeIndex为"
                + boundedQueue.getRemoveIndex() + ",count为" + boundedQueue.getCount());
    }

    private class DoRemove implements Runnable {
        @Override
        public void run() {
            try {
                boundedQueue.remove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class DoAdd implements Runnable {
        @Override
        public void run() {
            try {
                boundedQueue.add("Something");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
