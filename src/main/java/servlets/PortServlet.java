package servlets;

import dao.PortDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Port;
import util.SessionUtil;
import util.ValidationUtil;

@WebServlet(urlPatterns = {"/ports/add", "/ports/delete"})
public class PortServlet extends HttpServlet {
    private final PortDAO portDAO = new PortDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        int hostId;
        try {
            hostId = Integer.parseInt(request.getParameter("hostId"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/hosts");
            return;
        }

        try {
            if ("/ports/add".equals(servletPath)) {
                int portNum = Integer.parseInt(request.getParameter("portNumber"));
                String serviceName = ValidationUtil.trim(request.getParameter("serviceName"));
                String note = ValidationUtil.trim(request.getParameter("vulnerabilityNote"));

                if (portNum < 1 || portNum > 65535 || serviceName.isEmpty()) {
                    SessionUtil.flash(request, "Invalid port number (1-65535) or empty service name.");
                    response.sendRedirect(request.getContextPath() + "/host?id=" + hostId);
                    return;
                }

                Port port = new Port();
                port.setHostId(hostId);
                port.setPortNumber(portNum);
                port.setServiceName(serviceName);
                port.setVulnerabilityNote(note);
                portDAO.add(port);
                SessionUtil.flash(request, "Port " + portNum + " (" + serviceName + ") logged.");
            } else if ("/ports/delete".equals(servletPath)) {
                int portId = Integer.parseInt(request.getParameter("portId"));
                portDAO.delete(portId, hostId);
                SessionUtil.flash(request, "Port entry removed.");
            }
        } catch (NumberFormatException e) {
            SessionUtil.flash(request, "Port number must be an integer between 1 and 65535.");
        } catch (SQLException e) {
            throw new ServletException("Database error managing port", e);
        }

        response.sendRedirect(request.getContextPath() + "/host?id=" + hostId);
    }
}
