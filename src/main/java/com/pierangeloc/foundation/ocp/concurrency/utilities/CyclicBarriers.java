package com.pierangeloc.foundation.ocp.concurrency.utilities;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by pierangeloc on 13-10-14.
 */
public class CyclicBarriers {
    //like CountDownLatch just it uses the await and not countdown(), a thread specified to run when the countdown is over

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        //the Shoot thread is executed when all threads are awaited, but before they are released
        CyclicBarrier barrier = new CyclicBarrier(5, new Shoot());

        System.out.println("Race Starting... ");

        Auto ferrari = new Auto(barrier, "Ferrari");
        Auto bmw = new Auto(barrier, "BMW");
        Auto mercedes = new Auto(barrier, "Mercedes");
        Auto mclaren = new Auto(barrier, "Mc Laren");
        Auto redbull = new Auto(barrier, "Red Bull");

        System.out.println("Waiting for race to finish...");
        System.out.println("Race finished, all cars arrived");
    }

}

class Shoot extends Thread {
    @Override
    public void run() {
        System.out.println("Goooooooooooo!!!");
    }
}

class Auto extends Thread {
    CyclicBarrier cyclicBarrier;
    String model;

    public Auto(CyclicBarrier cyclicBarrier, String model) {
        this.cyclicBarrier = cyclicBarrier;
        this.model = model;

        this.start();
    }

    @Override
    public void run() {
        try {
            int millis = new Random().nextInt(2000);
            log("about to arrive... waiting " + millis + " ms");
            Thread.sleep(millis);
            log("arrived");
            this.cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void log(String s) {
        System.out.println(model + ": " + s);
    }




}
