package org.leon.concurent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有界队列<br/>
 * 当队列为空,队列获取操作将会阻塞获取线程,直到队列中有新元素;<br/>
 * 当队列已满,
 * <p>
 * Created by LeonWong on 16/4/29.
 */
public class BoundedQueue<T> {
    private Object[] items;
    // 添加的下标,删除的下标和数组当前数量
    private int addIndex, removeIndex, count;
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BoundedQueue() {
        items = new Object[5];
    }

    public BoundedQueue(int size) {
        items = new Object[size];
    }

    /**
     * 添加一个元素,数组满则添加线程进入等待状态
     *
     * @param t
     * @throws InterruptedException
     */
    public void add(T t) throws InterruptedException {
        lock.lock();
        try {
            while (items.length == count) {
                System.out.println("添加队列--陷入等待");
                notFull.await();
            }
            items[addIndex] = t;
            addIndex = ++addIndex == items.length ? 0 : addIndex;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 删除一个元素,数组空则进入等待
     *
     * @return
     * @throws InterruptedException
     */
    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                System.out.println("删除队列--陷入等待");
                notEmpty.await();
            }
            Object tmp = items[removeIndex];
            items[removeIndex] = null;// 这一步可以有可无
            removeIndex = ++removeIndex == items.length ? 0 : removeIndex;
            count--;
            notFull.signal();
            return (T) tmp;
        } finally {
            lock.unlock();
        }
    }

    public Object[] getItems() {
        return items;
    }

    public void setItems(Object[] items) {
        this.items = items;
    }

    public int getAddIndex() {
        return addIndex;
    }

    public void setAddIndex(int addIndex) {
        this.addIndex = addIndex;
    }

    public int getRemoveIndex() {
        return removeIndex;
    }

    public void setRemoveIndex(int removeIndex) {
        this.removeIndex = removeIndex;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}

