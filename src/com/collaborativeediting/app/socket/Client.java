package com.collaborativeediting.app.socket;

// Java implementation for a client
// Save file as Client.java

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

// Client class
public class Client extends Thread {

    private Integer portToConnect;
    private Integer myPort;
    private volatile String sendBuffer;

    public Client(Integer portToConnect, Integer myPort) {
        this.portToConnect = portToConnect;
        this.myPort = myPort;
        this.sendBuffer = "";
    }

    public Integer getPortToConnect() {
        return portToConnect;
    }

    public void write(String buffer) {
        this.sendBuffer = buffer;
    }

    public void emptyBuffer() {
        this.sendBuffer = "";
    }

    public void run() {
        try {
            System.out.println("Connecting ...");
            Scanner scn = new Scanner(System.in);

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket s = new Socket(ip, this.getPortToConnect());

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            while (true) {
                if (this.myPort != null) {
                    dos.writeUTF("port_number");
                    dos.writeUTF(this.myPort.toString());
                }
                myPort = null;
                if (!sendBuffer.equals("")) {
                    dos.writeUTF("message_buffer");
                    dos.writeUTF(sendBuffer);
                    if (sendBuffer.equals("Exit")) {
                        System.out.println("Closing this connection : " + s);
                        s.close();
                        System.out.println("Connection closed");
                        break;
                    }
                    this.emptyBuffer();
                }

//                Thread t = new Listener(s,dis);
//                t.start();
            }

            // closing resources
            dos.writeUTF("Exit");
            scn.close();
            dis.close();
            dos.close();
        } catch (Exception e) {
        }
    }

}
//
//// ClientHandler class
//class Listener extends Thread
//{
//    final DataInputStream dis;
//    final Socket s;
//
//    // Constructor
//    public Listener(Socket s, DataInputStream dis)
//    {
//        this.s = s;
//        this.dis = dis;
//    }
//
//    @Override
//    public void run()
//    {
//        String rcv;
//        while (true)
//        {
//            try {
//                rcv = dis.readUTF();
//
//                System.out.println("received:");
//                System.out.println(rcv);
//
//                if(rcv.equals("Exit"))
//                {
//                    this.s.close();
//                    System.out.println("Connection closed");
//                    break;
//                }
//
//            } catch (IOException e) {
//                try {
//                    this.s.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                System.out.println("Connection closed");
//                break;
//            }
//        }
//
//        try
//        {
//            // closing resources
//            this.dis.close();
//
//        }catch(IOException e){
//            System.out.println("Connection closed");
//        }
//    }
//}
//
