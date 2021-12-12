package db.service;

import com.google.gson.Gson;
import db.entity.Car;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static app.ClientSocket.sendData;
import static app.Main.sessionFactory;

public class AreaService {

    public void selectAreas(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List areas = session.createQuery("FROM Area").list();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(new ArrayList<Car>(areas)));
    }

}
