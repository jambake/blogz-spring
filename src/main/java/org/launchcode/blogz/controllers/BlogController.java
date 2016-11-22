package org.launchcode.blogz.controllers;

import java.util.List;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController extends AbstractController {

	@RequestMapping(value = "/")
	public String index(Model model){
		
		// TODO - fetch users and pass to template
		List<User> allUsers = userDao.findAll();
		model.addAttribute("users", allUsers);
		
		return "index";
		
//		int numPosts = 0;
//		for (int i = 0; i < allUsers.size(); i++) {
//			User user = userDao.findByUid(i);
//			List<Post> userPosts = user.getPosts();
//			for (int j = 0; j < userPosts.size(); i++) {
//				numPosts++;
//			}
//			model.addAttribute("numPosts", numPosts);
//		}
		//model.addAttribute("numPosts", numPosts);
		
	}
	
	@RequestMapping(value = "/blog")
	public String blogIndex(Model model) {
		
		// TODO - fetch posts and pass to template
		List<Post> allPosts = postDao.findAll();
		model.addAttribute("posts", allPosts);
		
		return "blog";
	}
	
}
