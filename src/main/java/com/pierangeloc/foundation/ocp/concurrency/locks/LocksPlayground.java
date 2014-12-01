package com.pierangeloc.foundation.ocp.concurrency.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pierangeloc on 29-11-14.
 */
public class LocksPlayground {


    public static void runConcurrentlyManyThreadsWithOneLock() {
        class ProgressChars implements Runnable {
            private final StringBuffer sb = new StringBuffer("a");

            private Lock lock = new ReentrantLock();

            @Override
            public void run() {
                lock.lock();
                for(int i = 0; i < 100; i++) {
                    System.out.println(i + 1 + ": " +  sb);
                }
                sb.setCharAt(0, (char)(sb.charAt(0) + 1));
                lock.unlock();
            }
        }

        ProgressChars progressChars = new ProgressChars();

        Thread t1 = new Thread(progressChars);
        Thread t2 = new Thread(progressChars);
        Thread t3 = new Thread(progressChars);
        Thread t4 = new Thread(progressChars);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    public static void tryLockAndSee() {
        final Lock lock = new ReentrantLock();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("Thread " + getName() + " acquired lock");
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
                System.out.println("Thread " + getName() + " released lock");

            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    System.out.println("Thread " + this.getName() + ": tryLock() returns: " + lock.tryLock() + " as other thread has lock");
                    System.out.println("Thread " + this.getName() + ": tryLock(1s) returns: " + lock.tryLock(1, TimeUnit.SECONDS) + " as other thread still has lock. \nIn 2.5 seconds it should be over, let's try to lock with timeout of  4s");
                    System.out.println("Thread " + this.getName() + ": tryLock(3s) returns: " + lock.tryLock(3, TimeUnit.SECONDS) + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        t1.setName("Thread1");
        t2.setName("Thread2");
        t1.start();
        t2.start();
    }

    public static void conditionsAsWaitNotifyReplacement() throws InterruptedException {
        final Lock lock = new ReentrantLock();

        final Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        Thread t1 = new Thread(){
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("Thread " + getName() + " acquired lock. Count to 100 and wait for next thread to notify");
                    for (int i = 0; i < 100; i++) {
                        System.out.println("Thread " + getName() + ": " + i);
                        Thread.sleep(10);
                    }
                    System.out.println("Thread " + getName() + " about to await");
                    condition1.await();
                    System.out.println("Thread " + getName() + " continuing with its stuff");
                    for (int i = 500; i < 600; i++) {
                        System.out.println("Thread " + getName() + ": " + i);
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                  e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("Thread " + getName() + " waiting for lock");
                    lock.lock();
                    System.out.println("Thread " + getName() + " acquired lock. Count to 100 and wait for next thread to notify");
                    for (int i = 0; i < 100; i++) {
                        System.out.println("Thread " + getName() + ": " + i);
                        Thread.sleep(10);
                    }
                    System.out.println("Thread " + getName() + " notifying job done");
                    condition1.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };

        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t1.start();
        Thread.sleep(10); //just to be sure t1 has started first
        t2.start();

    }

    public static void main(String[] args) throws Exception {
//        runConcurrentlyManyThreadsWithOneLock();
//        tryLockAndSee();
        conditionsAsWaitNotifyReplacement();
    }
}
