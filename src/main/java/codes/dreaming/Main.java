package codes.dreaming;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose mode: ");
        System.out.println("1 - Server");
        System.out.println("2 - Client");
        int mode = scanner.nextInt();
        System.out.println("Enter port:");
        int port = scanner.nextInt();

        switch (mode) {
            case 1 -> {
                // Start the server
                Server server = new Server();
                server.start(port);
            }
            case 2 -> {
                // Start the client
                System.out.println("Enter server IP:");
                String ip = scanner.next();
                Client client = new Client();
                client.start(ip, port);
            }
            default -> System.out.println("Invalid mode selected, closing.");
        }

        scanner.close();
    }
}
