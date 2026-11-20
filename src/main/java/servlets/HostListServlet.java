package servlets;

import dao.HostDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Host;
import util.SessionUtil;
import util.ValidationUtil;

@WebServlet("/hosts")
public class HostListServlet extends HttpServlet {
    private final HostDAO hostDAO = new HostDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String q = ValidationUtil.trim(request.getParameter("q"));
        String risk = ValidationUtil.trim(request.getParameter("risk"));
        if (risk.isEmpty()) risk = "All";

        try {
            List<Host> hosts = hostDAO.search(q, risk);
            request.setAttribute("hosts", hosts);
            request.setAttribute("q", q);
            request.setAttribute("risk", risk);
            request.setAttribute("flashMessage", SessionUtil.consumeFlash(request));
            request.getRequestDispatcher("/WEB-INF/views/hosts.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error loading hosts", e);
        }
    }
}
