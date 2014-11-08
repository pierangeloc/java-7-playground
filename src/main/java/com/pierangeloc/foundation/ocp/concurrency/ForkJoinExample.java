package com.pierangeloc.foundation.ocp.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by pierangeloc on 19-10-14.
 */
public class ForkJoinExample {

    private static final long N = 1_000_000;
    public static final int NUM_THREADS = 10;
    public static final long MIN_DELTA = N / NUM_THREADS;

    /**
     *  Define the recursive task to sum a sub-interval from-to. It is a task as it returns a value
     */
    static class RecursiveSumInInterval extends RecursiveTask<Long> {
        private long from;
        private long to;

        RecursiveSumInInterval(long from, long to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            if (to - from < MIN_DELTA) {
                System.out.println("executing direct calculation on " + this);
                return executeSum(from, to);
            } else {
                //spawn subtasks
                long mid = (from + to) / 2;
                // define left subtask
                RecursiveSumInInterval left = new RecursiveSumInInterval(from, mid);
                //push the task in front of the execution queue. Will await later for result
                System.out.println("spawning new " + left);
                left.fork();
                RecursiveSumInInterval right = new RecursiveSumInInterval(mid + 1, to);
                System.out.println("executing compute on " + right);
                //why compute() and not fork() ? why calling it synchronously and not asynchronously?
                long rightResult = right.compute();
                System.out.println("joining " + left + " and returning result of " + this);

                return left.join() + rightResult;
            }
        }

        //execute sum in [f, t]
        private long executeSum(long f, long t) {
            long res = 0;
            for(long i = f; i <= t; i++) {
                res += i;
            }
            System.out.println("executed sum from " + f + " to " + t +"; returning result: " + res);
            return res;
        }

        @Override
        public String toString() {
            return "RecursiveSumInInterval(" + from + ", " + to + ")";
        }
    }


    private static final int ARRAY_LENGTH = 10_000;
    private static final int MIN_INTERVAL = ARRAY_LENGTH / NUM_THREADS;
    private static final int searchKey = 100;
    private static int[] arrayToSearch;

    static class SearchAction extends RecursiveAction {
        private int from;
        private int to;

        SearchAction(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected void compute() {
            if((to - from) <= MIN_INTERVAL) {
                //look directly in this small interval
                for(int i = from; i <= to; i++) {
                    if(arrayToSearch[i] == searchKey) {
                        System.out.println("Found the searchKey" + searchKey + " at index " + i);
                    }
                }
            } else {
                int mid = (to + from) / 2;
                SearchAction left = new SearchAction(from, mid);
                SearchAction right = new SearchAction(mid + 1, to);
                System.out.println("spawning search: " + left + " and " + right);
//                left.fork();
//                right.compute();
                //alternative:
                invokeAll(left, right);
            }
        }


        @Override
        public String toString() {
            return "SearchAction(" + from + ", " + to + ")";
        }
    }

    public static void main(String[] args) {
        //create a pool
        ForkJoinPool forkJoinPool = new ForkJoinPool(NUM_THREADS);
        System.out.println("testing recursive sum calculation");
        long computedSum = forkJoinPool.invoke(new RecursiveSumInInterval(1, ARRAY_LENGTH));
        long formulaSum = ARRAY_LENGTH * (ARRAY_LENGTH + 1) / 2;

        System.out.println("done with computation. Computed: " + computedSum + "; formula: " + formulaSum);

        System.out.println("testing search of key " + searchKey);
        //prepare array
        arrayToSearch = new int[ARRAY_LENGTH];
        for(int i = 0; i < ARRAY_LENGTH; i++) {
            arrayToSearch[i] = ThreadLocalRandom.current().nextInt(0, 1000);
        }
        ForkJoinPool fjp = new ForkJoinPool(NUM_THREADS);
        fjp.execute(new SearchAction(0, ARRAY_LENGTH - 1));


    }
}
