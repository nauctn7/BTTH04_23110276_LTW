package vn.iotstar.controllers.category;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.services.CategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;
import vn.iotstar.utils.UploadUtils;

@MultipartConfig(fileSizeThreshold = 2*1024*1024, maxFileSize = 10*1024*1024, maxRequestSize = 20*1024*1024)
@WebServlet(urlPatterns={"/category/add"})
public class AddCategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService cateService = new CategoryServiceImpl();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/category/add.jsp").forward(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User u = (User) req.getSession().getAttribute("currentUser");

        try {
            String name = req.getParameter("name");
            Part imagePart = req.getPart("imageFile");
            String fileName = UploadUtils.saveImage(req, imagePart); // có thể null

            Category c = new Category();
            c.setCategoryname(name);
            c.setImages(fileName);
            c.setOwner(u);

            cateService.insert(c);
            req.getSession().setAttribute("flash", "Đã tạo category thành công!");
            redirectHomeByRole(u, req, resp);
        } catch (Exception ex) {
            req.setAttribute("error", ex.getMessage());
            req.getRequestDispatcher("/views/category/add.jsp").forward(req, resp);
        }
    }

    private void redirectHomeByRole(User u, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String ctx = req.getContextPath();
        if (u.getRoleId() == 2) resp.sendRedirect(ctx + "/manager/home");
        else if (u.getRoleId() == 3) resp.sendRedirect(ctx + "/admin/home");
        else resp.sendRedirect(ctx + "/user/home");
    }
}
