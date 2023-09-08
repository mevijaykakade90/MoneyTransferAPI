package com.moneyapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapi.exception.UserFoundException;
import com.moneyapi.exception.UserNotExistException;
import com.moneyapi.model.User;
import com.moneyapi.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Handles a POST request to create a new user.
	 *
	 * @param user The user object to be created, obtained from the request body.
	 * @return ResponseEntity containing a success message if user is created
	 *         successfully, or an error message with corresponding HTTP status if a
	 *         UserFoundException occurs.
	 */

	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@RequestBody @Valid User user) {
		try {
			userService.createUser(user);
			return new ResponseEntity<>("User sucessfully created.", HttpStatus.CREATED);
		} catch (UserFoundException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}

	}

	/**
	 * Retrieves a user by their unique ID.
	 *
	 * @param userId The unique identifier of the user to be retrieved.
	 * @return ResponseEntity containing the user information if found, or an error
	 *         message if not found.
	 */

	@GetMapping("/getUseById/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable long userId) {
		try {
			User user = userService.getUserById(userId);
			return new ResponseEntity<>(user, HttpStatus.FOUND);
		} catch (UserNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves a list of all users from the system.
	 *
	 * @return A list of User objects representing all users in the system.
	 */

	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {
		return userService.getAllUsers();

	}

	/**
	 * Updates an existing user's information.
	 *
	 * This method handles HTTP PUT requests to update a user's details.
	 *
	 * @param user object containing the updated information.
	 * @return ResponseEntity with a success message and HTTP status OK (200) if the
	 *         user is successfully updated, or ResponseEntity with an error message
	 *         and the corresponding HTTP status if the user does not exist.
	 */

	@PutMapping("/updateUser")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		try {
			userService.updateUser(user);
			return new ResponseEntity<>("User is successfully updated.", HttpStatus.OK);
		} catch (UserNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}

	/**
	 * Handles the DELETE request to delete a user by their unique ID.
	 *
	 * @param userId The unique identifier of the user to be deleted.
	 * @return ResponseEntity with a success message if the user is successfully
	 *         deleted, or an error message with an appropriate HTTP status if the
	 *         user does not exist.
	 */
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable long userId) {
		try {
			userService.deleteUser(userId);
			return new ResponseEntity<>("User is successfully deleted.", HttpStatus.OK);
		} catch (UserNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}

}
