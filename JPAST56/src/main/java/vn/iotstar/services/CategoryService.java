package vn.iotstar.services;

import java.util.List;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;

public interface CategoryService {
    void insert(Category category);
    void update(Category category, User actor);   // kiểm tra quyền
    void delete(int id, User actor);             // kiểm tra quyền
    Category findById(int id);
    List<Category> findALL();
    List<Category> findByOwner(int ownerId);
}
