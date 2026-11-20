package servlets;

import dao.UserDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import util.SessionUtil;
import util.ValidationUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        request.setAttribute("flashMessage", SessionUtil.consumeFlash(request));
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = ValidationUtil.trim(request.getParameter("username"));
        String password = request.getParameter("password");

        if (username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("username", username);
            request.setAttribute("loginError", "Username and password are required.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDAO.authenticate(username, password);
            if (user != null) {
                if (request.getSession(false) != null) {
                    request.changeSessionId();
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(15 * 60); // auto-logout after 15 idle minutes
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }
            request.setAttribute("username", username);
            request.setAttribute("loginError", "Invalid username or password.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error during login", e);
        }
    }
}
