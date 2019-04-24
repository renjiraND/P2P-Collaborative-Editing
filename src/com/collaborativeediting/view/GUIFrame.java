package com.collaborativeediting.view;

import com.collaborativeediting.app.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIFrame extends JFrame {
    private JPanel panel;
    private JTextArea textAreaEditor;
    private JLabel labelCursorIdx;

    private int cursorIdx;
    private int charCount;

    public GUIFrame(Controller.KeyEditorListener keyEditorListener) {
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
        textAreaEditor.addKeyListener(keyEditorListener);
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

    public int getCursorIdx() {
        return cursorIdx;
    }

    public void setCursorIdx(int cursorIdx) {
        this.cursorIdx = cursorIdx;
    }

    public int getCharCount() {
        return charCount;
    }

    public void setCharCount(int charCount) {
        this.charCount = charCount;
    }

    public void setCursorPosition() {
        textAreaEditor.setCaretPosition(this.cursorIdx);
    }

    public void updateFooter() {
        labelCursorIdx.setText("Cursor position: " + cursorIdx + " | Character count: " + charCount);
    }
}
