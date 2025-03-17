package com.javaspringclub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaspringclub.entity.User;
import com.javaspringclub.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	// Implementing Constructor based DI

	@Autowired
	private UserRepository repository;

	@Override
	public List<User> getAllUsers() {
		List<User> list = new ArrayList<User>();
		repository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public User getUserById(Long id) {

		Optional<User> optionalObject = repository.findById(id);

		if (optionalObject.isPresent()) {

			return optionalObject.get();
		} else {

			return null;
		}

	}

	@Transactional
	@Override
	public boolean saveUser(User user) {
		try {
			repository.save(user);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Transactional
	@Override
	public boolean deleteUserById(Long id) {
		try {
			repository.deleteById(id);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

}
