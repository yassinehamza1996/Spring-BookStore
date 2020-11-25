package com.vermeg.ams.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vermeg.ams.entities.Client;
import com.vermeg.ams.entities.Order;
import com.vermeg.ams.repositories.ClientRepository;
import com.vermeg.ams.repositories.OrderRepository;

@Controller
@RequestMapping("/commande/")
public class CommandeController {
	
	private  OrderRepository commandeRepository ;
	private ClientRepository clientrepository;
	@Autowired
	public CommandeController(OrderRepository commandeRepository,ClientRepository clientrepository ) {
	this.commandeRepository  = commandeRepository ;
	this.clientrepository= clientrepository;
	}

	@GetMapping("list")
	// @ResponseBody
	public String listcommandes(Model model) {
		List<Order> com = (List<Order>) commandeRepository.findAll();
		if (com.size() == 0)
			com = null;
		model.addAttribute("commandes", com);
		return "commande/listcommande";
		// System.out.println(lp);
		// return "Nombre de fournisseur = " + lp.size();
	}

	@GetMapping("add")
	public String showAddcommandeForm(Model model) {
		Order commande = new Order();// object dont la valeur des attributs par defaut
		model.addAttribute("commande", commande);
		return "commande/addcommande";
	}

	@PostMapping("add")
	@ResponseBody
	public String addProvider(@Valid Order commande, BindingResult result) {
		if (result.hasErrors()) {
			return "commande/addcommande";
		}
		Client c = clientrepository.findById(1).orElseThrow(()-> new IllegalArgumentException());
		commande.setClient(c);
		commandeRepository.save(commande);
		return "redirect:list";
	}

	@GetMapping("delete/{id}")
	public String deletecommande(@PathVariable("id") int id, Model model) {
		// long id2 = 100L;
		Order commande = commandeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid commande Id:" + id));
		System.out.println("suite du programme...");
		commandeRepository.delete(commande);
		/*
		 * model.addAttribute("providers", providerRepository.findAll()); return
		 * "provider/listProviders";
		 */
		return "redirect:../list";
	}

	@GetMapping("edit/{id}")
	public String showcommandeFormToUpdate(@PathVariable("id") int id, Model model) {
		Order commande = commandeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid commande Id:" + id));

		model.addAttribute("commande", commande);
		return "commande/updatecommande";
	}

	@PostMapping("update")
	public String updatecommande(@Valid Order commande, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "commande/updatecommande";
		}
		commandeRepository.save(commande);
		return "redirect:list";
	}
}
