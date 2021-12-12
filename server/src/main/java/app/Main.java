package app;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main  implements Runnable{

    public static SessionFactory sessionFactory;
    public static int clients = 1;

    public static void main(String[] args) {
        new Thread(new Main()).start();
    }

    @Override
    public void run() {
        ServerSocket s = null;
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("Сервер стартовал");
            s = new ServerSocket(3456);
            while (true) {
                Socket socket = s.accept();
                if (socket!=null)
                {
                    System.out.println("Подключился " + clients + " пользователь. " + socket.getInetAddress());
                    new ClientSocket(socket, clients);
                    clients++;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка работы сервера...");
        }finally {
            try {
                System.out.println("Сервер завершил работу.");
                assert s != null;
                s.close();
            }catch (IOException e) {
                System.out.println("Ошибка завершения работы сервера...");
            }
        }
    }
}