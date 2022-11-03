package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server started!");
        Path path = Path.of(System.getProperty("user.dir"),
                "src",
                "server",
                "data");
        System.out.println(path
                         .toFile()
                         .mkdirs());
        String[] request = {"", null};
        try (var server = new ServerSocket(23456)) {
            while (!request[0].equals("exit")) {
                var socket = server.accept();
                var output = new DataOutputStream(socket.getOutputStream());
                request = new DataInputStream(socket.getInputStream())
                        .readUTF()
                        .split("==");
                if (!request[0].equals("exit")) {
                    var file = Path.of(path + "/" + request[1]).toFile();
                    switch (request[0]) {
                        case "1" -> {
                            if (file.exists()) {
                                var sc = new Scanner(file);
                                var sb = new StringBuilder();
                                while (sc.hasNextLine())
                                    sb.append(sc.nextLine());
                                output.writeUTF(sb.toString());
                            } else {
                                output.writeUTF("the file was not found!");
                            }
                        }
                        case "2" -> {
                            if (file.createNewFile()) {
                                var writer = new FileWriter(file);
                                writer.write(request[2]);
                                writer.close();
                                output.writeUTF("file was created!");
                            } else {
                                output.writeUTF(
                                        "creating the file was forbidden!");
                            }
                        }
                        case "3" -> {
                            if (file.delete()) {
                                output.writeUTF(
                                        "the file was successfully deleted!");
                            } else {
                                output.writeUTF("the file was not found!");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}