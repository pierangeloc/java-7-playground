package com.pierangeloc.foundation.ocp.concurrency.collections;

import java.util.ArrayList;

/**
 * Created by pierangeloc on 30-11-14.
 */
public class CollectionsPlayground {

    public static void arrayListIsNotThreadSafe() {
        class ArrayListRunnable implements Runnable {

            private ArrayList<Integer> list = new ArrayList<>();

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

    public static void main(String[] args) {
        arrayListIsNotThreadSafe();
    }
}
