package com.moneyapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.moneyapi.constant.ErrorCode;
import com.moneyapi.dao.UserRepository;
import com.moneyapi.exception.UserFoundException;
import com.moneyapi.exception.UserNotExistException;
import com.moneyapi.model.User;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void createUser(User user) throws UserFoundException {
		User userObj = userRepository.findById(user.getUserId() == null ? 0 : user.getUserId()).orElse(null);
		if (userObj == null)
			userRepository.save(user);
		else
			throw new UserFoundException("User with id : " + user.getUserId() + " is exists.", ErrorCode.USER_ERROR,
					HttpStatus.FOUND);
	}

	@Transactional
	public User getUserById(long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotExistException("User with id : " + userId + " is not exists.",
						ErrorCode.USER_ERROR, HttpStatus.NOT_FOUND));
	}

	@Transactional
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public void updateUser(User user) {

		User oldUser = getUserById(user.getUserId());

		oldUser.setName(user.getName());
		oldUser.setBalance(user.getBalance());

		userRepository.save(oldUser);

	}

	@Transactional
	public void deleteUser(long userId) {
		userRepository.deleteById(getUserById(userId).getUserId());
	}

}
