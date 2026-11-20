package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class ValidationUtil {
    private static final Pattern IPV4 = Pattern.compile(
        "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$"
    );
    private static final Pattern HOSTNAME = Pattern.compile(
        "^[A-Za-z0-9]([A-Za-z0-9._-]{0,98}[A-Za-z0-9])?$"
    );
    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9_]{3,20}$");
    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Set<String> VALID_STATUSES = Set.of("Active", "Inactive");
    private static final Set<String> VALID_RISKS = Set.of("Low", "Medium", "High");

    public static Map<String, String> readHostForm(HttpServletRequest req) {
        Map<String, String> map = new HashMap<>();
        map.put("ipAddress", trim(req.getParameter("ipAddress")));
        map.put("hostname", trim(req.getParameter("hostname")));
        map.put("operatingSystem", trim(req.getParameter("operatingSystem")));
        map.put("status", trim(req.getParameter("status")));
        map.put("riskLevel", trim(req.getParameter("riskLevel")));
        map.put("notes", trim(req.getParameter("notes")));
        return map;
    }

    public static Map<String, String> validateHost(Map<String, String> form) {
        Map<String, String> errors = new HashMap<>();
        String ip = form.get("ipAddress");
        if (ip.isEmpty()) {
            errors.put("ipAddress", "IP address is required.");
        } else if (!IPV4.matcher(ip).matches()) {
            errors.put("ipAddress", "Enter a valid IPv4 address, for example 192.168.1.10.");
        }

        String hostname = form.get("hostname");
        if (!hostname.isEmpty() && !HOSTNAME.matcher(hostname).matches()) {
            errors.put("hostname", "Hostname may contain letters, numbers, dots, hyphens, and underscores.");
        }

        String os = form.get("operatingSystem");
        if (os.length() > 100) {
            errors.put("operatingSystem", "Operating system must be 100 characters or fewer.");
        }

        if (!VALID_STATUSES.contains(form.get("status"))) {
            errors.put("status", "Select a valid status.");
        }

        if (!VALID_RISKS.contains(form.get("riskLevel"))) {
            errors.put("riskLevel", "Select a valid risk level.");
        }

        if (form.get("notes").length() > 2000) {
            errors.put("notes", "Notes must be 2000 characters or fewer.");
        }
        return errors;
    }

    public static Map<String, String> validateRegistration(String fullName, String username, String email, String password, String confirmPassword) {
        Map<String, String> errors = new HashMap<>();
        if (fullName == null || fullName.trim().length() < 2 || fullName.trim().length() > 60) {
            errors.put("fullName", "Full name must be between 2 and 60 characters.");
        }
        if (username == null || !USERNAME.matcher(username.trim()).matches()) {
            errors.put("username", "Username must be 3-20 characters (letters, numbers, underscore).");
        }
        if (email == null || !EMAIL.matcher(email.trim()).matches()) {
            errors.put("email", "Enter a valid email address.");
        }
        if (password == null || password.length() < 8 || !password.matches(".*[A-Za-z].*") || !password.matches(".*\\d.*")) {
            errors.put("password", "Password must be at least 8 characters with a letter and a digit.");
        }
        if (confirmPassword == null || !confirmPassword.equals(password)) {
            errors.put("confirmPassword", "Passwords do not match.");
        }
        return errors;
    }

    public static String trim(String val) {
        return val == null ? "" : val.trim();
    }
}