package com.intertecintl.service.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intertecintl.model.User;
import com.intertecintl.repository.UserRepository;
import com.intertecintl.service.UserService;
import com.intertecintl.model.Result;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	public User findById(long id) {
		return userRepository.findOne(id);
	}

	public User findByName(String name) {
		return userRepository.findByUsername(name);
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public void deleteUserById(long id) {
		userRepository.delete(id);
	}

	public boolean isUserExist(User user) {
		return findByName(user.getUsername()) != null;
	}

	@Override
	public Result<Boolean, List<String>> checkUsername(User user) {
		Result<Boolean, List<String>> result = new Result<Boolean, List<String>>(isUserExist(user));
		
		if(!result.getKey()){
			result.setValues(generateSuggestedNames(user));
		}
		return result;
	}

	private List<String> generateSuggestedNames(User user) {
		// TODO Create the real logic
		return generateStubNames(user);
	}

	private List<String> generateStubNames(User user) {
		List<String> suggested =  null;
		Collection<String> orderList = new TreeSet<String>(Collator.getInstance());
		orderList.add("GustavoAT8");
		orderList.add("GustavoGS3");
		orderList.add("GustavoTO2");
		orderList.add("GustavoSA1");
		orderList.add("GustavoAS1");
		orderList.add("GustavoGT9");
		orderList.add("GustavoUS3");
		orderList.add("GustavoVA2");
		orderList.add("Gustavo2ST");
		orderList.add("GustavoA4G");
		orderList.add("Gustavo4AV");
		orderList.add("GustavoST1");
		orderList.add("GustavoOU4");
		orderList.add("GustavoTAS");
		suggested = new ArrayList<>(orderList);
		return suggested;
	}

}
