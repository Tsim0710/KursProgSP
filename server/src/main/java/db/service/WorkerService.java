package db.service;

import com.google.gson.Gson;
import db.entity.Car;
import db.entity.User;
import db.entity.Worker;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static app.ClientSocket.sendData;
import static app.Main.sessionFactory;

public class WorkerService {

    public void updateWorker(String workerInfo) {
        Gson gson = new Gson();
        Worker worker = gson.fromJson(workerInfo, Worker.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(worker);
        transaction.commit();
        session.close();
    }

    public void getWorker(Integer workerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Worker worker = session.get(Worker.class, workerId);
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(worker));
    }

    public void selectAllBrokers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List workers = session.createQuery("FROM Worker where id > 1").list();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(workers));
    }

    public void selectAreaBrokers(String area) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List workers = session.createQuery("FROM Worker where id > 1 and area = '" + area + "'").list();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(workers));
    }

    public void getWorkerByUser(Integer userId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Worker worker = (Worker) session.createQuery("FROM Worker WHERE user = " + userId).uniqueResult();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(worker));
    }

}
