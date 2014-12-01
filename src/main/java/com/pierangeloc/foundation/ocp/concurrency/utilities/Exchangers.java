package com.pierangeloc.foundation.ocp.concurrency.utilities;

import java.util.Date;
import java.util.concurrent.Exchanger;

/**
 * Created by pierangeloc on 13-10-14.
 */
public class Exchangers {



    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> comms = new Exchanger<>();

        System.out.println("Beginning communications...");
        Commander commander = new Commander(comms);
        Soldier soldier = new Soldier(comms);

        soldier.join();
        System.out.println("End of comms");
    }


}


class Soldier extends Thread {
    Exchanger<String> exchanger;

    Soldier(Exchanger<String> exchanger) {
        this.exchanger = exchanger;

        this.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            String commanderResponse = exchanger.exchange("What do I have to do?");
            System.out.println(new Date().getTime() / 1000 + "Soldier: Commander said: " + commanderResponse);

            commanderResponse = exchanger.exchange("I am a bit scared, any alternative?");
            System.out.println(new Date().getTime() / 1000 + "Soldier: Commander said: " + commanderResponse);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Commander extends Thread {
    Exchanger<String> exchanger;

    Commander(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        this.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            String soldierResponse = exchanger.exchange("Shoot'em boy!");
            System.out.println(new Date().getTime() / 1000 + "Commander: Soldier said: " + soldierResponse);
            soldierResponse = exchanger.exchange("Just Pray...");
            System.out.println(new Date().getTime() / 1000 + "Commander: Soldier said: " + soldierResponse);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}