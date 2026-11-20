package servlets;

import dao.UserDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.SessionUtil;
import util.ValidationUtil;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = ValidationUtil.trim(request.getParameter("fullName"));
        String username = ValidationUtil.trim(request.getParameter("username"));
        String email = ValidationUtil.trim(request.getParameter("email"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        Map<String, String> errors = ValidationUtil.validateRegistration(
            fullName, username, email, password, confirmPassword
        );

        try {
            if (errors.isEmpty() && userDAO.existsByUsernameOrEmail(username, email)) {
                errors.put("username", "Username or email is already registered.");
            }

            if (!errors.isEmpty()) {
                request.setAttribute("fullName", fullName);
                request.setAttribute("username", username);
                request.setAttribute("email", email);
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }

            userDAO.createUser(fullName, username, email, password);
            SessionUtil.flash(request, "Account created successfully. Please sign in.");
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (SQLException e) {
            throw new ServletException("Database error during registration", e);
        }
    }
}
