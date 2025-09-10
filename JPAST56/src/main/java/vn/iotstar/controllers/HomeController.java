package vn.iotstar.controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.services.CategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;

@WebServlet(urlPatterns={"/user/home", "/manager/home", "/admin/home"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("currentUser");
        String uri = req.getRequestURI();

        List<Category> data = (uri.contains("/manager/"))
                ? categoryService.findByOwner(u.getId()) // chỉ của mình
                : categoryService.findALL();             // user & admin thấy tất cả

        req.setAttribute("listcate", data);
        req.getRequestDispatcher("/views/category/list.jsp").forward(req, resp);
    }
}
