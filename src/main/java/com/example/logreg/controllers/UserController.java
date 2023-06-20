package com.example.logreg.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.logreg.models.LoginUser;
import com.example.logreg.models.User;
import com.example.logreg.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	@Autowired 
	private UserService userService;

	@GetMapping("/")
	public String logreg(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "logreg.jsp";
	}
	
	@PostMapping("/register")
	public String processRegister(@Valid @ModelAttribute("newUser") User newUser, 
			BindingResult result, Model model, HttpSession session) {
        // 1. get the registeredUser by calling register in service and make use of the binding result
		User registeredUser = userService.register(newUser, result);
        // 2. check for result errors
        // 2.1 if there are errors, put the missing model in and return jsp
		if(result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "logreg.jsp";
		} else {
			session.setAttribute("userId", registeredUser.getId());
			session.setAttribute("userName", registeredUser.getUserName());
			return "redirect:/dashboard";
		}
        // 2.2 if no errors, set userId in session and redirect
	}
	
	@PostMapping("/login")
	public String processLogin(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
			BindingResult result, Model model, HttpSession session) {
         // 1. get the user by calling login in service and make use of the binding result
		User loginUser = userService.login(newLogin, result);
        // 2. check for result errors
        // 2.1 if there are errors, put the missing model in and return jsp
		if(result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "logreg.jsp";
		} else {
			session.setAttribute("userId", loginUser.getId());
			session.setAttribute("userName", loginUser.getUserName());
			return "redirect:/dashboard";
		}
        // 2.2 if no errors, get the user info from user and set userId in the session
        }
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/dashboard")
	public String renderDashboard(HttpSession session) {
		if(session.getAttribute("userId") == null) {
			session.invalidate();
			return "redirect:/";
		}
		return "dashboard.jsp";
	}
}
