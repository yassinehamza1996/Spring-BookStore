package com.vermeg.ams.controllers;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vermeg.ams.entities.Client;
import com.vermeg.ams.repositories.ClientRepository;

@Controller
@RequestMapping("/client/")
public class ClientController {
	private ClientRepository clientrepository;
	public static String uploadDirectory =
			System.getProperty("user.dir")+"/src/main/resources/static/uploads";
	@Autowired
	public ClientController(ClientRepository clientrepository) {
		this.clientrepository = clientrepository;
	}

	@GetMapping("list")
	public String listclients(Model model) {
		List<Client> liste = (List<Client>) clientrepository.findAll();
		if (liste.size() == 0)
			liste = null;
		model.addAttribute("clients", liste);
		return "client/listclients";
	}

	@GetMapping("add")
	public String showaddform(Model model) {
		Client client = new Client();
		model.addAttribute("client", client);
		return "client/addclient";
	}
	@GetMapping("show/{id}")
	public String ShowclientDetails(@PathVariable("id")int id,Model m) {
		Client b = clientrepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("invalid client id"+id));
		m.addAttribute("client", b);
		return"client/showclient";
	}

	@PostMapping("add")
	public String addclient(@Valid Client client, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "client/addclient";
		}
		
		clientrepository.save(client);
		return "redirect:list";

	

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
		return "client/updateclient";
	}

	@PostMapping("update")
	public String updateclient(@Valid Client client, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "client/updateclient";
		}
			
		clientrepository.save(client);
		return "redirect:list";
	}
}
