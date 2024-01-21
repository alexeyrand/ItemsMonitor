package com.alexeyrand.itemsmonitor.monitor;

public interface Parser {
    void start();
    void stop();
    void setup();
    void openBrowser(String URL);
    void update();
}
