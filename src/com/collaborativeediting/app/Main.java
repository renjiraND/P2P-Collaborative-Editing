package com.collaborativeediting.app;

import com.collaborativeediting.view.MainFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }

}