package service;

import DAO.CarDao;
import model.Car;

import java.util.List;

public class CarService {

    private static CarService carService;

    private CarService() {

    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService();
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return CarDao.getInstance().getAllCarsDAO();
    }

    public Long getIdCar(String brand, String model, String licensePlate) {
        CarDao carDao = CarDao.getInstance();
        Long id = null;
        try {
            id = carDao.getIdCar(brand, model, licensePlate);
        } catch (Exception e) {
            return null;
        }
        return id;
    }

    public Car buyCar(String brand, String model, String licensePlate) {
        CarDao carDao = CarDao.getInstance();
        Car car = null;
        try {
            car = carDao.buyCarDAO(brand, model, licensePlate);
        } catch (Exception e) {
            return null;
        }
        return car;
    }

    public boolean addCar(String brand, String model, String licensePlate, Long price) {
        Car car = new Car(brand, model, licensePlate, price);
        CarDao carDao = CarDao.getInstance();
        return carDao.addCarDAO(car);
    }

    public Car isPresentTable(String brand, String model, String licensePlate) {
        CarDao carDao = CarDao.getInstance();
        Car car = null;
        try {
            car = carDao.isPresentTable(brand, model, licensePlate);
        } catch (Exception e) {
            return null;
        }
        return car;
    }


}
