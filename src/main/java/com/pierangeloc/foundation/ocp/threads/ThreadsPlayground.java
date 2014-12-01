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

    public static void triggerDeadlock() {
        final StringBuffer lockA = new StringBuffer();
        final StringBuffer lockB = new StringBuffer();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                synchronized (lockA){
                    System.out.println(this.getName() + " acquired lockA");
                    System.out.println(this.getName() + " trying to acquire lockB");
                    synchronized (lockB) {
                        System.out.println(this.getName() + " acquired lockB");
                    }
                }
            }
        };
        t1.setName("Thread1");

        Thread t2 = new Thread() {
            @Override
            public void run() {
                synchronized (lockB){
                    System.out.println(this.getName() + " acquired lockB");
                    System.out.println(this.getName() + " trying to acquire lockA");

                    synchronized (lockA) {
                        System.out.println(this.getName() + " acquired lockA");
                    }
                }
            }
        };
        t2.setName("Thread2");

        t1.start();
        t2.start();
    }

    public static void doNotTriggerDeadlockAsAcquisitionOrderIsTheSame() {
        final StringBuffer lockA = new StringBuffer();
        final StringBuffer lockB = new StringBuffer();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                synchronized (lockA){
                    System.out.println(this.getName() + " acquired lockA");
                    System.out.println(this.getName() + " trying to acquire lockB");
                    synchronized (lockB) {
                        System.out.println(this.getName() + " acquired lockB");
                    }
                }
            }
        };
        t1.setName("Thread1");

        Thread t2 = new Thread() {
            @Override
            public void run() {
                synchronized (lockA){
                    System.out.println(this.getName() + " acquired lockA");
                    System.out.println(this.getName() + " trying to acquire lockB");

                    synchronized (lockB) {
                        System.out.println(this.getName() + " acquired lockB");
                    }
                }
            }
        };
        t2.setName("Thread2");

        t1.start();
        t2.start();
    }


    public static void waitNotifyToJoinThreads() {
        final Object lock = new Object();

        Thread executor = new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("executor waiting for preparation to be performed: ");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i < 10; i++) {
                        System.out.println("executor running step: " + i);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        executor.setName("Execution Thread");

        Thread preparator = new Thread() {
            @Override
            public void run() {
                synchronized (lock) {

                    for(int i = 0; i < 100; i++) {
                        System.out.println("preparator running step: " + i);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.notify();
                }
            }
        };
        preparator.setName("Preparation Thread");

        executor.start();
        preparator.start();
    }

    public static void whenMultipleThreadsWaitingGetNotifyOnlyOneIsPickedForNotification() throws InterruptedException {
        final Object lock = new Object();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                synchronized(lock) {
                    System.out.println("Thread " + getName() + " waiting for notification");
                    try {
                        lock.wait();
                        System.out.println("Thread " + getName() + " got notified, continuing");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.setName("Thread 1 - waiting");

        Thread t2 = new Thread() {
            @Override
            public void run() {
                synchronized(lock) {
                    System.out.println("Thread " + getName() + " waiting for notification");
                    try {
                        lock.wait();
                        System.out.println("Thread " + getName() + " got notified, continuing");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t2.setName("Thread 2 - waiting");


        Thread t3 = new Thread() {
            @Override
            public void run() {
                synchronized(lock) {
                    System.out.println("Thread " + getName() + " notifying");
                    try {
                        lock.notify();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t3.setName("Thread 3 - notifying");

        t1.start();
        Thread.sleep(500);
        t2.start();
        Thread.sleep(500);
        t3.start();

        //at this point, one thread has been notified, the other one is waiting.
        // Not being daemon thread it means it keeps the main in waiting state, it doesn't finish

    }


    public static void threadThrowsException() {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                throw new NullPointerException("Booom!!!");
            }
        };

        t1.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Even if thread has thrown exception, main thread can still continue");
    }

    public static void cannotWaitOrNotifyWithoutLock() {
        try {
            ThreadsPlayground.class.wait();
        } catch (Exception e) {
            System.out.println("can't wait without lock: ");
            e.printStackTrace();
        }

        try {
            ThreadsPlayground.class.notify();
        } catch (Exception e) {
            System.out.println("can't notify without lock");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        startLoopPrintingThreads();
//        startAnotherThreadAndJoinOnIt();
//        canSynchronizedOnObjectWithinStaticMethod();
//        priorityIsAlwaysInheritedFromCreator();
//        synchronizeThreads();
//        triggerDeadlock();
//        doNotTriggerDeadlockAsAcquisitionOrderIsTheSame();
//        waitNotifyToJoinThreads();
//        cannotWaitOrNotifyWithoutLock();
//        whenMultipleThreadsWaitingGetNotifyOnlyOneIsPickedForNotification();

        threadThrowsException();
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

