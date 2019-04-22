package com.collaborativeediting.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private JFrame frame;
    private JPanel panelMain;
    private JTextArea textEditor;

    public MainFrame() {
        frame = new JFrame("P2P Collaborative Editor");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelMain = new JPanel(new GridLayout(3,1), true);

        /*** text editor label ***/
        JLabel labelTitle = new JLabel("Your Awesome Text Editor");
        labelTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        panelMain.add(labelTitle);

        /*** text editor ***/
        textEditor = new JTextArea(5, 50);
        textEditor.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        textEditor.setLineWrap(true);
        textEditor.setWrapStyleWord(true);

        JScrollPane editorScollPane = new JScrollPane(textEditor);
        editorScollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        editorScollPane.setPreferredSize(new Dimension(600, 200));
        panelMain.add(editorScollPane);

        /*** frame final setup ***/
        frame.add(panelMain);
        frame.pack();
        frame.setVisible(true);    }
}
