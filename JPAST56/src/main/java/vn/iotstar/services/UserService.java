package vn.iotstar.services;

import vn.iotstar.entity.User;

public interface UserService {
    User login(String username, String rawPassword);
    User findById(int id);
    void create(User u);

    // Tạo sẵn 3 tài khoản mẫu
    void seedUsersIfNeeded();
}
