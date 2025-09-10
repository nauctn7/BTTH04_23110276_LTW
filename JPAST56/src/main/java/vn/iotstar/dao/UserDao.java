package vn.iotstar.dao;

import vn.iotstar.entity.User;

public interface UserDao {
	  User findByUsername(String username);
	  User findById(int id);
	  void create(User u);
	}
