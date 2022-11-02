package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
        var str = "All files were sent!";
        System.out.println("Server started!");
        try (var server = new ServerSocket(23456,
                50,
                InetAddress.getByName("127.0.0.1"))){
            var socket = server.accept();
            var output  = new DataOutputStream(socket.getOutputStream());
            var msg = new DataInputStream(socket.getInputStream())
                    .readUTF();
            output.writeUTF(str);
            System.out.println("Received: " + msg);
            System.out.println("Sent: " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}