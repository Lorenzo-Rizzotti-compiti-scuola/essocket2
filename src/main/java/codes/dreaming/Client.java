package codes.dreaming;

import java.io.*;
import java.net.*;

public class Client {
    private boolean isWinAnnounced = false;

    public void start(String ip, int port) {
        try (Socket echoSocket = new Socket(ip, port);
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            // Start a new thread that will listen for the winning message from the server
            Thread listenerThread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String serverResponse = in.readLine();
                        switch (Integer.parseInt(serverResponse)){
                            case 1 -> System.out.println("Il numero è troppo piccolo");
                            case 2 -> System.out.println("Il numero è troppo grande");
                            case 3 -> {
                                System.out.println("Hai indovinato!");
                                System.exit(0);
                            }
                            case 4 -> {
                                System.out.println("Hai perso, qualcuno ha indovinato prima di te!");
                                System.exit(0);
                            }
                        }

                        System.out.print("Inserisci il numero: ");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            listenerThread.start(); // Start the listener thread

            System.out.print("Inserisci il numero: ");
            for (int i = 1; !isWinAnnounced; i++) {
                String userInput = stdIn.readLine();
                out.println(userInput);
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
