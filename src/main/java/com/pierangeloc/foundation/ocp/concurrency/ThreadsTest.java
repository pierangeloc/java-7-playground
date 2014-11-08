package com.pierangeloc.foundation.ocp.concurrency;

/**
 * Created by pierangeloc on 4-11-14.
 */
public class ThreadsTest {
    public static void main(String[] args) {
        Thread currentThread = Thread.currentThread();
//        currentThread.setPriority(Thread.MAX_PRIORITY);

        MyRunnableTest myRunnableTest = new MyRunnableTest();

        Thread thread1 = new Thread(myRunnableTest);
        thread1.setName("First");
        Thread thread2 = new Thread(myRunnableTest);
        thread2.setName("Second ");
        thread2.setPriority(Thread.MIN_PRIORITY);

        thread1.start();
        thread2.start();
    }
}


class MyRunnableTest implements Runnable {


    public void run(){
        Thread currentThread = Thread.currentThread();
        for ( int i = 1 ; i < 100; i++ ) {
            if ( (i % 10) == 0){
                System.out.println("Thread [" + currentThread.getName() + "] : " + i + " Thread Priority :" + currentThread.getPriority());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Absorb
            }
        }
    }
}