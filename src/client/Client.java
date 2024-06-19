package client;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("server", 3033);
                var console = new Scanner(System.in);
                var writer = new PrintWriter(socket.getOutputStream())) {

            System.out.println("Client connected\n Type \"exit\" to stop the server");
            String text;
            while (!(text = console.nextLine()).equals("exit")) {
                writer.println(text);
                writer.flush();
            }
            

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
