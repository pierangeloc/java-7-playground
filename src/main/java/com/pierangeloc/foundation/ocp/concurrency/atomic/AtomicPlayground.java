package com.pierangeloc.foundation.ocp.concurrency.atomic;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pierangeloc on 29-11-14.
 */
public class AtomicPlayground {

    interface Incrementer {
        void increment();

        int getCounter();
    }

    static class NonThreadSafeIncrementer implements Incrementer {
        int counter = 0;
        @Override
        public void increment() {
            counter++;
        }
        @Override
        public int getCounter() {
            return this.counter;
        }
    }

    static class AllSynchronizedIncrementer implements Incrementer {
        int counter = 0;
        @Override
        public synchronized void increment() {
            counter++;
        }
        @Override
        public int getCounter() {
            return this.counter;
        }
    }

    static class AtomicIncrementer implements Incrementer {
        AtomicInteger counter = new AtomicInteger(0);
        @Override
        public synchronized void increment() {
            counter.getAndIncrement();
        }
        @Override
        public int getCounter() {
            return this.counter.intValue();
        }
    }

    static class IncrementingThread extends Thread {
        private Incrementer incrementer;
        IncrementingThread(Incrementer incrementer) {
            this.incrementer = incrementer;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                incrementer.increment();
            }
        }
    }

    public static void nonThreadSafeStateSharingLeadsToWeirdResults() throws InterruptedException {
        long now;

        Incrementer atomicIncrementer = new AtomicIncrementer();
        now = new Date().getTime();
        Thread t5 = new IncrementingThread(atomicIncrementer);
        Thread t6 = new IncrementingThread(atomicIncrementer);
        t5.start();
        t6.start();
        t5.join();
        t6.join();
        System.out.println("incrementer value after both threads have been running: " + atomicIncrementer.getCounter());
        System.out.println("took: " + (new Date().getTime() - now) + " ms");


        Incrementer incrementer = new NonThreadSafeIncrementer();
        now = new Date().getTime();
        Thread t1 = new IncrementingThread(incrementer);
        Thread t2 = new IncrementingThread(incrementer);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("incrementer value after both threads have been running: " + incrementer.getCounter());
        System.out.println("took: " + (new Date().getTime() - now) + " ms");

        Incrementer synchronizedIncrementer = new AllSynchronizedIncrementer();
        now = new Date().getTime();
        Thread t3 = new IncrementingThread(synchronizedIncrementer);
        Thread t4 = new IncrementingThread(synchronizedIncrementer);
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        System.out.println("incrementer value after both threads have been running: " + synchronizedIncrementer.getCounter());
        System.out.println("took: " + (new Date().getTime() - now) + " ms");


    }

    public static void main(String[] args) throws InterruptedException {
        nonThreadSafeStateSharingLeadsToWeirdResults();
    }

}
