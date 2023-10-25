package codes.dreaming;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    // List to keep all active clients
    private CopyOnWriteArrayList<ClientHandler> clients;

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server has started!");

            clients = new CopyOnWriteArrayList<>();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + port);
            System.out.println(e.getMessage());
        }
    }

    // Function to broadcast a message to all clients
    private void broadcast(Integer message, ClientHandler notToSend) {
        for (ClientHandler client : clients) {
            if (client != notToSend) {
                client.sendMessage(message);
            }
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        // Function to send a message to the client
        public void sendMessage(Integer message) {
            this.out.println(message);
        }

        @Override
        public void run() {
            try {
                Random rand = new Random();
                int number = rand.nextInt(100) + 1;

                out = new PrintWriter(socket.getOutputStream(), true);
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
                        // Broadcast to all clients that the current client has won
                        broadcast(4, this);
                        break;
                    }
                }

                // Remove the client from the active clients list
                clients.remove(this);
                socket.close();
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen");
                System.out.println(e.getMessage());
            }
        }
    }
}
