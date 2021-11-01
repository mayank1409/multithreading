package com.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;


public class ReentrantTestDemo {
    public static void main(String[] args) {
        Increment test = new Increment();
        test.incrementTest();
    }
}

class Increment {
    int count;
    void incrementTest() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Lock lock = new ReentrantLock();
        IntStream.rangeClosed(1, 10000)
                .forEach(i -> executor.submit(() -> {
                    lock.lock();
                    try {
                        increment();
                    } finally {
                        lock.unlock();
                    }
                }));
        executor.shutdown();
        try {
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("count " + count);
    }
    void increment() {
        count++;
    }
}

