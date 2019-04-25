package com.collaborativeediting.app.socket;

import java.util.ArrayList;
import java.util.Scanner;

//kelas ini memiliki fungsi untuk melakukan broadcast objek operasi dan menerima broadcast objek operasi
public class Messenger {

    private ArrayList<Thread> ClientList;
    private Thread myServer;
    private Thread listener;
    private String buffer;
    private ArrayList<Integer> ClientListInteger;

    public Messenger() {
        this.ClientList = new ArrayList<>();
        this.myServer = new Server();
        this.listener = new Listener(this,(Server) this.myServer);
    }

    public Thread getMyServer() {
        return myServer;
    }

    public void initialize() {
        this.listener.start();
        this.myServer.start();
    }

    public void broadcast(String message){
        for (Thread client: this.ClientList) {
            Client c = (Client) client;
            c.write(message);
        }
    }

    public void autoAddClient(){
        Server server = (Server) myServer;
        if (server.getIncomingClient() != null){
            Thread client = new Client(server.getIncomingClient(),null);
            client.start();
            this.ClientList.add(client);
            server.clearIncomingClient();
        }
    }

    public void checkIncomingPort(){

    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public void addClient() {
        Scanner scn = new Scanner(System.in);
        System.out.print("Please input PortNumber for new client: ");
        Integer portNumber = Integer.parseInt(scn.nextLine());
        Server server = (Server) this.myServer;

        Thread client = new Client(portNumber, server.getServerPort());
        client.start();
        this.ClientList.add(client);
    }

    public void listClient() {
        for (Thread client: this.ClientList) {
            Client c = (Client) client;
            System.out.println(c.getPortToConnect());
        }
    }

    public void removeClient(Client client) {
        this.ClientList.remove(this.ClientList.indexOf(client));
    }

}

class Listener extends Thread
{
    final Server server;
    final Messenger messenger;

    // Constructor
    public Listener(Messenger messenger, Server server)
    {
        this.messenger = messenger;
        this.server = server;
    }

    @Override
    public void run()
    {
        while (true)
        {
            if(! server.getReceiveBuffer().equals("")){
                this.messenger.setBuffer(server.getReceiveBuffer());
            }
            this.messenger.autoAddClient();
        }
    }
}


