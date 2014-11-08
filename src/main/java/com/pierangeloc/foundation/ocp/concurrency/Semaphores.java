package com.pierangeloc.foundation.ocp.concurrency;

import java.util.concurrent.Semaphore;

/**
 * Created by pierangeloc on 13-10-14.
 */
public class Semaphores {
    public static void main(String[] args) {
        //2 resources to be shared
        Semaphore machines = new Semaphore(2);
        new Person(machines, "Johnny");
        new Person(machines, "Archibald");
        new Person(machines, "Frankie");
        new Person(machines, "Angelo");
        new Person(machines, "Turiddu");
    }
}

class Person extends Thread {
    private Semaphore machines;

    Person(Semaphore machines, String name) {
        this.machines = machines;
        this.setName(name);

        //start thread
        this.start();
    }

    @Override
    public void run() {
        try {
            log(" waiting to access machine");
            //acquiring resource
            this.machines.acquire();
            log(" ready to use machine... withdrawing cash...");
            Thread.sleep(1000);
            log(" done using machine");
            //releasing resources
            this.machines.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void log(String s) {
        System.out.println(getName() + s);
    }
}
