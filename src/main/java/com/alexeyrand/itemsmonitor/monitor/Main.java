package com.alexeyrand.itemsmonitor.monitor;

public class Main {
    public static void startThread() {
        ThreadParser parser1 = new ThreadParser();
        ThreadParser parser2 = new ThreadParser();
        Thread th1 = new Thread(parser1);
        Thread th2 = new Thread(parser2);

        th1.start();
        th2.start();


    }
}
