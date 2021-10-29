package com.app.pcprob;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class Processor {
    private LinkedList<Integer> list = new LinkedList<>();
    private final int LIMIT = 10;
    private Random random = new Random();

    public void produce() throws InterruptedException {
        synchronized (this) {
            while (true) {
                while (list.size() == LIMIT) {
                    wait();
                }
                int value = random.nextInt(100);
                System.out.println("Producer produced :" + value + "; list size :" + list.size());
                list.add(value);
                notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (this) {
            while (true) {

                int value = list.removeFirst();
                System.out.println("Item consumed :" + value + "; List size =" + list.size());
                notify();

                while (list.size() == 0) {
                    wait();
                }

                Thread.sleep(1000);
            }

        }
    }
}

public class WaitAndNotifyDemo {

    public static void main(String[] args) {
        Processor processor = new Processor();
       Thread t1 = new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   processor.produce();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
