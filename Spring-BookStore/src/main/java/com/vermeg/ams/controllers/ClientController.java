package com.vermeg.ams.controllers;

import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vermeg.ams.entities.Client;
import com.vermeg.ams.entities.Role;
import com.vermeg.ams.repositories.ClientRepository;
import com.vermeg.ams.repositories.RoleRepository;
import com.vermeg.ams.services.UserService;

@Controller
@RequestMapping("/client/")
public class ClientController {

	@Autowired
	private UserService userService;
	@Autowired
	private JavaMailSender javaMailSender;

	private ClientRepository clientrepository;
	private RoleRepository rolerepository;
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";

	@Autowired
	public ClientController(ClientRepository clientrepository, RoleRepository rolerepository) {
		this.clientrepository = clientrepository;
		this.rolerepository = rolerepository;
	}

	@GetMapping("list")
	public String listclients(Model model) {
		List<Client> liste = (List<Client>) clientrepository.findAll();
		if (liste.size() == 0)
			liste = null;
		model.addAttribute("clients", liste);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String saymyname = authentication.getName();
		
		model.addAttribute("saymyname", saymyname);
		return "client/listclients";
	}

	@GetMapping("add")
	public String showaddform(Model model) {
		Client client = new Client();
		model.addAttribute("client", client);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String saymyname = authentication.getName();
		
		model.addAttribute("saymyname", saymyname);
		return "client/addclient";
	}

	@GetMapping("show/{id}")
	public String ShowclientDetails(@PathVariable("id") int id, Model m) {
		Client b = clientrepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("invalid client id" + id));
		m.addAttribute("client", b);
		return "client/showclient";
	}

	@GetMapping("disable/{id}/{state}")
	// @ResponseBody
	public String disableUserAcount(@PathVariable("id") int id, @PathVariable("state") int state) {

		Client user = clientrepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
		user.setActive(0);
		sendEmail(user.getMail(), false);
		clientrepository.save(user);

		return "redirect:../../list";
	}

	@GetMapping("enable/{id}/{state}")
	// @ResponseBody
	public String enableUserAcount(@PathVariable("id") int id, @PathVariable("state") int state) {

		Client user = clientrepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
		user.setActive(1);
		sendEmail(user.getMail(), true);
		clientrepository.save(user);

		return "redirect:../../list";
	}

	@PostMapping("add")
//	public String addclient(@Valid Client client, BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			return "client/addclient";
//		}
//		
//		
//		clientrepository.save(client);
//		return "redirect:list";
//
//	
//
//	}
	public ModelAndView createNewUser(@Valid Client user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Client userExists = userService.findUserByEmail(user.getMail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("client/addClient");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new Client());
			modelAndView.setViewName("client/addClient");
		}
		return modelAndView;
	}

	@GetMapping("delete/{id}")
	public String deleteclient(@PathVariable("id") int id, Model model) {

		Client client = clientrepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));

		clientrepository.delete(client);

		return "redirect:../list";
	}

	@GetMapping("edit/{id}")
	public String showclientFormToUpdate(@PathVariable("id") int id, Model model) {
		Client client = clientrepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
		model.addAttribute("client", client);
		List<Role> r = (List<Role>) rolerepository.findAll();
		model.addAttribute("r", r);

		return "client/updateclient";
	}

	@PostMapping("update")
	public String updateclient(@Valid Client client, BindingResult result, Model model, @RequestParam("r") String r) {
		if (result.hasErrors()) {
			return "client/updateclient";
		}
		Role rr = rolerepository.findByRole(r);
		HashSet<Role> set = new HashSet<Role>();
		set.add(rr);
		client.setRole(set);
		Client currentclient = clientrepository.findById(client.getIdClient())
				.orElseThrow(() -> new IllegalArgumentException("Invalid id client"));
		client.setActive(currentclient.getActive());
		clientrepository.save(client);
		return "redirect:list";
	}

	void sendEmail(String email, boolean state) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		if (state == true) {
			msg.setSubject("Account Has Been Activated");
			msg.setText("Hello, Your account has been activated. " + "You can log in : http://127.0.0.1:81/login"
					+ " \n Best Regards!");
		} else {
			msg.setSubject("Account Has Been disactivated");
			msg.setText("Hello, Your account has been disactivated.");
		}
		javaMailSender.send(msg);
	}
}
