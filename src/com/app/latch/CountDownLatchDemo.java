package com.app.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable {

    private CountDownLatch latch;
    private int id;
    Task(CountDownLatch latch, int id) {
        this.latch = latch;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting task :" + id);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed task :" + id + " by " + Thread.currentThread().getName());
        latch.countDown();
    }
}

public class CountDownLatchDemo {

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(10);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i=1; i <= 10; i++) {
            executorService.submit(new Task(latch, i));
        }
        executorService.shutdown();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All tasks executed. Exiting ...");
    }

}