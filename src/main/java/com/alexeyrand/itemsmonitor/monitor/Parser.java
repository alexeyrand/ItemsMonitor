package com.alexeyrand.itemsmonitor.monitor;

public interface Parser {
    void start() throws InterruptedException;
    void setup();
    void openBrowser(String URL);
    void update();
}
