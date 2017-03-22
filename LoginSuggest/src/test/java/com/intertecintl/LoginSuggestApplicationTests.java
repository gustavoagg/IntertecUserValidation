package com.intertecintl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.intertecintl.model.RestrictedWord;
import com.intertecintl.model.Result;
import com.intertecintl.model.User;
import com.intertecintl.service.RestrictedWordService;
import com.intertecintl.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginSuggestApplicationTests {
	@Autowired
	UserService userService;

	@Autowired
	RestrictedWordService restrictedService;

	/**
	 * Ideal case with no conflict
	 */
	@Test
	public void checkingNewUser() {
		Result<Boolean, List<String>> result;
		User user = new User();
		user.setUsername("Sofia.678");

		result = userService.checkUsername(user);
		assertTrue(result.getKey());
		assertNull(result.getValues());

		userService.saveUser(user);
	}

	/**
	 * Ideal case with existing conflict
	 */
	@Test
	public void checkingExistingUser() {
		Result<Boolean, List<String>> result;
		User user = new User();
		user.setUsername("Irene.345");

		result = userService.checkUsername(user);
		assertTrue(result.getKey());
		assertNull(result.getValues());

		userService.saveUser(user);

		result = userService.checkUsername(user);
		assertFalse(result.getKey());
		assertTrue(result.getValues().size() == 14);

	}

	/**
	 * Border Cases
	 */
	@Test
	public void checkingWithSpecialNames() {
		Result<Boolean, List<String>> result;
		User user = new User(); // case empty
		result = userService.checkUsername(user);
		assertFalse(result.getKey());
		assertNull(result.getValues());

		user.setUsername("aBa"); // case Too small
		result = userService.checkUsername(user);
		assertFalse(result.getKey());
		assertNull(result.getValues());

		user.setUsername("./&%$/__-"); // case symbols
		result = userService.checkUsername(user);
		assertTrue(result.getKey());
		assertNull(result.getValues());

		user.setUsername(null); // case Null
		result = userService.checkUsername(user);
		assertFalse(result.getKey());
		assertNull(result.getValues());

	}

	/**
	 * case with restricted Words
	 */
	@Test
	public void checkingWithRestrictedWords() {
		//Adding restricted word
		RestrictedWord word = new RestrictedWord();
		word.setWord("drunk");
		restrictedService.saveRestrictedWord(word);
		
		Result<Boolean, List<String>> result;
		User user = new User();
		user.setUsername("IwasDrunk");
		
		Iterable<RestrictedWord> dictionary = restrictedService.findAllRestrictedWord();
		result = userService.checkUsername(user);
		assertFalse(result.getKey());
		assertTrue(result.getValues().size() == 14);

		List<String> suggested = result.getValues();
		for (int i = 0; i < suggested.size()-1; i++) {
			assertTrue(userService.isValidSuggestion(suggested.get(i), dictionary));
		}
	}

	/**
	 * remove all the used data from database
	 */
	@After
	public void cleanData() {
		// Data from 1st Test
		User user = userService.findByName("Sofia.678");
		if (user != null && user.getId() != 0)
			userService.deleteUserById(user.getId());

		// Data from 2nd Test
		user = null;
		user = userService.findByName("Irene.345");
		if (user != null && user.getId() != 0)
			userService.deleteUserById(user.getId());

		// Data from 3nd Test
		RestrictedWord word = new RestrictedWord();
		word = restrictedService.findByWord("drunk");
		if (word != null && word.getId() != 0)
			restrictedService.deleteRestrictedWordById(word.getId());
	}
}
