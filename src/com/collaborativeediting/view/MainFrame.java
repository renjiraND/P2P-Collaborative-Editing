package com.collaborativeediting.view;

import com.collaborativeediting.app.CRDT;
import com.collaborativeediting.app.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private JPanel panel;
    private JTextArea textAreaEditor;
    private JLabel labelCursorIdx;

    private int cursorIdx;
    private int charCount;

    private Controller controller = new Controller();

    public MainFrame() {
        super();
        
        this.setTitle("P2P Collaborative Editor");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout(), true);
        GridBagConstraints cst = new GridBagConstraints();

        /*** title label ***/
        JLabel labelTitle = new JLabel("Your Awesome Text Editor");
        labelTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        cst.fill = GridBagConstraints.CENTER;
        cst.gridx = 0;
        cst.gridy = 0;
        panel.add(labelTitle, cst);

        /*** text editor ***/
        textAreaEditor = new JTextArea(5, 50);
        textAreaEditor.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        textAreaEditor.setLineWrap(true);
        textAreaEditor.setFocusable(true);
        textAreaEditor.addKeyListener(new KeyEditorListener());
        textAreaEditor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textAreaEditor.setCaretPosition(charCount);
            }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });

        JScrollPane editorScrollPane = new JScrollPane(textAreaEditor);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        editorScrollPane.setPreferredSize(new Dimension(600, 200));
        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridx = 0;
        cst.gridy = 1;
        panel.add(editorScrollPane, cst);

        /*** character index label ***/
        cursorIdx = 0; charCount = 0;
        labelCursorIdx = new JLabel("Cursor position: " + cursorIdx + " | Character count: " + charCount);
        labelCursorIdx.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        cst.fill = GridBagConstraints.CENTER;
        cst.gridx = 0;
        cst.gridy = 2;
        panel.add(labelCursorIdx, cst);

        /*** frame final setup ***/
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

    private void updateFooter() {
        labelCursorIdx.setText("Cursor position: " + cursorIdx + " | Character count: " + charCount);
    }

    private class KeyEditorListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:          // left arrow
                    cursorIdx = (cursorIdx > 0) ? (cursorIdx-1) : 0;
                    break;
                case KeyEvent.VK_RIGHT:         // right arrow
                    cursorIdx = (cursorIdx < charCount) ? (cursorIdx+1) : cursorIdx;
                    break;
                case KeyEvent.VK_UP:            // up arrow
                    cursorIdx = 0;
                    textAreaEditor.setCaretPosition(cursorIdx);
                    break;
                case KeyEvent.VK_DOWN:          // down arrow
                    cursorIdx = charCount;
                    textAreaEditor.setCaretPosition(cursorIdx);
                    break;
                case KeyEvent.VK_BACK_SPACE:    // backspace
                    if (cursorIdx > 0) {
                        charCount--;
                        cursorIdx--;
                        CRDT.Character ch = controller.getCRDT().getCharacters().get(cursorIdx);
                        controller.getCRDT().deleteChar(ch);
//                        controller.getCRDT().delete(cursorIdx);
                    }
                    break;
                case KeyEvent.VK_DELETE:        // delete
                    if (cursorIdx < charCount) {
                        charCount--;
                        CRDT.Character ch = controller.getCRDT().getCharacters().get(cursorIdx);
                        controller.getCRDT().deleteChar(ch);
//                        controller.getCRDT().delete(cursorIdx);
                    }
                    break;
                default:
                    if (isValidChar(keyCode)) { // alphabet, digit, space
//                        controller.getCRDT().insert(e.getKeyChar(), cursorIdx);
                        CRDT.Character ch = controller.getCRDT().new Character(e.getKeyChar(), controller.getCRDT().generatePos(cursorIdx));
                        controller.getCRDT().insertChar(ch, cursorIdx);
                        charCount++;
                        cursorIdx++;
                    }
            }
            updateFooter();
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
