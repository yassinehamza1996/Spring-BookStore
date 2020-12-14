package com.vermeg.ams.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vermeg.ams.entities.Role;
import com.vermeg.ams.repositories.RoleRepository;

@Controller
@RequestMapping("/role/")
public class RoleController {

private RoleRepository	rolerepository;

	 @Autowired
	public RoleController(RoleRepository rolerepository) {
		this.rolerepository=rolerepository;
	}
	 
	 @GetMapping("list")
	 public String listRoles(Model model) {

	 List<Role> roles = (List<Role>) rolerepository.findAll();
	 long nbr = rolerepository.count();
	 if(roles.size()==0)
	 roles = null;
	 model.addAttribute("roles", roles);
	 model.addAttribute("nbr", nbr);
	 return "role/listRoles";
	 }
	 
	 @GetMapping("add")
	 public String showAddRoleForm() {
	 //m.addAttribute("Role",new Role("Admin"));
	 return "role/addRole";
	 }

	 @PostMapping("add")
	 public String addRole(@RequestParam("role") String role) {

	 System.out.println(role);
	 Role r = new Role(role);
	 Role rSaved = rolerepository.save(r);
	 System.out.println("role = "+ rSaved);
	 return "redirect:list";
	 }
}
