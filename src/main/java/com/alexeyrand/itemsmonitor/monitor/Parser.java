package com.alexeyrand.itemsmonitor.monitor;

public interface Parser {
    void start();
    void stop() throws InterruptedException;
    void setup();
    void openBrowser(String URL);
    void update() throws InterruptedException;
    void sleep(long sec) throws InterruptedException;
}
