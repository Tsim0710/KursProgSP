package db.service;

import com.google.gson.Gson;
import db.entity.Client;
import db.entity.User;
import db.entity.Worker;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import static app.ClientSocket.sendData;
import static app.Main.sessionFactory;

public class UserService {

    public void getUser(String userInfo) {
        Gson gson = new Gson();
        User user = gson.fromJson(userInfo, User.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User extendUser = (User) session.createQuery("FROM User WHERE login = '" + user.getLogin() + "' and  password = '" + user.getPassword() + "'").uniqueResult();
        transaction.commit();
        session.close();
        if(extendUser!=null){
            sendData(gson.toJson(extendUser));
            switch (extendUser.getRole()){
                case "Клиент":
                    new ClientService().getClient(extendUser.getId());
                    break;
                case "Работник":
                    new WorkerService().getWorkerByUser(extendUser.getId());
                    break;
            }
        }else{
            sendData("null");
        }
    }

    public void checkLogin(String userInfo) {
        User user = new Gson().fromJson(userInfo, User.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User extendUser = (User) session.createQuery("FROM User WHERE login = '" + user.getLogin() + "'").uniqueResult();
        transaction.commit();
        session.close();
        if(extendUser!=null){
            sendData("Пользователь с таким логином уже существует");
        }else{
            sendData("null");
        }
    }

    public void addWorker(String userInfo, String workerInfo) {
        Gson gson = new Gson();
        User user = gson.fromJson(userInfo, User.class);
        Worker worker = gson.fromJson(workerInfo, Worker.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        User extendUser = (User) session.createQuery("FROM User WHERE login = '" + user.getLogin() + "'").uniqueResult();
        transaction.commit();
        session.close();
        worker.setUser(extendUser.getId());
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(worker);
        transaction.commit();
        session.close();
    }

    public void addClient(String userInfo, String clientInfo) {
        Gson gson = new Gson();
        User user = gson.fromJson(userInfo, User.class);
        Client client = gson.fromJson(clientInfo, Client.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        User extendUser = (User) session.createQuery("FROM User WHERE login = '" + user.getLogin() + "'").uniqueResult();
        transaction.commit();
        session.close();
        client.setUser(extendUser.getId());
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(client);
        transaction.commit();
        session.close();
    }

    public void updateWorkerSales(Integer workerId){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("UPDATE Sale SET worker = 1, diller = 1, price = 0.0, status = 'Ожидает' where worker = " + workerId);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void deleteWorker(Integer userId, Integer workerId) {
        updateWorkerSales(workerId);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, userId);
        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public void deleteClient(Integer userId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, userId);
        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

}
