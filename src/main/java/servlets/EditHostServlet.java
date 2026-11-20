package servlets;

import dao.HostDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Host;
import util.SessionUtil;
import util.ValidationUtil;

@WebServlet("/hosts/edit")
public class EditHostServlet extends HttpServlet {
    private final HostDAO hostDAO = new HostDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = parseId(request.getParameter("id"));
        if (id <= 0) {
            response.sendRedirect(request.getContextPath() + "/hosts");
            return;
        }

        try {
            Host host = hostDAO.findById(id);
            if (host == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Map<String, String> form = new HashMap<>();
            form.put("id", String.valueOf(host.getId()));
            form.put("ipAddress", host.getIpAddress());
            form.put("hostname", host.getHostname() == null ? "" : host.getHostname());
            form.put("operatingSystem", host.getOperatingSystem() == null ? "" : host.getOperatingSystem());
            form.put("status", host.getStatus());
            form.put("riskLevel", host.getRiskLevel());
            form.put("notes", host.getNotes() == null ? "" : host.getNotes());

            request.setAttribute("form", form);
            request.setAttribute("mode", "edit");
            request.getRequestDispatcher("/WEB-INF/views/hostForm.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error loading host for edit", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = parseId(request.getParameter("id"));
        if (id <= 0) {
            response.sendRedirect(request.getContextPath() + "/hosts");
            return;
        }

        Map<String, String> form = ValidationUtil.readHostForm(request);
        form.put("id", String.valueOf(id));
        Map<String, String> errors = ValidationUtil.validateHost(form);

        if (!errors.isEmpty()) {
            request.setAttribute("form", form);
            request.setAttribute("errors", errors);
            request.setAttribute("mode", "edit");
            request.getRequestDispatcher("/WEB-INF/views/hostForm.jsp").forward(request, response);
            return;
        }

        try {
            Host host = new Host();
            host.setId(id);
            host.setIpAddress(form.get("ipAddress"));
            host.setHostname(form.get("hostname"));
            host.setOperatingSystem(form.get("operatingSystem"));
            host.setStatus(form.get("status"));
            host.setRiskLevel(form.get("riskLevel"));
            host.setNotes(form.get("notes"));

            hostDAO.update(host);
            SessionUtil.flash(request, "Host " + host.getIpAddress() + " updated successfully.");
            response.sendRedirect(request.getContextPath() + "/host?id=" + id);
        } catch (SQLException e) {
            throw new ServletException("Database error updating host", e);
        }
    }

    private int parseId(String raw) {
        try {
            return Integer.parseInt(raw);
        } catch (Exception e) {
            return -1;
        }
    }
}
