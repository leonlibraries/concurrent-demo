package org.leon.concurent.volatileUsage;

/**
 * 多线程环境下的单例模式（volatile用法系列）
 * Created by LeonWong on 16/4/25.
 */
public class MultiThreadSingletonTest {

    public static void main(String[] args) {
        for (int i = 0; i < 5000; i++) {
            new Thread(new Runner(),"Runner"+i).start();
        }
    }

    private static class Runner implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance();
        }
    }
}

/**
 * 多线程环境下单例模式<br/>（volatile用法系列）
 */
class Singleton {

    private static volatile Singleton _instance;

    private Singleton() {
        // do nothing
    }

    public static Singleton getInstance() {
        if (_instance == null) {
            synchronized (Singleton.class) {
                if (_instance == null) {
                    System.out.println("堆内存中创建新实例");
                    _instance = new Singleton();
                }
            }
        }
        return _instance;
    }
}
