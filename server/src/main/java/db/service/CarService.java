package db.service;

import com.google.gson.Gson;
import db.entity.Car;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static app.ClientSocket.sendData;
import static app.Main.sessionFactory;

public class CarService {

    public void selectClientCars(String clientId){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List cars = session.createQuery("FROM Car WHERE clientId = " + clientId).list();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(new ArrayList<Car>(cars)));
    }

    public void selectAllCars(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List cars = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(new ArrayList<Car>(cars)));
    }

    public void addCar(String carJson) {
        Gson gson = new Gson();
        Car car = gson.fromJson(carJson, Car.class);
        if(selectCarByStateNumber(car.getStateNumber())) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(car);
            transaction.commit();
            session.close();
            sendData("Успешно добавлено");
        }else{
            sendData("Машина с таким гос. номером уже есть в системе");
        }
    }

    public void updateCar(String carJson) {
        Gson gson = new Gson();
        Car car = gson.fromJson(carJson, Car.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(car);
        transaction.commit();
        session.close();
    }

    public void getCar(String carId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Car car = (Car) session.createQuery("FROM Car WHERE id = " + carId).uniqueResult();
        transaction.commit();
        session.close();
        sendData(new Gson().toJson(car));
    }

    public void deleteCar(String carJson) {
        Gson gson = new Gson();
        Car car = gson.fromJson(carJson, Car.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(car);
        transaction.commit();
        session.close();
    }

    public boolean selectCarByStateNumber(String stateNumber){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Car car = (Car) session.createQuery("FROM Car WHERE stateNumber = '" + stateNumber + "'").uniqueResult();
        transaction.commit();
        session.close();
        return car==null;
    }

}
