package codes.dreaming;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    public void start(int port) {
        // Try with resources construct to ensure everything is properly closed
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server has started!");

            Socket clientSocket = serverSocket.accept();

            Random rand = new Random();
            int number = rand.nextInt(100) + 1;

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

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
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + port);
            System.out.println(e.getMessage());
        }
    }
}
