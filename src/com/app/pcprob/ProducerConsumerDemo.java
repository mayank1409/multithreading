package com.app.pcprob;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerDemo {
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }

    private static void producer() throws InterruptedException {
        while (true) {
            Thread.sleep(100);
            int item = random.nextInt(100);
            System.out.println("Producer produced item: " + item);
            queue.add(item);
        }
    }

    private static void consumer() throws InterruptedException {
        while (true) {
            int rand = random.nextInt(10);
            if (rand == 0) {
                int item = queue.take();
                System.out.println("Item consumed: " + item + "; Queue size: " + queue.size());
            }
        }
    }
}
