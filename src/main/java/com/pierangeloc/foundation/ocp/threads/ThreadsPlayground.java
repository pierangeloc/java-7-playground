package com.pierangeloc.foundation.ocp.threads;

public class ThreadsPlayground {


    public static void startLoopPrintingThreads() {
        Runnable job = new LoopPrintingRunnable();
        Thread t1 = new Thread(job);
        t1.setName("Einstein");
        Thread t2 = new Thread(job);
        t2.setName("Feynman");
        Thread t3 = new Thread(job);
        t3.setName("Poincare");
        t1.start();
        t2.start();
        t3.start();
    }

    public static void startAnotherThreadAndJoinOnIt() throws InterruptedException {
        System.out.println("MAIN: executing main thread");
        Thread t = new Thread() {
            @Override
            public void run() {
                for(int i = 0; i < 10; i++) {
                    System.out.println("Thread " + this.getName() + ": step " + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        t.setName("SubThread");

        t.start();
        t.join();

        System.out.println("MAIN: after starting SubThread");

    }

    public static synchronized void canSynchronizedOnObjectWithinStaticMethod() {
        //a static method can have a block where it synchronize on a non-static instance
        synchronized (new Object()) {

        }
    }

    public static void priorityIsAlwaysInheritedFromCreator() {
        Runnable runnableThreadFactory = new Runnable() {
            @Override
            public void run() {
                Thread t1 = new Thread();
                System.out.println("created thread, priority: " + t1.getPriority());
            }
        };

        Thread tMax = new Thread(runnableThreadFactory);
        tMax.setPriority(Thread.MAX_PRIORITY);
        tMax.start();

        Thread tMin = new Thread(runnableThreadFactory);
        tMin.setPriority(Thread.MIN_PRIORITY);
        tMin.start();
    }


    public static void synchronizeThreads() {
        class SynchronizedPrinter extends Thread {
            private final StringBuffer sb;

            SynchronizedPrinter(StringBuffer sb) {this.sb = sb;}

            @Override
            public void run() {
                //if you comment this line you will see mess going on
                synchronized (sb)
                {
                    for(int i = 0; i < 100; i++) {
                        System.out.println(i + 1 + ": " +  sb);
                    }
                    sb.setCharAt(0, (char)(sb.charAt(0) + 1));
                }
            }
        }

        StringBuffer lock = new StringBuffer("A");
        Thread synchronizedPrinter1 = new SynchronizedPrinter(lock);
        Thread synchronizedPrinter2 = new SynchronizedPrinter(lock);
        Thread synchronizedPrinter3 = new SynchronizedPrinter(lock);
        synchronizedPrinter1.start();
        synchronizedPrinter2.start();
        synchronizedPrinter3.start();

    }

    public static void main(String[] args) throws Exception {
//        startLoopPrintingThreads();
//        startAnotherThreadAndJoinOnIt();
//        canSynchronizedOnObjectWithinStaticMethod();
//        priorityIsAlwaysInheritedFromCreator();
        synchronizeThreads();
    }

    static class LoopPrintingRunnable implements Runnable {

        @Override
        public void run() {
            for(int i = 0; i < 100; i++) {
                if(i % 10 == 0) {
                    System.out.println(Thread.currentThread().getName() + " prints: " + i);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        }
    }


}

