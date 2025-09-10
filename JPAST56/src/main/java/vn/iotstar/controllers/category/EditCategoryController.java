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
@WebServlet(urlPatterns={"/category/edit"})
public class EditCategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService cateService = new CategoryServiceImpl();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Category c = cateService.findById(id);
        if (c == null) { resp.sendError(404); return; }
        req.setAttribute("category", c);
        req.getRequestDispatcher("/views/category/edit.jsp").forward(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User u = (User) req.getSession().getAttribute("currentUser");
        int id = Integer.parseInt(req.getParameter("id"));

        Category c = cateService.findById(id);
        if (c == null) { resp.sendError(404); return; }

        String oldFile = c.getImages();
        c.setCategoryname(req.getParameter("name"));

        try {
            Part imagePart = req.getPart("imageFile");
            if (imagePart != null && imagePart.getSize() > 0) {
                String newFile = UploadUtils.saveImage(req, imagePart);
                c.setImages(newFile);
            }
            cateService.update(c, u);
            // Xóa file cũ nếu đã upload file mới
            if (c.getImages() != null && oldFile != null && !oldFile.equals(c.getImages())) {
                UploadUtils.deleteImage(req, oldFile);
            }
            req.getSession().setAttribute("flash", "Đã cập nhật category!");
            resp.sendRedirect(req.getContextPath() + (u.getRoleId()==2?"/manager/home":u.getRoleId()==3?"/admin/home":"/user/home"));
        } catch (SecurityException se) {
            req.setAttribute("error", se.getMessage());
            req.setAttribute("category", c);
            req.getRequestDispatcher("/views/category/edit.jsp").forward(req, resp);
        } catch (Exception ex) {
            req.setAttribute("error", ex.getMessage());
            req.setAttribute("category", c);
            req.getRequestDispatcher("/views/category/edit.jsp").forward(req, resp);
        }
    }
}
