package com.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ConcurrentProgramDemo {

    private static int x = 0;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                decrement();
            }
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("x = " + x);
    }

    private synchronized static void decrement() {
            for (int i = 0; i < 10000; i++) {
                x--;
            }
        }

    private synchronized static void increment() {
            for (int i = 0; i < 10000; i++) {
                x++;
            }
    }

}
