package com.pierangeloc.foundation.ocp.concurrency.executors;

import java.util.concurrent.*;

/**
 * Created by pierangeloc on 2-12-14.
 */
public class ExecutorsPlayground {


    public static void executeRunnable() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        class MyUselessRunnable implements Runnable {
            @Override
            public void run() {
                int acc = 0;
                for(int i = 0; i < 100; i++) {
                    acc = acc + i;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("after execution of runnable, acc=" + acc);
            }
        };

        executor.execute(new MyUselessRunnable());
        System.out.println("shutting down executorService");
        executor.shutdown();
        System.out.println("service shut down, waiting for all the pending tasks to be executed!");

    }

    public static void executeRunnableAndShutdownNow() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        class MyUselessRunnable implements Runnable {
            @Override
            public void run() {
                int acc = 0;
                for(int i = 0; i < 100; i++) {
                    acc = acc + i;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("after execution of runnable, acc=" + acc);
            }
        };

        executor.execute(new MyUselessRunnable());
        System.out.println("shutting down executorService");
        executor.shutdownNow();
        System.out.println("service shut down, waiting for all the pending tasks to be executed!");

    }

    public static void scheduledExecutor() {
        class Task implements Runnable {
            @Override
            public void run() {
                System.out.println("doing chore... for 5s");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("chore done");
            }
        }

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        scheduledExecutorService.scheduleAtFixedRate(new Task(), 0, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
//        executeRunnable();
//        executeRunnableAndShutdownNow();
        scheduledExecutor();
    }
}
