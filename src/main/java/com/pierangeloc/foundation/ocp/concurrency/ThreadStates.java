package com.pierangeloc.foundation.ocp.concurrency;

/**
 * Created by pierangeloc on 21-10-14.
 */
public class ThreadStates {

    public static void main(String[] args) throws InterruptedException {
        final Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println("xxx");
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                System.out.println("yyy");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1 actual state  is: " + t1.getState());
            }
        };

        t1.run();
        t2.run();
        synchronized (t1) {
            t1.wait();
        }

        System.out.println("t1 / 2: " + t1.getState());
    }
}
