package org.leon.concurent.volatileUsage;

/**
 * 实验效果 <br/>
 * 1.volatile关键字修饰flag, 程序正常退出;<br/>
 * 2.去掉volatile,程序不能退出.<br/>
 *
 * （volatile用法系列）<br/>
 * Created by LeonWong on 16/4/25.
 */
public class VolatileTest extends Thread {
    private static boolean flag = false;

    public void run() {
        while (!flag)
            ;
    }

    public static void main(String[] args) throws Exception {
        new VolatileTest().start();
        Thread.sleep(2000);
        flag = true;
    }
}
