package org.leon.concurent.blockedandnonblocked;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 尝试自己用CAS算法实现队列(基于数组来实现)
 * 
 * Created by LeonWong on 16/5/9.
 */
public class MyOwnConcurrentQueue<E> extends AbstractQueue<E> implements Queue<E>, java.io.Serializable {

    protected MyOwnConcurrentQueue() {
        super();
    }

    @Override
    public boolean add(E e) {
        return super.add(e);
    }

    @Override
    public E remove() {
        return super.remove();
    }

    @Override
    public E element() {
        return super.element();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return super.addAll(c);
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return false;
    }

    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    @Override
    public Stream<E> stream() {
        return null;
    }

    @Override
    public Stream<E> parallelStream() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super E> action) {

    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
