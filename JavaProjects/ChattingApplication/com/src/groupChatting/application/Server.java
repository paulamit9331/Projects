package groupChatting.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Vector;

public class Server implements Runnable {
    Socket socket;
    public static Vector<BufferedWriter> clients = new Vector<>();

    public Server(Socket socket) {  
        super();    

        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clients.add(writer);

            while (true) {
                String message = reader.readLine().trim();
                System.out.println("Recieved " + message);

                for (BufferedWriter client : clients) {
                    client.write(message);
                    client.write("\r\n");
                    client.flush();
                }
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(2020);

            while (true) {
                Socket socket = serverSocket.accept();
                Thread server = new Thread(new Server(socket));
                server.start();
            }
        } catch (IOException e) {
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}