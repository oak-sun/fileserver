package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        var msg = "Give me everything you have!";
        System.out.println("Client started!");
        try {
            var socket = new Socket(InetAddress.getByName("127.0.0.1"), 23456);
            new DataOutputStream(socket.getOutputStream())
                    .writeUTF(msg);
            System.out.println("Sent: " + msg);
            System.out.println("Received: " +
                    new DataInputStream(socket.getInputStream())
                            .readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
