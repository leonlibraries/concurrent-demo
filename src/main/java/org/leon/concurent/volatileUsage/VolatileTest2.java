package org.leon.concurent.volatileUsage;

/**
 * 1、对于非volatile修饰的变量，尽管jvm的优化，会导致变量的可见性问题，但这种可见性的问题也只是在短时间内高并发的情况下发生，CPU执行时会很快刷新Cache，一般的情况下很难出现，而且出现这种问题是不可预测的，与jvm, 机器配置环境等都有关。所以在未修改flag1之前，i会一直自增。一旦flag1修改后，sleep了1s,在flag2为修改之前，while循环就退出了，所以基本不会看到输出。
 * 2、说说volatile的语义。volatile能保证可见性。其保证每次对volatile变量的读取会重新从主存中获取，以使得最新修改的值对其可见。(其大概的实现方式：每次写volatile变量时，会锁定系统总线，这样会导致其他CPU的Cache失效，这样下次读取时，CPU检测到Cache失效，会重新从主存中加载)。在jdk1.5之前，volatile只能保证可见性，但会re-order的问题，这也是著名的double-check-lock的问题(对此，可google出一大堆的文章)。在jdk1.5中，对volatile语义进行了增强，其保证jvm内存模型不会对volatile修饰的变量进行重排序(写volatile变量操作不会与其之前的读写操作重排，读volatile操作不会与其后的读写操作重排)[1], 之后double-check-lock才算实际的可用。
 * 3、volatile提供的可见性和禁止指令重排的语义可以满足一定程度的同步性需求。对于volatile变量的使用，文献[2]中给出最佳实践：
 *      写入变量时并不依赖变量的当前值，或者可以确保只有单一线程修改该变量值；
 *      变量不需要和其他成员变量一起参与类的状态不变性约束；
 *      访问变量时，没有其他额外的原因需要加锁。
 * Created by LeonWong on 16/4/25.
 */

public class VolatileTest2 extends Thread {

    // 非volatile标志
    private static boolean flag1 = false;
    // volatile标志
    private static volatile boolean flag2 = false;

    private int i = 0;

    public void run() {
        // Object o = new Object();

        // synchronized (o) {

        /*
         * 外围标志flag1为非volatile，该线程(t)跑起来后由另一线程(main)将flag1改为true后，
         * 如果出现情况1.flag1如果不从主存重新读取，那他将继续以false运行，所以会继续循环并进入内部的flag2的if判断；
         * 如果出现情况2.flag1从主存重新读取，那他将以true运行，所以会跳出循环，也就没有机会进入flag2的if判断了；
         */
        while (!flag1) {
            i++;
            // 注意 ： System.out.println(i);
            /*
             * 如果出现情况1，将进入该判断，内部标志flag2为volatile，当线程(main)将flag2改为true后，
             * 因为flag2会从主存重新读取，将以true运行，所以将跳出循环，并打印"over"语句
             */
            if (flag2) {
                System.out.println("over:" + i);
                break;
            }
        }
        System.out.println("退出循环");
        // }
    }

    public static void main(String[] args) {

        new VolatileTest2().start();

        try {
            Thread.currentThread().sleep(2000);
            // 先更改flag1
            flag1 = true;
            System.out.println("更改flag1状态");
            /*
             * 为了确保flag1的变更有机会被t察觉，
             * 并保证flag2能在flag1变为true后进行一次以上while(!flag1)条件判断后再判断if(flag2),sleep1秒（
             * 1秒可以跑很多循环了）
             */
            Thread.currentThread().sleep(1000);
            System.out.println("更改flag2状态");
            // 将flag2置为true，如果有机会进入if(flag2),则将退出循环
            flag2 = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
