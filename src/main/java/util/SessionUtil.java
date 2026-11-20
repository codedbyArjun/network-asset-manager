package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.User;

public class SessionUtil {
    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }

    public static User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        return (User) session.getAttribute("user");
    }

    public static void flash(HttpServletRequest request, String message) {
        HttpSession session = request.getSession(true);
        session.setAttribute("flashMessage", message);
    }

    public static String consumeFlash(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        String msg = (String) session.getAttribute("flashMessage");
        if (msg != null) {
            session.removeAttribute("flashMessage");
        }
        return msg;
    }
}
