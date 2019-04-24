package com.collaborativeediting.app.socket;

// Java implementation of Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

// Server class
public class Server extends Thread
{

    private Integer serverPort;
    private String receiveBuffer;
    private volatile Integer incomingClient;

    public Server() {
        this.receiveBuffer = "";
    }

    public Integer getServerPort() {
        return serverPort;
    }

    @Override
    public void run()
    {
        // server is listening on available port
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(0);
            this.setServerPort(ss.getLocalPort());
            System.out.println("Listening on port: " + this.serverPort);
        } catch (IOException e) {
        }

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                System.out.println("Waiting for Client...");

                try {
                    s = ss.accept();
                } catch (IOException e){
                }

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());

                System.out.println("Assigning new thread for this client");

//                Integer client = null;
//                try {
//                    System.out.print("before");
//                    client = Integer.parseInt(dis.readUTF());
//                    System.out.println("after");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if(client != -1){
//                    this.setIncomingClient(client);
//                }

                // create a new thread object
                Thread tclient = new ClientHandler(s, dis, this);

                // Invoking the start() method
                tclient.start();

            }
            catch (Exception e){
                try {
                    s.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public void setReceiveBuffer(String receiveBuffer) {
        this.receiveBuffer = receiveBuffer;
    }

    public String getReceiveBuffer() {
        return receiveBuffer;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public void setIncomingClient(Integer incomingClient) {
        this.incomingClient = incomingClient;
    }

    public Integer getIncomingClient() {
        return incomingClient;
    }

    public void clearIncomingClient() {
        this.incomingClient = null;
    }
}

// ClientHandler class
class ClientHandler extends Thread
{
    final DataInputStream dis;
    final Socket s;
    final Server myServer;

    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, Server server)
    {
        this.s = s;
        this.dis = dis;
        this.myServer = server;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try {
                // receive the answer from client
                myServer.setReceiveBuffer(dis.readUTF());
                System.out.println(myServer.getReceiveBuffer());

                if(myServer.getReceiveBuffer().equals("Exit"))
                {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }else if(myServer.getReceiveBuffer().equals("message_buffer")){
                    myServer.setReceiveBuffer(dis.readUTF());
                    System.out.println(myServer.getReceiveBuffer());
                }else if(myServer.getReceiveBuffer().equals("port_number")){
                    Integer port = Integer.parseInt(dis.readUTF());
                    this.myServer.setIncomingClient(port);
                }

            } catch (IOException e) {
            }
        }

        try
        {
            // closing resources
            this.dis.close();

        }catch(IOException e){
        }
    }
}
