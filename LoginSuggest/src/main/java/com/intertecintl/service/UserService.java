package com.intertecintl.service;

import java.util.List;

import com.intertecintl.model.User;
import com.intertecintl.model.RestrictedWord;
import com.intertecintl.model.Result;

public interface UserService {

	User findById(long id);

	User findByName(String name);

	void saveUser(User user);

	void deleteUserById(long id);

	List<User> findAllUsers();

	public boolean isUserExist(User user);
		
	
	/**
	 * @param user with assigned name
	 * @return checks to see if user is valid and is not registered, if registered
	 * it will generate a list of 14 different and valid user names
	 */
	public Result<Boolean,List<String>> checkUsername(User user);

	boolean isValidSuggestion(String string, Iterable<RestrictedWord> dictionary);

}
