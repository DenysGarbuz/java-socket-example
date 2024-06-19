package server;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(3033);
        server.run();

    }
}
