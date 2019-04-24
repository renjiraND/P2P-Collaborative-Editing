package com.collaborativeediting.app;

import com.collaborativeediting.view.GUIFrame;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ListIterator;

public class Controller {
    private int siteId;

    private GUIFrame frame;
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

    public void run() {
        SwingUtilities.invokeLater(() -> this.frame = new GUIFrame(new KeyEditorListener()));
    }

    /*** CLASS ***/
    public class KeyEditorListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:          // left arrow
                    if (frame.getCursorIdx() > 0) {
                        frame.setCursorIdx(frame.getCursorIdx()-1);
                    }
                    break;
                case KeyEvent.VK_RIGHT:         // right arrow
                    if (frame.getCursorIdx() < frame.getCharCount()) {
                        frame.setCursorIdx(frame.getCursorIdx()+1);
                    }
                    break;
                case KeyEvent.VK_UP:            // up arrow
                    frame.setCursorIdx(0);
                    frame.setCursorPosition();
                    break;
                case KeyEvent.VK_DOWN:          // down arrow
                    frame.setCursorIdx(frame.getCharCount());
                    frame.setCursorPosition();
                    break;
                case KeyEvent.VK_BACK_SPACE:    // backspace
                    if (frame.getCursorIdx() > 0)
                    {
                        frame.setCharCount(frame.getCharCount()-1);
                        frame.setCursorIdx(frame.getCursorIdx()-1);
                        CRDT.Character ch = crdt.getCharacters().get(frame.getCursorIdx());
                        crdt.deleteChar(ch);
                    }
                    break;
                case KeyEvent.VK_DELETE:        // delete
                    if (frame.getCursorIdx() < frame.getCharCount())
                    {
                        frame.setCharCount(frame.getCharCount()-1);
                        CRDT.Character ch = crdt.getCharacters().get(frame.getCursorIdx());
                        crdt.deleteChar(ch);
                    }
                    break;
                default:
                    if (isValidChar(keyCode)) { // alphabet, digit, space
                        CRDT.Character ch = crdt.new Character(e.getKeyChar(), crdt.generatePos(frame.getCursorIdx()));
                        crdt.insertChar(ch, frame.getCursorIdx());
                        frame.setCharCount(frame.getCharCount()+1);
                        frame.setCursorIdx(frame.getCursorIdx()+1);
                    }
            }
            frame.updateFooter();
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        private boolean isValidChar(int keycode) {
            return (48 <= keycode) && (keycode <= 57)   // digit
                || (65 <= keycode) && (keycode <= 90)   // alphabet
                || (keycode == 32);                     // space
        }
    }

}
