package com.alexeyrand.itemsmonitor.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@AllArgsConstructor
public class StateThread {

    public boolean startFlag;
    public boolean stopFlag;
}
