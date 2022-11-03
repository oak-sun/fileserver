package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);
        System.out.println("Client started!");
        try (var socket = new Socket(
                InetAddress.getByName("127.0.0.1"),
                23456);
             var output = new DataOutputStream(socket.getOutputStream())) {
            System.out.println("Enter action (" +
                    "1 - get a file, " +
                    "2 - create a file," +
                    " 3 - delete a file): ");
            var command = sc.nextLine();
            if (!command.equals("exit")) {
                System.out.println("Enter filename: ");
                var fileName = sc.nextLine();

                if (!command.equals("2")) {
                    output.writeUTF(command +
                            "==" + fileName);
                } else {
                    System.out.println("Enter file content: ");
                    output.writeUTF(command +
                            "==" +
                            fileName +
                            "==" +
                            sc.nextLine());
                }
                System.out.println("The request was sent.");
                var in = new DataInputStream(
                                    socket.getInputStream())
                                .readUTF();
                System.out.println(
                        command.equals("1") &&
                        !in.equals("the file was not found!") ?
                        "The content of the file is: " + in
                        :
                        "The response says that " + in);
            } else {
                System.out.println("The request was sent.");
                output.writeUTF(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}