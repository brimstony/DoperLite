package com.brimstony.doper.dto;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tbrim on 9/8/2016.
 */
public class Sequence {
    private final AtomicInteger nextVal = new AtomicInteger(1);

    public Sequence(){}

    public int nextVal(){
        return nextVal.getAndIncrement();
    }

    public void resetSequence() {nextVal.set(1);}
}
