package vn.iotstar.services.impl;

import java.util.List;
import vn.iotstar.dao.CategoryDao;
import vn.iotstar.dao.impl.CategoryDaoImpl;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.services.CategoryService;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao cateDao = new CategoryDaoImpl();

    @Override public void insert(Category category) { cateDao.create(category); }

    @Override
    public void update(Category category, User actor) {
        if (!canTouch(actor, category)) {
            throw new SecurityException("Không có quyền cập nhật category này.");
        }
        cateDao.update(category);
    }

    @Override
    public void delete(int id, User actor) {
        Category c = cateDao.findById(id);
        if (c == null) return;
        if (!canTouch(actor, c)) {
            throw new SecurityException("Không có quyền xóa category này.");
        }
        cateDao.delete(id);
    }

    @Override public Category findById(int id) { return cateDao.findById(id); }
    @Override public List<Category> findALL() { return cateDao.findAll(); }
    @Override public List<Category> findByOwner(int ownerId) { return cateDao.findByOwnerId(ownerId); }

    private boolean canTouch(User actor, Category c) {
        if (actor == null || c == null) return false;
        return actor.getRoleId() == 3 || c.getOwner().getId() == actor.getId();
    }
}
