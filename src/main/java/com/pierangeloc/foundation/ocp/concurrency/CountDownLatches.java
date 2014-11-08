package com.pierangeloc.foundation.ocp.concurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by pierangeloc on 13-10-14.
 */
public class CountDownLatches {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);

        System.out.println("Race Starting... ");

        Car ferrari = new Car(latch, "Ferrari");
        Car bmw = new Car(latch, "BMW");
        Car mercedes = new Car(latch, "Mercedes");
        Car mclaren = new Car(latch, "Mc Laren");
        Car redbull = new Car(latch, "Red Bull");

        System.out.println("Waiting for race to finish...");
        latch.await();
        System.out.println("Race finished, all cars arrived");
    }

}

class Car extends Thread {
    CountDownLatch countDownLatch;
    String model;

    public Car(CountDownLatch latch, String model) {
        this.countDownLatch = latch;
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
            this.countDownLatch.countDown();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void log(String s) {
        System.out.println(model + ": " + s);
    }
}