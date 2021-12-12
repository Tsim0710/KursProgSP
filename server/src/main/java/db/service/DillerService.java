package db.service;

import com.google.gson.Gson;
import db.entity.Diller;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

import static app.ClientSocket.sendData;
import static app.Main.sessionFactory;

public class DillerService {

    public void addDiller(String dillerJson) {
        Gson gson = new Gson();
        Diller diller = gson.fromJson(dillerJson, Diller.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(diller);
        transaction.commit();
        session.close();
    }

    public void selectAreaDillers(String area){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List dillers = session.createQuery("FROM Diller WHERE id >= 1 AND area = '" + area + "'").list();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(dillers));
    }

    public void selectAllDillers(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List dillers = session.createQuery("FROM Diller WHERE id > 1").list();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(dillers));
    }

    public void updateDiller(String dillerJson) {
        Gson gson = new Gson();
        Diller diller = gson.fromJson(dillerJson, Diller.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(diller);
        transaction.commit();
        session.close();
    }

    public void deleteDiller(Integer dillerId) {
        updateDillerSales(dillerId);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Diller diller = session.get(Diller.class, dillerId);
        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(diller);
        transaction.commit();
        session.close();
    }

    public void updateDillerSales(Integer dillerId){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("UPDATE Sale SET diller = 1, price = 0.0, status = 'Ожидает' where diller = " + dillerId);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

}
