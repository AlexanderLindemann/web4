package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        DailyReportDao dailyReportDao = DailyReportDao.getInstance();
        List<DailyReport> list = dailyReportDao.getAllDailyReport();
        return list;
    }

    public DailyReport getNewReport() {
        DailyReportDao dailyReportDao = DailyReportDao.getInstance();
        CarDao carDao = CarDao.getInstance();
        long earning = carDao.getSoldCar().size();
        long soldCars = 0;
        for (Car car : carDao.getSoldCar()) {
            soldCars += car.getPrice();
        }
        DailyReport dailyReport = new DailyReport(soldCars, earning);
        dailyReportDao.saveNewDailyReport(dailyReport);
        carDao.getSoldCar().clear();
        return dailyReport;
    }

    public DailyReport getLastReport() {
        DailyReportDao dailyReportDao = DailyReportDao.getInstance();
        DailyReport dailyReport = dailyReportDao.getLastReport();
        return dailyReport;
    }
}
