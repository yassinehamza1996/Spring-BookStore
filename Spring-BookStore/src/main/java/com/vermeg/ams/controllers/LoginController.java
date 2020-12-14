package com.vermeg.ams.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vermeg.ams.services.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public ModelAndView accueil() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String saymyname = authentication.getName();
		
		modelAndView.addObject("saymyname", saymyname);
		modelAndView.setViewName("home");
		return modelAndView;
	}
	@GetMapping("/aboutus")
	public String aboutus(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String saymyname = authentication.getName();
		
		model.addAttribute("saymyname", saymyname);
		return "about-us";
	}
	
	/*
	 * @RequestMapping(value = "/registration", method = RequestMethod.GET) public
	 * ModelAndView registration() { ModelAndView modelAndView = new ModelAndView();
	 * User user = new User(); modelAndView.addObject("user", user);
	 * modelAndView.setViewName("registration"); return modelAndView; }
	 * 
	 * @RequestMapping(value = "/registration", method = RequestMethod.POST) public
	 * ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
	 * ModelAndView modelAndView = new ModelAndView(); User userExists =
	 * userService.findUserByEmail(user.getEmail()); if (userExists != null) {
	 * bindingResult.rejectValue("email", "error.user",
	 * "There is already a user registered with the email provided"); } if
	 * (bindingResult.hasErrors()) { modelAndView.setViewName("registration"); }
	 * else { userService.saveUser(user); modelAndView.addObject("successMessage",
	 * "User has been registered successfully"); modelAndView.addObject("user", new
	 * User()); modelAndView.setViewName("registration"); } return modelAndView; }
	 */
	/*
	 * @RequestMapping(value="/admin/home", method = RequestMethod.GET) public
	 * ModelAndView home(){ ModelAndView modelAndView = new ModelAndView();
	 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	 * User user = userService.findUserByEmail(auth.getName());
	 * modelAndView.addObject("userName", "Welcome " + user.getName() + " " +
	 * user.getLastName() + " (" + user.getEmail() + ")");
	 * modelAndView.addObject("adminMessage","Content Available Only for Users with
	 * Admin Role"); modelAndView.setViewName("admin/home"); return modelAndView; }
	 */

	@GetMapping("/403")
	public String error403() {
		return "/error/403";
	}
	@GetMapping("/404")
	public String geterror() {
		
		return "404";
	}
}
