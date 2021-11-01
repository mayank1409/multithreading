package com.app.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Worker {

    private Random random = new Random();
    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    Object lock1 = new Object();
    Object lock2 = new Object();

    private void stageOne() {
        synchronized(lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }

    private void stageTwo() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }
    }
    public void process() {
        for (int i = 1; i <= 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public List<Integer> getList1() {
        return list1;
    }

    public List<Integer> getList2() {
        return list2;
    }
}

public class SyncBlockDemo {
    public static void main(String[] args) {

        System.out.println("Starting ...");

        Worker worker = new Worker();
        long start = System.currentTimeMillis();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                worker.process();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                worker.process();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("Time taken : " + (end - start));
        System.out.println("List1 size " + worker.getList1().size() +"\nList2 size :" + worker.getList2().size());
    }
}
