package DAO;

import model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CarDao {
    private static CarDao carDao;
    private Session session;
    private List<Car> soldCar;
    private SessionFactory sessionFactory;

    public static CarDao getInstance() {
        if (carDao == null) {
            carDao = new CarDao(DBHelper.getSessionFactory());
        }
        return carDao;
    }

    private CarDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        soldCar = new ArrayList<>();
    }

    public List<Car> getSoldCar() {
        return soldCar;
    }

    public void setSoldCar(List<Car> soldCar) {
        this.soldCar = soldCar;
    }

    public List<Car> getAllCarsDAO() {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Car> listCar = session.createCriteria(Car.class).list();
        transaction.commit();
        session.close();
        return listCar;
    }

    public Long getIdCar(String brand, String model, String licensePlate) {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Car> listCar = session.createCriteria(Car.class).
                add(Restrictions.eq("brand", brand)).
                add(Restrictions.eq("model", model)).
                add(Restrictions.eq("licensePlate", licensePlate)).
                list();
        Car car = listCar.get(0);
        Long id = car.getId();
        transaction.commit();
        session.close();
        return id;
    }

    public Car buyCarDAO(String brand, String model, String licensePlate) {
        session = sessionFactory.openSession();
        Long id = getIdCar(brand, model, licensePlate);

        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Car tmpCar = (Car) session.get(Car.class, id);
        soldCar.add(tmpCar);
        session.delete(tmpCar);
        transaction.commit();
        session.close();
        return tmpCar;
    }

    public Car isPresentTable(String brand, String model, String licensePlate) {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Car> listCar = session.createCriteria(Car.class).
                add(Restrictions.eq("brand", brand)).
                add(Restrictions.eq("model", model)).
                add(Restrictions.eq("licensePlate", licensePlate)).
                list();
        Car car = listCar.get(0);
        transaction.commit();
        session.close();
        return car;
    }

    public boolean addCarDAO(Car car) {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Car> listCar = session.createCriteria(Car.class).
                add(Restrictions.eq("brand", car.getBrand())).list();
        transaction.commit();
        int i = getAllCarsDAO().size();
        if (listCar.size() >= 10) {
            return false;
        }

        session = sessionFactory.openSession();
        Transaction transaction1 = session.beginTransaction();
        session.save(car);
        transaction1.commit();
        boolean isAdded = i < getAllCarsDAO().size();
        return isAdded;
    }


    public void drop() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery("DELETE FROM db_example.cars").executeUpdate();
        session.createSQLQuery("DELETE FROM db_example.daily_reports").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}

