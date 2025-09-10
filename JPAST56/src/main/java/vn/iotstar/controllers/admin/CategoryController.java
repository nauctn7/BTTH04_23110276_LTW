package vn.iotstar.controllers.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.entity.Category;
import vn.iotstar.services.CategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = {"/views/admin1/categories.jsp"})
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // (Tuỳ chọn) set encoding nếu bạn render tiếng Việt
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        try {
            List<Category> listCategory = categoryService.findALL();
            req.setAttribute("listcate", listCategory);
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin1/categories.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Không thể lấy danh sách category: " + e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("/views/error.jsp");
            rd.forward(req, resp);
        }
    }
}
