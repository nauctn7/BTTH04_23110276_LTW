package vn.iotstar.controllers.category;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.services.CategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;
import vn.iotstar.utils.UploadUtils;

@WebServlet(urlPatterns={"/category/delete"})
public class DeleteCategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService cateService = new CategoryServiceImpl();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("currentUser");
        int id = Integer.parseInt(req.getParameter("id"));
        Category c = cateService.findById(id);
        if (c == null) { resp.sendError(404); return; }
        try {
            cateService.delete(id, u);
            UploadUtils.deleteImage(req, c.getImages());
            req.getSession().setAttribute("flash", "Đã xóa category!");
            resp.sendRedirect(req.getContextPath() + (u.getRoleId()==2?"/manager/home":u.getRoleId()==3?"/admin/home":"/user/home"));
        } catch (SecurityException se) {
            resp.sendError(403, se.getMessage());
        }
    }
}
