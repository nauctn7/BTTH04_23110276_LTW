package vn.iotstar.controllers.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;
import vn.iotstar.services.UserService;
import vn.iotstar.services.impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class AuthController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    @Override
    public void init() {
        userService.seedUsersIfNeeded();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/logout")) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        User user = userService.login(u, p);
        if (user == null) {
            req.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
            return;
        }
        req.getSession().setAttribute("currentUser", user);
        switch (user.getRoleId()) {
            case 1 -> resp.sendRedirect(req.getContextPath() + "/user/home");
            case 2 -> resp.sendRedirect(req.getContextPath() + "/manager/home");
            case 3 -> resp.sendRedirect(req.getContextPath() + "/admin/home");
            default -> resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
