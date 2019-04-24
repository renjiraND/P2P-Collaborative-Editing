package com.collaborativeediting.app;

import java.util.ArrayList;
import java.util.ListIterator;

public class Controller {
    private int siteId;

    private CRDT crdt;
    private ArrayList<CRDT.Character> deleteBuffer = new ArrayList<>();

    public Controller() {
        this.siteId = 1;                // ini hrsnya nanti dpt dari connection
        this.crdt = new CRDT(1);
    }

    public CRDT getCRDT() {
        return crdt;
    }

    public void addDeleteBuffer(CRDT.Character c) {
        this.deleteBuffer.add(c);
    }

    public void updateDeleteBuffer(CRDT.Character targetChar) {
        boolean isUpdated = false;
        ListIterator litr = this.deleteBuffer.listIterator();
        while (litr.hasNext() || (!isUpdated)) {
            CRDT.Character crntChar = (CRDT.Character) litr.next();
            if (crntChar.getValue() == targetChar.getValue()
                && crntChar.getPosition() == targetChar.getPosition()
                && crntChar.getSiteId() == targetChar.getSiteId()
                && crntChar.getSiteCounter() > targetChar.getSiteCounter())
            {
                this.crdt.deleteChar(crntChar);
                this.deleteBuffer.remove(crntChar);
                isUpdated = true;
            }
        }
    }
}
