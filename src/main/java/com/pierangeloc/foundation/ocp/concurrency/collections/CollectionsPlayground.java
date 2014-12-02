package com.pierangeloc.foundation.ocp.concurrency.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by pierangeloc on 30-11-14.
 */
public class CollectionsPlayground {

    public static void linkedListIsNotThreadSafe() {
        class ArrayListRunnable implements Runnable {

            private List<Integer> list = new LinkedList<>();

            ArrayListRunnable() {
                //populate list
                for(int i = 0; i < 100000; i++) {
                    list.add(i);
                }
            }

            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                //empty out collections
//                synchronized (this)
                {
                    while (!list.isEmpty()) {
                        //here we can expect exception!
                        System.out.println(threadName + " removed " + list.remove(0));
                    }
                }
            }
        }

        ArrayListRunnable alr = new ArrayListRunnable();
        Thread t1 = new Thread(alr, "#1");
        Thread t2 = new Thread(alr, "#2");

        t1.start();
        t2.start();
    }

    public static void iterateOverArrayListBeingModified() {
        final ArrayList<Integer> arrayList = new ArrayList();
        for(int i = 0; i < 100; i++) {
            arrayList.add(i);
        }

        Thread t1 = new Thread() {
            @Override
            public void run() {
                for(int i : arrayList) {
                    System.out.println("Thread " + getName() + " iterating: " + i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
//                Iterator<Integer> it = arrayList.iterator();
//                int index = arrayList.size();
//                while(it.hasNext()) {
//                    if(index % 2 == 0 && !(index < 0)){
//                        System.out.println("Thread " + getName() + " removing element " + it.next());
//                        it.remove();
//                    } else {
//                        it.next();
//                    }
//                    index--;
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                //modify a random element in the list
                for(int i = 0; i < 10; i++) {
                    int rand =  ThreadLocalRandom.current().nextInt(0, arrayList.size());
                    arrayList.set(rand, 9000);
                }
            }
        };
        t1.setName("Iterating-thread");
        t2.setName("Removing-even-thread");
        t1.start();
        t2.start();
    }

    public static void iterateOverArrayListBeingModifiedWithCopyOnModifyArray() {
        final List<Integer> arrayList = new CopyOnWriteArrayList<>();
        for(int i = 0; i < 100; i++) {
            arrayList.add(i);
        }

        Thread t1 = new Thread() {
            @Override
            public void run() {
                for(int i : arrayList) {
                    System.out.println("Thread " + getName() + " iterating: " + i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                Iterator<Integer> it = arrayList.iterator();
                int index = arrayList.size();
                while(it.hasNext()) {
                    if(index % 2 == 0 && !(index < 0)){
                        System.out.println("Thread " + getName() + " removing element " + it.next());
                        it.remove();
                    } else {
                        it.next();
                    }
                    index--;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.setName("Iterating-thread");
        t2.setName("Removing-even-thread");
        t1.start();
        t2.start();
    }

    public static void copyOnWriteListIsNotThreadSafe() {
        class CopyOnWriteListRunnable implements Runnable {

            private List<Integer> list = new CopyOnWriteArrayList<>();

            CopyOnWriteListRunnable() {
                //populate list
                for(int i = 0; i < 100000; i++) {
                    list.add(i);
                }
            }

            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                //empty out collections
//                synchronized (this)
                {
                    while (!list.isEmpty()) {
                        //here we can expect exception!
                        System.out.println(threadName + " removed " + list.remove(0));
                    }
                }
            }
        }

        CopyOnWriteListRunnable alr = new CopyOnWriteListRunnable();
        Thread t1 = new Thread(alr, "#1");
        Thread t2 = new Thread(alr, "#2");

        t1.start();
        t2.start();
    }


    public static void main(String[] args) {
//        linkedListIsNotThreadSafe();
//        copyOnWriteListIsNotThreadSafe();
//        iterateOverArrayListBeingModified();
//    iterateOverArrayListBeingModifiedWithCopyOnModifyArray();
    }
}
