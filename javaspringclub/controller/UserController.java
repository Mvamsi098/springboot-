package com.javaspringclub.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.javaspringclub.entity.User;
import com.javaspringclub.service.UserService;

@Controller
public class UserController {
	
	
	// Constructor based Dependency Injection
	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView hello(HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home");
		return mv;
	}

	// Get All Users
	@RequestMapping(value = "/allUsers", method = RequestMethod.POST)
	public ModelAndView displayAllUser() {
		System.out.println("User Page Requested : All Users");
		ModelAndView mv = new ModelAndView();
		List<User> userList = userService.getAllUsers();
		mv.addObject("userList", userList);
		mv.setViewName("allUsers");
		return mv;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView displayNewUserForm() {
		ModelAndView mv = new ModelAndView("addUser");
		mv.addObject("headerMessage", "Add User Details");
		mv.addObject("user", new User());
		return mv;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ModelAndView saveNewUser(@ModelAttribute User user, BindingResult result) {
		ModelAndView mv = new ModelAndView("redirect:/home");

		if (result.hasErrors()) {
			return new ModelAndView("error");
		}
		boolean isAdded = userService.saveUser(user);
		if (isAdded) {
			mv.addObject("message", "New user successfully added");
		} else {
			return new ModelAndView("error");
		}

		return mv;
	}

	@RequestMapping(value = "/editUser/{id}", method = RequestMethod.GET)
	public ModelAndView displayEditUserForm(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("/editUser");
		User user = userService.getUserById(id);
		mv.addObject("headerMessage", "Edit User Details");
		mv.addObject("user", user);
		return mv;
	}

	@RequestMapping(value = "/editUser/{id}", method = RequestMethod.POST)
	public ModelAndView saveEditedUser(@ModelAttribute User user, BindingResult result) {
		ModelAndView mv = new ModelAndView("redirect:/home");

		if (result.hasErrors()) {
			System.out.println(result.toString());
			return new ModelAndView("error");
		}
		boolean isSaved = userService.saveUser(user);
		if (!isSaved) {

			return new ModelAndView("error");
		}

		return mv;
	}

	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
	public ModelAndView deleteUserById(@PathVariable Long id) {
		boolean isDeleted = userService.deleteUserById(id);
		System.out.println("User deletion respone: " + isDeleted);
		ModelAndView mv = new ModelAndView("redirect:/home");
		return mv;

	}

}
