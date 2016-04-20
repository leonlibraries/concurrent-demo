package org.leon.concurent;

/**
 * Created by LeonWong on 16/4/19.
 */
public class SynchronizeDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(SynchronizeDemo::doSth);
        Thread t2 = new Thread(SynchronizeDemo::doSth);
        t1.start();
        t2.start();
    }

    public synchronized static void doSth() {
        try {
            System.out.println("正在执行方法");
            Thread.sleep(10000);
            System.out.println("正在退出方法");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
