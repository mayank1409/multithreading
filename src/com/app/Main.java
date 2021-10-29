package com.app;

import java.util.Scanner;

class Processor implements Runnable{
    private volatile boolean running = true; // prevents caching of running
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Executing ");
        }
    }

    public void shutdown() {
        running = false;
    }
}

public class Main {

    public static void main(String[] args) {

        Processor proc1 = new Processor();
        Thread thread = new Thread(proc1);
        thread.start();

        Scanner scan = new Scanner(System.in);
        System.out.println("Press return to stop");
        scan.nextLine();

        proc1.shutdown();
    }
}
