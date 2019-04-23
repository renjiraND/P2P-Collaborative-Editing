package com.collaborativeediting.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame {
    private JFrame frame;
    private JPanel panelMain;
    private JTextArea textAreaEditor;
    private JLabel labelCursorIdx;

    private int cursorIdx;
    private int charCount;

    public MainFrame() {
        frame = new JFrame("P2P Collaborative Editor");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelMain = new JPanel(new GridBagLayout(), true);
        GridBagConstraints cst = new GridBagConstraints();

        /*** title label ***/
        JLabel labelTitle = new JLabel("Your Awesome Text Editor");
        labelTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        cst.fill = GridBagConstraints.CENTER;
        cst.gridx = 0;
        cst.gridy = 0;
        panelMain.add(labelTitle, cst);

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

        JScrollPane editorScollPane = new JScrollPane(textAreaEditor);
        editorScollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        editorScollPane.setPreferredSize(new Dimension(600, 200));
        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridx = 0;
        cst.gridy = 1;
        panelMain.add(editorScollPane, cst);

        /*** character index label ***/
        cursorIdx = 0; charCount = 0;
        labelCursorIdx = new JLabel("Cursor position: " + cursorIdx + " | Character count: " + charCount);
        labelCursorIdx.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        cst.fill = GridBagConstraints.CENTER;
        cst.gridx = 0;
        cst.gridy = 2;
        panelMain.add(labelCursorIdx, cst);

        /*** frame final setup ***/
        frame.add(panelMain);
        frame.pack();
        frame.setVisible(true);
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
                case KeyEvent.VK_LEFT:
                    cursorIdx = (cursorIdx > 0) ? (cursorIdx-1) : 0;
                    break;
                case KeyEvent.VK_RIGHT:
                    cursorIdx = (cursorIdx < charCount) ? (cursorIdx+1) : cursorIdx;
                    break;
                case KeyEvent.VK_UP:
                    cursorIdx = 0;
                    textAreaEditor.setCaretPosition(cursorIdx);
                case KeyEvent.VK_BACK_SPACE:
                    charCount = (charCount > 0) ? (charCount-1) : 0;
                    cursorIdx = (cursorIdx > 0) ? (cursorIdx-1) : 0;
                    break;
                case KeyEvent.VK_DELETE:
                default:
                    if (isValidChar(keyCode)) {
                        char inputChar = e.getKeyChar();
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
                || (65 <= keycode) && (keycode <= 90);  // alphabet
        }
    }
}
