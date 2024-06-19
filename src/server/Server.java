package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;

public class Server {

    private final int port;
    private int connections = 0;
    private int idCounter = 0;

    public Server(int port) {
        this.port = port;

    }

    public void run() {
        try (var serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(port));

            System.out.println("Server started");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected " + Calendar.getInstance().getTime());

                new ServerThread(socket).start();
                connections += 1;

                System.out.println("Active Threads count: " + Thread.activeCount());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerThread extends Thread {

        private final Socket socket;
        private int id = idCounter++;

        public ServerThread(Socket socket) {
            this.socket = socket;

        }

        @Override
        public void run() {
            try (var input = socket.getInputStream();
                    var reader = new BufferedReader(new InputStreamReader(input));) {

                String text;
                while ((text = reader.readLine()) != null) {
                    System.out.printf("Message ID[%d]: %s \n", id, text);
                }
                

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    connections -= 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
