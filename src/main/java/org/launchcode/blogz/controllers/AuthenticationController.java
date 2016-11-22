package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// TODO - implement signup
		// get parameters from request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		
		// validate parameters (username, password, verify)
		boolean has_error = false;
		if (!User.isValidUsername(username)) {
			has_error = true;
			model.addAttribute("username_error", "Invalid Username");
		} else {
			model.addAttribute("username", username);
		}
		if (!User.isValidPassword(password)) {
			has_error = true;
			model.addAttribute("password_error", "Invalid password");
		}
		if (!password.equals(verify)) {
			has_error = true;
			model.addAttribute("verify_error", "Passwords do not match");
		} 

		// if they validate, create a new user and store in the session
		if (has_error == true) {
			return "signup";
		}
		else {
		
			HttpSession thisSession = request.getSession();
			
			User user1 = new User(username, password);
			userDao.save(user1);
			setUserInSession(thisSession, user1);
		
			return "redirect:blog/newpost";
		}		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO - implement login
		// get parameters from request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// get user by their username
		User user1 = userDao.findByUsername(username);
		
		if(user1 == null){
			model.addAttribute("error", "Username Not Found");
			return "login";
		}
		// check that password is correct...
		if(!user1.isMatchingPassword(password)){
			model.addAttribute("error", "Incorrect Password");
			return "login";
		}
		
		// if so, then log them in (i.e. setting the user in the session)
		else {
			
			HttpSession thisSession = request.getSession();
			setUserInSession(thisSession, user1);
			
			return "redirect:blog/newpost";
		}
	}
	
	// Try and show who you are currently logged in as?
	
//	@RequestMapping(value = "/", method = RequestMethod.POST)
//	public String currentUser(HttpServletRequest request, Model model){
//		HttpSession thisSession = request.getSession();
//		User user1 = getUserFromSession(thisSession);
//		String username = user1.getUsername();
//		model.addAttribute("username", username);
//		
//		return "template";
//	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
