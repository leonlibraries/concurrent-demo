package org.leon.concurent.volatileUsage;

import org.leon.concurent.SleepUtils;

/**
 * 简易计数器 实验目的: <br/>
 * 深入理解volatile 关键字和static 关键字的 差异部分 <br/>
 * Created by LeonWong on 16/4/24.
 */
public class VolatileAndStatic {

    private static final Object lock = new Object();


    public static void main(String[] args) throws Exception {
        Runner runner = new Runner();
        for (int i = 0; i < 1000; i++) {
            new Thread(runner).start();
        }
    }

}

class Runner implements Runnable {
    private volatile long count;

    @Override
    public void run() {
        SleepUtils.sleepForSecond(1);
        count++;
//        System.out.println(count);
        if(count == 999l){
            System.out.println("OK");
        }
    }

}