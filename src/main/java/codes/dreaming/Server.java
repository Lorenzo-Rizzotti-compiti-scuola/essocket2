package codes.dreaming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server has started!");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + port);
            System.out.println(e.getMessage());
        }
    }

    private record ClientHandler(Socket socket) implements Runnable {

        @Override
        public void run() {
            try {
                Random rand = new Random();
                int number = rand.nextInt(100) + 1;

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    int guess = Integer.parseInt(inputLine);
                    if (guess < number) {
                        out.println(1);
                    } else if (guess > number) {
                        out.println(2);
                    } else {
                        out.println(3);
                        break;
                    }
                }

                socket.close();
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen");
                System.out.println(e.getMessage());
            }
        }
    }
}
