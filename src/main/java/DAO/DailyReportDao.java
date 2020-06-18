package DAO;

import model.DailyReport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import util.DBHelper;

import java.util.List;

public class DailyReportDao {

    private Session session;
    private static DailyReportDao dailyReportDao;
    private SessionFactory sessionFactory;

    public static DailyReportDao getInstance() {
        if (dailyReportDao == null) {
            dailyReportDao = new DailyReportDao(DBHelper.getSessionFactory());
        }
        return dailyReportDao;
    }

    private DailyReportDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<DailyReport> getAllDailyReport() {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public void saveNewDailyReport(DailyReport dailyReport) {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(dailyReport);
        transaction.commit();
        session.close();
    }

    public DailyReport getLastReport() {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria c = session.createCriteria(DailyReport.class);
        c.addOrder(Order.desc("id"));
        c.setMaxResults(1);
        DailyReport dailyReport = (DailyReport) c.uniqueResult();
        transaction.commit();
        session.close();
        return dailyReport;
    }
}
