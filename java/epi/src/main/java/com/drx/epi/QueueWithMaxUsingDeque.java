package com.drx.epi;

import java.util.LinkedList;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class QueueWithMaxUsingDeque {
    // @include
    public static class Queue<T extends Comparable<T>> {
        private LinkedList<T> Q_ = new LinkedList<T>();
        private LinkedList<T> D_ = new LinkedList<T>();

        public void enqueue(T x) {
            Q_.addFirst(x);
            while(!D_.isEmpty() && D_.getLast().compareTo(x) < 0) {
                D_.removeLast();
            }
            D_.addLast(x);
        }

        public T dequeue() {
            if(!Q_.isEmpty()) {
                T ret = Q_.removeLast();
                if(ret.equals(D_.getFirst())) {
                    D_.removeFirst();
                }
                return ret;
            }
            throw new RuntimeException("empty queue");
        }

        public T max() {
            if(!D_.isEmpty()) {
                return D_.getFirst();
            }
            throw new RuntimeException("empty queue");
        }
        // @exclude

        public T head() {
            return Q_.getLast();
        }
        // @include
    }
    // @exclude

    private static <T extends Comparable<T>> void assertDequeue(Queue<T> q, T t) {
        T dequeue = q.dequeue();
        assert(t.equals(dequeue));
    }

    public static void main(String[] args) {
        Queue<Integer> Q = new Queue<Integer>();
        Q.enqueue(1);
        Q.enqueue(2);
        assert(2 == Q.max());
        assertDequeue(Q, 1);
        assert(2 == Q.max());
        assertDequeue(Q, 2);
        Q.enqueue(3);
        assert(3 == Q.max());
        assertDequeue(Q, 3);
        try {
            Q.max();
        } catch(RuntimeException e) {
            System.out.println(e.getMessage());
        }
        try {
            Q.dequeue();
        } catch(RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
