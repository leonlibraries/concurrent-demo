package org.leon.concurent.threadpool;

/**
 * Created by LeonWong on 16/4/24.
 */
public interface ThreadPool<Job extends Runnable> {

    /**
     * 执行单个线程
     * @param job
     */
    void excute(Job job);

    /**
     * 关闭整个线程池,回收资源
     */
    void shutdown();

    /**
     * 增加工作者线程
     * @param num
     */
    void addWorkers(int num);

    /**
     * 减少工作者线程
     * @param num
     */
    void removeWorker(int num);

    /**
     * 正在等待执行的任务数量
     * @return
     */
    int getJobSize();
}
