package codes.dreaming;

import java.io.*;
import java.net.*;

public class Client {
    public void start(String ip, int port) {
        // Try with resources construct to ensure everything is properly closed
        try (Socket echoSocket = new Socket(ip, port);
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            System.out.print("Inserisci il numero: ");
            for (int i = 1; (userInput = stdIn.readLine()) != null; i++) {
                out.println(userInput);
                int serverResponse = Integer.parseInt(in.readLine());

                if (serverResponse == 1)
                    System.out.println("Numero è troppo piccolo");
                else if (serverResponse == 2)
                    System.out.println("Numero è troppo grande");
                else {
                    System.out.println("HAI INDOVINATO, IN " + i + " TENTATIVI!");
                    break;
                }
                System.out.print("Inserisci il numero: ");
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + ip);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + ip);
            System.exit(1);
        }
    }
}
