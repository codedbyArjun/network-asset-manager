package servlets;

import dao.HostDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Host;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final HostDAO hostDAO = new HostDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Map<String, Integer> stats = hostDAO.getDashboardStats();
            List<Host> recentHosts = hostDAO.findRecent(5);
            request.setAttribute("stats", stats);
            request.setAttribute("recentHosts", recentHosts);
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error loading dashboard", e);
        }
    }
}
