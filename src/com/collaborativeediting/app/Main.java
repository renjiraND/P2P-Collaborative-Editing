package com.collaborativeediting.app;

import com.collaborativeediting.app.socket.Messenger;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public ArrayList<Integer> portsUsed;

    public static void main(String[] args) {

//        SwingUtilities.invokeLater(() -> new MainFrame());
//
        Messenger messenger = new Messenger();
        messenger.initialize();
        Thread menu = new Menu(messenger);
        menu.start();

    }

}

class Menu extends Thread{

    private Messenger messenger;

    public Menu(Messenger messenger){
        this.messenger = messenger;
    }

    public void run() {
        Scanner scn = new Scanner(System.in);
        while (true){
            System.out.println("Option\n<1. Connect to client>\n<2. List client(s)>\n<3. Send message>");
            Integer opt = Integer.parseInt(scn.nextLine());
            switch (opt){
                case 1:
                    this.messenger.addClient();
                    break;
                case 2:
                    this.messenger.listClient();
                    break;
                case 3:{
                    System.out.print("Input Message to be sent: ");
                    this.messenger.broadcast(scn.nextLine());
                    break;
                }
            }
        }
    }
}