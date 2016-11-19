package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.UserDao;
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
		User user = new User(username, password);
		
		HttpSession thisSession = request.getSession();	
		
		setUserInSession(thisSession, user);
		
		return "redirect:blog/newpost";
		}
		
		//Session thisSession = request.getSession(); (code that gets current session)
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
		// User user = UserDao.findByUid(user.getUid());
		
		// check that password is correct...
//		if(!User.isMatchingPassword(password)){
//
//		}
		// if so, then log them in (i.e. setting the user in the session)
		
		// Session thisSession = request.getSession(); (code that gets current session)
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
