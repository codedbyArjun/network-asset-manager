package servlets;

import dao.HostDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.SessionUtil;

@WebServlet("/hosts/delete")
public class DeleteHostServlet extends HttpServlet {
    private final HostDAO hostDAO = new HostDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            hostDAO.delete(id);
            SessionUtil.flash(request, "Host and its associated ports were deleted.");
        } catch (NumberFormatException ignored) {
            // Invalid ID ignored
        } catch (SQLException e) {
            throw new ServletException("Database error deleting host", e);
        }
        response.sendRedirect(request.getContextPath() + "/hosts");
    }
}
