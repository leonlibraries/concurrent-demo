package org.leon.concurent.database;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * Created by LeonWong on 16/4/22.
 */
public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                // 连接释放后需要进行通知,这样其他消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    // 在mills 内无法获取到连接, 将会返回 null
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            // 不设置超时,将陷入无线等待
            System.out.println("FetchConnection start! timeout : " + mills + " ms");
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    // 当连接池没有资源的时候,pool陷入等待,并且把锁交出去,等到被唤醒并且获得锁之后,代码继续执行
                    pool.wait();
                }
                System.out.println("FetchConnection end! timeout : " + mills + " ms");
                return pool.removeFirst();
            }
            // 设置超时时间
            else {
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    // 貌似这一步有点多余
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                if (result == null) {
                    System.out.println("FetchConnection end! timeout : " + mills + " ms, BUT FAILED!!!");
                } else {
                    System.out.println("FetchConnection end! timeout : " + mills + " ms");
                }
                return result;
            }
        }
    }
}
