package com.collaborativeediting.app;

import java.util.ArrayList;

public class Version {

    private long siteId;
    private int counter;
    private ArrayList<Integer> exceptions;


    public Version(long siteId) {
        this.siteId = siteId;
        this.counter = 0;
        this.exceptions = new ArrayList<>();
    }

    public void updateVersion(Version version){
        final int incomingCounter = version.counter;
        if (incomingCounter <= this.counter) {
        final int index = this.exceptions.indexOf(incomingCounter);
        if(index >= 0){
            this.exceptions.remove(index);
        }
        } else if (incomingCounter == this.counter + 1) {
            this.counter = this.counter + 1;
        } else {
            for (int i = this.counter + 1; i < incomingCounter; i++) {
                this.exceptions.add(i);
            }
            this.counter = incomingCounter;
        }
    }

    public void increment() {
        this.counter++;
    }

    public long getSiteId() {
        return siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public int getCounter() {
        return this.counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public ArrayList<Integer> getExceptions() {
        return this.exceptions;
    }
}


