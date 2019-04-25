package com.collaborativeediting.app;

import com.collaborativeediting.app.socket.Messenger;
import com.collaborativeediting.app.socket.Server;
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
    private ArrayList<CRDT.Character> deletionBuffer = new ArrayList<>();
    private Messenger messenger;
    private String commandBuffer;

    public Controller(Messenger messenger) {
        this.siteId = 1;                // ini hrsnya nanti dpt dari connection
        this.crdt = new CRDT(1);
        this.messenger = messenger;
    }

    public CRDT getCRDT() {
        return crdt;
    }

    public void insertRemote(CRDT.Character character) {

    }

    public void addDeletionBuffer(CRDT.Character c) {
        this.deletionBuffer.add(c);
    }

    public void updateDeletionBuffer() {
        if (!this.deletionBuffer.isEmpty()) {
            for (CRDT.Character c : this.deletionBuffer) {
                boolean found = false;
                ListIterator litr = this.crdt.getCharacters().listIterator();
                while (litr.hasNext() && !found) {
                    CRDT.Character currentChar = (CRDT.Character) litr.next();
                    if (c.isEqualTo(currentChar)) {
                        int crntCharPos = this.crdt.deleteChar(currentChar);
                        this.deletionBuffer.remove(c);
                        this.frame.removeChar(crntCharPos);
                        found = true;
                    }
                }
            }
        }
    }

    public void run() {
        SwingUtilities.invokeLater(() -> this.frame = new GUIFrame(new KeyEditorListener()));
        Thread t = new CommandListener(this.messenger,this);
        t.start();
    }

    public void setCommandBuffer(String commandBuffer) {
        this.commandBuffer = commandBuffer;
    }

    public String getCommandBuffer() {
        return commandBuffer;
    }

    /*** CLASS ***/
    public class KeyEditorListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:          // left arrow
                    if (frame.getCursorIdx() > 0) {
                        frame.setCursorIdx(frame.getCursorIdx() - 1);
                    }
                    break;
                case KeyEvent.VK_RIGHT:         // right arrow
                    if (frame.getCursorIdx() < frame.getCharCount()) {
                        frame.setCursorIdx(frame.getCursorIdx() + 1);
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
                    if (frame.getCursorIdx() > 0) {
                        frame.setCharCount(frame.getCharCount() - 1);
                        frame.setCursorIdx(frame.getCursorIdx() - 1);
                        CRDT.Character ch = crdt.getCharacters().get(frame.getCursorIdx());
                        messenger.broadcast(crdt.encode(crdt.new Message(ch, 2)));
                        int cPosition = crdt.deleteChar(ch);
                    }
                    break;
                case KeyEvent.VK_DELETE:        // delete
                    if (frame.getCursorIdx() < frame.getCharCount()) {
                        frame.setCharCount(frame.getCharCount() - 1);
                        CRDT.Character ch = crdt.getCharacters().get(frame.getCursorIdx());
                        messenger.broadcast(crdt.encode(crdt.new Message(ch, 2)));
                        int cPosition = crdt.deleteChar(ch);
                    }
                    break;
                default:
                    if (isValidChar(keyCode)) { // alphabet, digit, space
                        CRDT.Character ch = crdt.new Character(e.getKeyChar(), crdt.generatePos(frame.getCursorIdx()));
                        messenger.broadcast(crdt.encode(crdt.new Message(ch, 1)));
                        int cPosition = crdt.insertChar(ch);
                        frame.setCharCount(frame.getCharCount() + 1);
                        frame.setCursorIdx(frame.getCursorIdx() + 1);
                    }
            }
            frame.updateFooter();
        }

        @Override
        public void keyReleased(KeyEvent e) { }

        private boolean isValidChar(int keycode) {
            return (48 <= keycode) && (keycode <= 57)   // digit
                || (65 <= keycode) && (keycode <= 90)   // alphabet
                || (keycode == 32);                     // space
        }
    }

}

class CommandListener extends Thread {
    final Messenger messenger;
    final Controller controller;

    public CommandListener(Messenger messenger, Controller controller) {
        this.messenger = messenger;
        this.controller = controller;
    }

    @Override
    public void run() {
        Server s = (Server)messenger.getMyServer();
        while (true) {
            if (s.getCommand() != null) {
                CRDT.Message msg = controller.getCRDT().decode(s.getCommand());
                CRDT.Character ch = msg.getCharacter();
                if (msg.getType() == 1) {
                    controller.getCRDT().insertChar(ch);
                } else if (msg.getType() == 2) {
                    controller.addDeletionBuffer(ch);
                }
                controller.updateDeletionBuffer();
            }
        }
    }
}
