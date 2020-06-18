package servlet;

import DAO.CarDao;
import com.google.gson.Gson;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

        if (req.getPathInfo().contains("all")) {
            String json = gson.toJson(DailyReportService.getInstance().getAllDailyReports());
            resp.getWriter().println(json);
        } else if (req.getPathInfo().contains("last")) {
            String json = gson.toJson(DailyReportService.getInstance().getLastReport());
            resp.getWriter().println(json);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarDao.getInstance().drop();
    }
}
