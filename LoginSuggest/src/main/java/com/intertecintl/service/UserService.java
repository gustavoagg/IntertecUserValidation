package com.intertecintl.service;

import java.util.List;

import com.intertecintl.model.User;

public interface UserService {

	User findById(long id);

	User findByName(String name);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserById(long id);

	List<User> findAllUsers();

	public boolean isUserExist(User user);

}
