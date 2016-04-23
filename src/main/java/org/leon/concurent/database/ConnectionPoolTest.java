package org.leon.concurent.database;

import org.leon.concurent.SleepUtils;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 建立一个数据库连接池模型,允许设置超时时间
 *
 * 这个数据库连接池模型,可以帮助理解应用并发和数据库并发的关系
 * Created by LeonWong on 16/4/22.
 */
public class ConnectionPoolTest {
    // 数据库连接池数量
    private static ConnectionPool pool = new ConnectionPool(10);
    // 保证所有ConnectionRunner 能够同时开始
    private static CountDownLatch start = new CountDownLatch(1);
    // main 线程将会等待所有ConnectionRunner结束后才能继续执行
    private static CountDownLatch end;

    public static void main(String[] args) throws Exception {
        // 线程数量,可以修改线程数量进行观察.
        int threadCount = 10;
        end = new CountDownLatch(threadCount);
        // 每个线程串行请求的连接数量 此参数不会影响到数据连接获取超时
        // 这里可以理解为应用线程数（用户并发量）,如果和数据库连接池数量一致,则不会出现超时
        int count = 30;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        /**
         * CountDownLatch是个一倒计计数器,实例化一个初始值,每次执行countDown方法,则计数器-1;<br/>
         * 在计数器没有归零之前,await使得线程进入阻塞状态;
         *
         * 这是一个让所有线程同时开始执行的方法.
         */
        start.countDown();
        end.await();
        System.out.println("total invoke :" + (threadCount * count));
        System.out.println("got connection: " + got);
        System.out.println("not got connection: " + notGot);
    }

    private static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (count > 0) {
                long start = 0;
                try {
                    // 从线程池中获取链接,如果1秒内无法获取到,将会返回null
                    // 分别统计连接获取的数量got和未获取到的数量notGot
                    Connection connection = pool.fetchConnection(100);
                    start = System.currentTimeMillis();
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            SleepUtils.sleepForMillsSecond(100);
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            System.out.println(
                                    "Single Thread (after release connection) cost : " + (System.currentTimeMillis() - start) + " ms");
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    count--;
                    System.out.println(
                            "Single Thread (connection commit) cost : " + (System.currentTimeMillis() - start) + " ms");
                }
            }
            end.countDown();
        }
    }

}
