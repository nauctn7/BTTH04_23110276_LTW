package vn.iotstar.controllers;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;

import vn.iotstar.dao.UserDao;                 // dùng DAO để check trùng username (không sửa service cũ)
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.entity.User;
import vn.iotstar.services.UserService;
import vn.iotstar.services.impl.UserServiceImpl;

@WebServlet(urlPatterns={"/register"})
public class RegisterController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final UserService userService = new UserServiceImpl();
  private final UserDao userDao = new UserDaoImpl(); // chỉ dùng để kiểm tra trùng username

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String username = safe(req.getParameter("username"));
    String password = req.getParameter("password");
    String confirm  = req.getParameter("confirm");
    String fullName = safe(req.getParameter("fullName"));
    String email    = safe(req.getParameter("email"));

    // Prefill khi lỗi
    req.setAttribute("prefill_username", username);
    req.setAttribute("prefill_fullName", fullName);
    req.setAttribute("prefill_email", email);

    Map<String,String> errors = new LinkedHashMap<>();

    // ===== Validate =====
    // username: bắt buộc + pattern 3-30 ký tự (chữ/số/._-)
    if (username.isBlank()) {
      errors.put("username", "Vui lòng nhập tài khoản.");
    } else if (!Pattern.matches("^[a-zA-Z0-9._-]{3,30}$", username)) {
      errors.put("username", "Tài khoản chỉ gồm chữ/số/._- (3–30 ký tự).");
    } else if (userDao.findByUsername(username) != null) {
      // kiểm tra trùng username qua DAO
      errors.put("username", "Tài khoản đã tồn tại.");
    }

    // password: bắt buộc + tối thiểu 6
    if (password == null || password.isBlank()) {
      errors.put("password", "Vui lòng nhập mật khẩu.");
    } else if (password.length() < 6) {
      errors.put("password", "Mật khẩu tối thiểu 6 ký tự.");
    }

    // confirm phải khớp
    if (confirm == null || !confirm.equals(password)) {
      errors.put("confirm", "Xác nhận mật khẩu không khớp.");
    }

    // email: bắt buộc + đúng định dạng
    if (email.isBlank()) {
      errors.put("email", "Vui lòng nhập email.");
    } else {
      String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
      if (!Pattern.matches(emailRegex, email)) {
        errors.put("email", "Email không hợp lệ.");
      }
    }

    // ===== Có lỗi -> trả về form với map errors =====
    if (!errors.isEmpty()) {
      req.setAttribute("errors", errors);
      req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
      return;
    }

    try {
      // ===== Tạo user mới (roleId = 1) =====
      User u = new User();
      u.setUsername(username);

      // Nếu entity của bạn dùng trường bcrypt:
      u.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
      // Nếu project bạn dùng plaintext cũ, thay dòng trên bằng:
      // u.setPassWord(password);

      u.setFullName(fullName.isBlank() ? username : fullName);
      u.setEmail(email);
      u.setRoleId(1);
      try { u.setActive(true); } catch (Throwable ignore) {}

      userService.create(u); // dùng service hiện có của bạn

      // Flash + về login
      req.getSession().setAttribute("flash", "Đăng ký thành công! Vui lòng đăng nhập.");
      resp.sendRedirect(req.getContextPath() + "/login");
    } catch (Exception ex) {
      // Trường hợp hi hữu (VD: unique constraint ở DB)
      req.setAttribute("errors", Map.of("global", "Không thể đăng ký: " + ex.getMessage()));
      req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
    }
  }

  private String safe(String s){ return s==null? "": s.trim(); }
}
