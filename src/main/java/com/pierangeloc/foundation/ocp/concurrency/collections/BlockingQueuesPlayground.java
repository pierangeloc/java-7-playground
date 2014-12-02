package com.pierangeloc.foundation.ocp.concurrency.collections;

import java.util.concurrent.*;

/**
 * Created by pierangeloc on 2-12-14.
 */
public class BlockingQueuesPlayground {

    public static void generalBlockingQueue() throws InterruptedException {
        //blocking queue of 5 elements
        final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(5);


        blockingQueue.put(1);
        blockingQueue.put(2);
        blockingQueue.put(3);
        blockingQueue.put(4);
        blockingQueue.put(5);
        System.out.println("blocking queue of 5 is full. \nNow we start a Thread that in 2s will remove 2 elements. The next put call will be blocked until the thread will be complete");

        Thread removingThread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("Thread " + getName() + " removing element");
                    System.out.println("Thread " + getName() + " removed: " + blockingQueue.take());
                    System.out.println("Thread " + getName() + " removed: " + blockingQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        removingThread.start();

        System.out.println("trying to insert 6");
        blockingQueue.put(6);
        System.out.println("inserted 6");
        System.out.println("trying to insert 7" );
        blockingQueue.put(7);
        System.out.println("inserted 7" );
        System.out.println("trying to insert 8" );
        //here we are blocked
        blockingQueue.put(8);
        System.out.println("inserted 8");

    }

    public static void synchronousQueue() {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
    }

    public static void linkedTransferQueue() {
        LinkedTransferQueue<Integer> linkedTransferQueue = new LinkedTransferQueue<>();


    }

    public static void main(String[] args) throws InterruptedException {
        generalBlockingQueue();
    }
}
