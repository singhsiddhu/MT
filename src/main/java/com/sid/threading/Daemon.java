package com.sid.threading;

public class Daemon extends Thread {

    public Daemon(String name) {
        super(name);
    }

    @Override
    public void run() {
        if (Thread.currentThread().isDaemon()) {
            System.out.println(getName() + " is Daemon thread");
        } else {
            System.out.println(getName() + " is User thread");
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello Java World!");
        Daemon thr1 = new Daemon("thr1");
        Daemon thr2 = new Daemon("thr2");
        Daemon thr3 = new Daemon("thr3");
        thr1.setDaemon(true);
        thr1.start();
        thr2.start();
        thr3.setDaemon(true);
        thr3.start();
    }
}
