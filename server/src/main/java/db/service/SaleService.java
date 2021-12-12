package db.service;

import com.google.gson.Gson;
import db.entity.Area;
import db.entity.Sale;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static app.ClientSocket.sendData;
import static app.Main.sessionFactory;

public class SaleService {

    public void addSale(String SaleJson) {
        Gson gson = new Gson();
        Sale sale = gson.fromJson(SaleJson, Sale.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(sale);
        transaction.commit();
        session.close();
    }

    public void selectClientSales(String clientId){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List Sales = session.createQuery("FROM Sale WHERE client = " + clientId).list();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(Sales));
    }

    public void selectWorkerSales(Integer workerId){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List Sales = session.createQuery("FROM Sale WHERE worker = " + workerId).list();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(Sales));
    }

    public void selectFreeSale(String area){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List Sales = session.createQuery("FROM Sale WHERE area = '" + area + "' AND status = 'Ожидает'").list();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(Sales));
    }

    public void deleteSale(String SaleJson) {
        Gson gson = new Gson();
        Sale sale = gson.fromJson(SaleJson, Sale.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(sale);
        transaction.commit();
        session.close();
    }

    public void deleteArea(String areaJson) {
        Gson gson = new Gson();
        Area area = gson.fromJson(areaJson, Area.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(area);
        transaction.commit();
        session.close();
    }

    public void addArea(String areaJson) {
        Gson gson = new Gson();
        Area area = gson.fromJson(areaJson, Area.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(area);
        transaction.commit();
        session.close();
    }

    public void updateSale(String SaleJson) {
        Gson gson = new Gson();
        Sale sale = gson.fromJson(SaleJson, Sale.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(sale);
        transaction.commit();
        session.close();
    }
}
