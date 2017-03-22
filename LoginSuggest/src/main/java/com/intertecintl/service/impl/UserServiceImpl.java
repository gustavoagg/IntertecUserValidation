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

	public void deleteUserById(long id) {
		userRepository.delete(id);
	}

	public boolean isUserExist(User user) {
		return findByName(user.getUsername()) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intertecintl.service.UserService#checkUsername(com.intertecintl.model
	 * .User)
	 */
	@Override
	public Result<Boolean, List<String>> checkUsername(User user) {
		Result<Boolean, List<String>> result;
		// check if empty or size at least 6 chars
		String name = user.getUsername();
		if ((user != null) && (name!=null)&&(!name.isEmpty()) && (name.length() > 6)) {
			// obtains all Restricted Word List, to improve performance
			Iterable<RestrictedWord> dictionary = wordRepository.findAll();
			if ((!isUserExist(user))&&(!hasRestrictedWord(user, dictionary))) {	
				result = new Result<Boolean, List<String>>(true);
			} else {
				result = new Result<Boolean, List<String>>(false);
				result.setValues(generateSuggestedNames(user, dictionary));
			}
		} else {
			result = new Result<Boolean, List<String>>(false);
		}

		return result;
	}

	/**
	 * @param user
	 *            user with the given name
	 * @param dictionary
	 *            dictionary of RestrictedWords
	 * @return Generates suggested names based on the name of the given user
	 */
	private List<String> generateSuggestedNames(User user, Iterable<RestrictedWord> dictionary) {
		// if the given user name contains a restricted word, it gets completely
		// regenerated using the letters that it contained
		boolean regenerate = hasRestrictedWord(user, dictionary);
		String suggested = "";
		Integer badSuggestions = 0;
		Integer counter = 0;

		Collection<String> orderList = new TreeSet<String>(Collator.getInstance());

		while ((counter != 14) || (badSuggestions == 3)) {
			if (regenerate) {
				// Added the counter to the sourceString to obtain alternatives
				// in the case of a username with the same letter ejm: "iiiiii"
				// the suggested alternatives will change all the username but
				// using the same letters and the counter number
				suggested = UserUtil.randomString(user.getUsername() + counter, user.getUsername().length());
			} else {
				// Suggested alternative will have a dot at the end of the user
				// name with 3 random chars from the user name or counter number
				suggested = user.getUsername() + "." + UserUtil.randomString(user.getUsername() + counter, 3);
			}

			// The suggestion is check to see if it didn't regenerate a
			// restricted name or if it is used
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
	public boolean isValidSuggestion(String suggested, Iterable<RestrictedWord> dictionary) {
		User user = new User();
		user.setUsername(suggested);
		if (!hasRestrictedWord(user, dictionary)) {
			if (!isUserExist(user)) {
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

}
