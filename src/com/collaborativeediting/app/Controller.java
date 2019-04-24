package com.collaborativeediting.app;

public class Controller {
    private CRDT crdt;
    private int siteId;

    public Controller() {
        this.siteId = 1;                // ini hrsnya nanti dpt dari connection
        this.crdt = new CRDT(1);
    }

    public CRDT getCRDT() {
        return crdt;
    }
}
