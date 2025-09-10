package vn.iotstar.services.impl;

import org.mindrot.jbcrypt.BCrypt;
import vn.iotstar.dao.UserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.entity.User;
import vn.iotstar.services.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String rawPassword) {
        User u = userDao.findByUsername(username);
        if (u != null && BCrypt.checkpw(rawPassword, u.getPasswordHash())) return u;
        return null;
    }

    @Override public User findById(int id) { return userDao.findById(id); }
    @Override public void create(User u) { userDao.create(u); }

    @Override
    public void seedUsersIfNeeded() {
        // 1 admin, 1 manager
        seedOne("admin",     "admin123",   3, "System Admin",   "admin@example.com");
        seedOne("manager01", "manager123", 2, "Default Manager","manager01@example.com");

        // 2 user như bạn yêu cầu
        seedOne("user01", "user123", 1, "User 01", "user01@example.com");
        seedOne("user02", "user123", 1, "User 02", "user02@example.com");
    }

    private void seedOne(String username, String pass, int roleId, String fullName, String email) {
        if (userDao.findByUsername(username) == null) {
            User u = new User();
            u.setUsername(username);
            u.setPasswordHash(BCrypt.hashpw(pass, BCrypt.gensalt()));
            u.setRoleId(roleId);
            u.setFullName(fullName);
            u.setEmail(email);
            u.setActive(true);
            userDao.create(u);
        }
    }

}
