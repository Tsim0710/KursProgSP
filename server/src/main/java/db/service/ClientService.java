package db.service;

import com.google.gson.Gson;
import db.entity.Client;
import db.entity.Worker;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static app.ClientSocket.sendData;
import static app.Main.sessionFactory;

public class ClientService {

    public void getClient(int userId) {
        Gson gson = new Gson();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Client client = (Client) session.createQuery("FROM Client WHERE user = " + userId).uniqueResult();
        transaction.commit();
        session.close();
        sendData(gson.toJson(client));
    }

    public void selectAllClients() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List clients = session.createQuery("FROM Client").list();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(clients));
    }

    public void updateClient(String clientInfo) {
        Gson gson = new Gson();
        Client client = gson.fromJson(clientInfo, Client.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(client);
        transaction.commit();
        session.close();
    }
}
