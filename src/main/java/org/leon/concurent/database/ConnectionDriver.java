package org.leon.concurent.database;

import org.leon.concurent.SleepUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * Created by LeonWong on 16/4/22.
 */
public class ConnectionDriver {

    private static class ConnectionHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("commit")) {
                SleepUtils.sleepForMillsSecond(100);
            }
            return null;
        }
    }

    // 创建一个Connection代理,在commit时休眠1秒
    static Connection createConnection() {
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                new Class<?>[] { Connection.class }, new ConnectionHandler());
    }
}
