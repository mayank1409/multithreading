package com.app.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class Shared {
    static int count = 0;
}
class Increment  {
    void incrementTest() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Semaphore semaphore = new Semaphore(1);
        IntStream.rangeClosed(1, 10000)
                .forEach(i -> executor.submit(() -> {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Shared.count++;
                        semaphore.release();
                }));
        executor.shutdown();
        try {
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("count = " + Shared.count);
    }
}
public class SemaphoreCountTest {
    public static void main(String[] args) {
        Increment increment = new Increment();
        increment.incrementTest();
    }
}
