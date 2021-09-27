package com.sid.threading.ch2;

import java.util.ArrayList;
import java.util.List;

public class PubSub {
    private int nextInitial = 0;
    
    private int initialCapacity = 10;
    
    private List<Integer> list = new ArrayList<>(initialCapacity);
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello Java World!");
        PubSub pubSub = new PubSub();
        
        // Functional interfaces. The Runnable interface—like the Callable<T> interface, the Comparator<T> interface, and 
        // a whole host of other interfaces already defined within Java—is what Java 8 calls a functional interface: it 
        // is an interface that requires exactly one method to be implemented in order to satisfy the requirements of the
        // interface. This is how the syntax achieves its brevity, because there is no ambiguity around which method
        // of the interface the lambda is trying to define.

        // The designers of Java 8 have chosen to give us an annotation, @FunctionalInterface, to serve as a 
        // documentation hint that an interface is designed to be used with lambdas, but the compiler does not require 
        // this—it determines “functional interfaceness” from the structure of the interface, not from the annotation.
        // https://github.com/ganfanlaowang/Java8-Source-Code/blob/master/src/main/jdk8/java/lang/Runnable.java
        Thread consumer = new Thread(()->pubSub.subscriber());
        Thread producer = new Thread(()->pubSub.publisher());
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
    }

    
    public void subscriber() {

        for (int i = 0; i < initialCapacity; i++) {
            synchronized (this) {
                try {
                    while (list.size() == 0) {
                        this.wait();
                    }
                    System.out.println(Thread.currentThread().getName() + " " + nextInitial + ". Message subscribed..." + list.get(0));
                    list.remove(0);
                    this.notifyAll();
                } catch (InterruptedException e) {
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void publisher() {

        for (int i = 0; i < initialCapacity; i++) {
            synchronized (this) {
                try {
                    while (list.size() == initialCapacity) {
                        this.wait();
                    }
                    list.add(nextInitial);
                    System.out.println(Thread.currentThread().getName() + " " + nextInitial + ". Message published..." + nextInitial);
                    nextInitial++;
                    this.notifyAll();
                } catch (InterruptedException e) {
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
