package app;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

public class ClientSocket extends Thread {

    private int number;
    public Socket socket;
    public static ObjectInputStream in;
    public static ObjectOutputStream out;

    public ClientSocket(Socket s, int number) throws IOException {
        this.number = number;
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    @Override
    public void run() {
        try {
            RequestHandler requestHandler = new RequestHandler();
            while (true) {
                requestHandler.accept((String) in.readObject());
            }
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Пользователь " + number + " отключился.");
        } finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                System.err.println("Сокет не закрыт");
            }
        }
    }

    public static void sendData(Object object){
        try {
            out.flush();
            out.writeObject(object);
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}