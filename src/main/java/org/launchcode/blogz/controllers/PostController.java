package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		// get request parameters
		String title = request.getParameter("title");
		String body = request.getParameter("body");
	
		// validate parameters
		boolean has_error = false;
		if (title == null || title == "") {
			has_error = true;
			model.addAttribute("error1", "Please provide a post title");
		}
		else {
			model.addAttribute("title", title);
		}
		if (body == null || body == ""){
			has_error = true;
			model.addAttribute("error2", "Please add some text to your post");
		}
		else {
			model.addAttribute("body", body);
		}
		if (has_error == true) {
			return "newpost";
		}
		// if valid, create new Post
		else {
			HttpSession thisSession = request.getSession();
			User user1 = getUserFromSession(thisSession);
			Post post = new Post(title, body, user1);
			postDao.save(post);
			String username = user1.getUsername();
			int postId = post.getUid();
			model.addAttribute("post", post);
			
			return "redirect:" + username + "/" + postId; // TODO - this redirect should go to the new post's page 
		}
		// if not, send back to form with error message 		
	}
	
	// will handle requests such as '/blog/james/5'
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		// get the given post
		Post post1 = postDao.findByUid(uid);
		// pass the post into the template
		model.addAttribute("post", post1);
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		
		// get all posts from user
		User user1 = userDao.findByUsername(username);
		//int userUid = user1.getUid();
		List<Post> userPosts = user1.getPosts();
		// pass the posts into the template
		model.addAttribute("posts", userPosts);
		
		return "blog";
	}
	
}
