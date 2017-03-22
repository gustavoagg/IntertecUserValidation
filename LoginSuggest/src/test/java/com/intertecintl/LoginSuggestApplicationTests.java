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

import com.intertecintl.model.Result;
import com.intertecintl.model.User;
import com.intertecintl.repository.RestrictedWordRepository;
import com.intertecintl.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginSuggestApplicationTests {
	@Autowired
	UserService userService;

	@Autowired
	RestrictedWordRepository restrictedRepository;


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
		user = null;
		user = userService.findByName("iiiiiiiii");
		if (user != null && user.getId() != 0)
			userService.deleteUserById(user.getId());
	}
}
