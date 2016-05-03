package org.leon.concurent.condition;

import org.junit.Test;
import org.leon.concurent.SleepUtils;

/**
 * 测试通过<br/>
 * Created by LeonWong on 16/4/29.
 */
public class BoundedQueueTest {

    private BoundedQueue<String> boundedQueue = new BoundedQueue<>(10);

    @Test
    public void testBoundedQueue() throws InterruptedException {
        new Thread(new DoRemove()).start();
        SleepUtils.sleepForSecond(2);
        for (int i = 0; i < 11; i++) {
            new Thread(new DoAdd()).start();
        }
        SleepUtils.sleepForSecond(4);
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
