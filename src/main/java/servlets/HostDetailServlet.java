package servlets;

import dao.HostDAO;
import dao.PortDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Host;
import model.Port;
import util.SessionUtil;

@WebServlet("/host")
public class HostDetailServlet extends HttpServlet {
    private final HostDAO hostDAO = new HostDAO();
    private final PortDAO portDAO = new PortDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/hosts");
            return;
        }

        try {
            Host host = hostDAO.findById(id);
            if (host == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            List<Port> ports = portDAO.findByHostId(id);
            request.setAttribute("host", host);
            request.setAttribute("ports", ports);
            request.setAttribute("flashMessage", SessionUtil.consumeFlash(request));
            request.getRequestDispatcher("/WEB-INF/views/hostDetail.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error loading host details", e);
        }
    }
}
