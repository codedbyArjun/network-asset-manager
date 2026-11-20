package servlets;

import dao.HostDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Host;
import util.SessionUtil;
import util.ValidationUtil;

@WebServlet("/hosts/add")
public class AddHostServlet extends HttpServlet {
    private final HostDAO hostDAO = new HostDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("mode", "add");
        request.getRequestDispatcher("/WEB-INF/views/hostForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> form = ValidationUtil.readHostForm(request);
        Map<String, String> errors = ValidationUtil.validateHost(form);

        if (!errors.isEmpty()) {
            request.setAttribute("form", form);
            request.setAttribute("errors", errors);
            request.setAttribute("mode", "add");
            request.getRequestDispatcher("/WEB-INF/views/hostForm.jsp").forward(request, response);
            return;
        }

        try {
            Host host = new Host();
            host.setIpAddress(form.get("ipAddress"));
            host.setHostname(form.get("hostname"));
            host.setOperatingSystem(form.get("operatingSystem"));
            host.setStatus(form.get("status"));
            host.setRiskLevel(form.get("riskLevel"));
            host.setNotes(form.get("notes"));

            int newId = hostDAO.add(host);
            SessionUtil.flash(request, "Host " + host.getIpAddress() + " was added. You can now log its open ports.");
            response.sendRedirect(request.getContextPath() + "/host?id=" + newId);
        } catch (SQLException e) {
            throw new ServletException("Database error while adding a host", e);
        }
    }
}
