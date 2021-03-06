package com.intertecintl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.intertecintl.model.Result;
import com.intertecintl.model.User;
import com.intertecintl.service.UserService;

/**
 * @author Gustavo
 *
 */
@RestController
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * Request all users
	 * 
	 * @return ResponseEntity<List<User>> with all the users in the repository
	 */
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);// or
																			// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	/**
	 * Request a User by its Id
	 * 
	 * @param id
	 * @return ResponseEntity<User> associated to the given id
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") long id) {
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Create a new user into the repository with the given user
	 * 
	 * @param user
	 * @param ucBuilder
	 * @return ResponseEntity with the status of the transaction
	 */
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<List<String>> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		Result<Boolean,List<String>> result = userService.checkUsername(user);
		if (!result.getKey()) {
			List<String> suggestedWords = result.getValues();			
			return new ResponseEntity<List<String>>(suggestedWords,HttpStatus.CONFLICT);
		}

		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());		
		
		return new ResponseEntity<List<String>>(headers, HttpStatus.CREATED);
	}
	
	
	/**
	 * Delete the user specified by the id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);
 
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
 
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}
