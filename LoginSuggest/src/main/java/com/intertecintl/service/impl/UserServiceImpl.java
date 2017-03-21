package com.intertecintl.service.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intertecintl.model.User;
import com.intertecintl.repository.RestrictedWordRepository;
import com.intertecintl.repository.UserRepository;
import com.intertecintl.service.UserService;
import com.intertecintl.Util.UserUtil;
import com.intertecintl.model.RestrictedWord;
import com.intertecintl.model.Result;

/**
 * @author Gustavo
 *
 */
/**
 * @author Gustavo
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestrictedWordRepository wordRepository;

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

	/* (non-Javadoc)
	 * @see com.intertecintl.service.UserService#checkUsername(com.intertecintl.model.User)
	 */
	@Override
	public Result<Boolean, List<String>> checkUsername(User user) {
		Result<Boolean, List<String>> result = new Result<Boolean, List<String>>(isUserExist(user));
		Iterable<RestrictedWord> dictionary = wordRepository.findAll();
		if (!result.getKey()) {
			result.setValues(generateSuggestedNames(user, dictionary));
		}
		return result;
	}

	/**
	 * @param user
	 *            user with the given name
	 * @param regenerate
	 *            true to specify if its necessary to change the complete name,
	 *            false - to just add a random 3 char or number at the end.
	 * @return Generates suggested names based on the name of the given user,
	 * 
	 */
	private List<String> generateSuggestedNames(User user, Iterable<RestrictedWord> dictionary) {
		boolean regenerate = hasRestrictedWord(user, dictionary);
		String suggested = user.getUsername();
		int badSuggestions = 0, counter = 0;

		Collection<String> orderList = new TreeSet<String>(Collator.getInstance());

		while (badSuggestions >= 3 || counter >= 14) {
			if (regenerate) {
				suggested = UserUtil.randomString(user.getUsername(), user.getUsername().length());
			} else {
				suggested = user.getUsername() + "." + UserUtil.randomString(user.getUsername(), 3);
			}
			if (isValidSuggestion(suggested, dictionary)) {
				orderList.add(suggested);
				counter++;
			} else {
				badSuggestions++;
			}
		}
		return new ArrayList<String>(orderList);
	}

	/**
	 * @param suggested
	 * @param dictionary
	 * @return Checks if suggested name already exists or has a restricted word
	 */
	private boolean isValidSuggestion(String suggested, Iterable<RestrictedWord> dictionary) {
		User user = new User(suggested);
		if (!isUserExist(user)) {
			if (!hasRestrictedWord(user, dictionary)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param user
	 *            with a name
	 * @param dictionary
	 * @return true if username contains any of the words in the dictionary
	 */
	private boolean hasRestrictedWord(User user, Iterable<RestrictedWord> dictionary) {
		String username = user.getUsername().toLowerCase();
		for (RestrictedWord restrictedWord : dictionary) {
			if (username.indexOf(restrictedWord.getWord().toLowerCase()) != -1) {
				return true;
			}
		}
		return false;
	}

	private List<String> generateStubNames(User user) {
		List<String> suggested = null;
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
