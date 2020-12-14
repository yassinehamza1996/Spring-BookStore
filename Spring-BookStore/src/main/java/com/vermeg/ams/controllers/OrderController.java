package com.vermeg.ams.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.vermeg.ams.entities.Book;
import com.vermeg.ams.entities.Client;
import com.vermeg.ams.entities.Order;
import com.vermeg.ams.repositories.BookRepository;
import com.vermeg.ams.repositories.ClientRepository;
import com.vermeg.ams.repositories.OrderRepository;

@Controller
@RequestMapping("/order/")
public class OrderController {

	private OrderRepository orderRepository;
	private ClientRepository clientrepository;
	private BookRepository bookrepository;

	@Autowired
	public OrderController(OrderRepository orderRepository, ClientRepository clientrepository,
			BookRepository bookrepository) {
		this.orderRepository = orderRepository;
		this.clientrepository = clientrepository;
		this.bookrepository = bookrepository;
	}

	@GetMapping("list")
	// @ResponseBody
	public String listorders(Model model) {
		List<Order> com = (List<Order>) orderRepository.findAll();
		if (com.size() == 0)
			com = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String saymyname = authentication.getName();
		
		model.addAttribute("saymyname", saymyname);
		model.addAttribute("orders", com);
		return "order/listOrders";
		// System.out.println(lp);
		// return "Nombre de fournisseur = " + lp.size();
	}
	
	@GetMapping("listall")
	public String listclient(Model m) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String saymyname = authentication.getName();
		m.addAttribute("saymyname", saymyname);
		Client c = clientrepository.findByEmail(saymyname);
		List<Order> myorder = orderRepository.getOrderbyclient(c.getIdClient());
		m.addAttribute("orders", myorder);
			return "order/listclient";	
	}
	
	@GetMapping("add")
	public String showAddorderForm(Model model) {
		Order order = new Order();// object dont la valeur des attributs par defaut
		LocalDate lt = LocalDate.now();
		order.setOrderDate(lt);
		model.addAttribute("order", order);
		List<Client> cl = (List<Client>) clientrepository.findAll();
		model.addAttribute("clients", cl);
		List<Book> lb = (List<Book>) bookrepository.findAll();
		model.addAttribute("books", lb);
		return "order/addorder";
	}

	@PostMapping("add")
	// @ResponseBody
	public String addOrder(@Valid Order order, @RequestParam("checkid") List<Integer> listids,
			@RequestParam("idclient") int idclient) {
		double prices =0.0;
		Client client = clientrepository.findById(idclient)
				.orElseThrow(() -> new IllegalArgumentException("invalid client"));
		order.setClient(client);

		for (int i = 0; i < listids.size(); i++) {

			Book b = bookrepository.findById(listids.get(i))
					.orElseThrow(() -> new IllegalArgumentException("invalid id "));
			prices+=b.getPrice();

			//order.addmybooks(b);
		}
		order.setPrice(prices);
		orderRepository.save(order);
		return "redirect:list";
	}
	
	@GetMapping("showdetails/{id}")
	public String showdetails(@PathVariable("id")int id,Model m) {
		
		Order o = orderRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"+id));
		//List<Book> mybooks = o.getmybooks();
		System.out.println("------------------------------------------------------------------------");
		//System.out.println(mybooks.size()+mybooks.toString());
		System.out.println("**************************************************************************");
		//m.addAttribute("mybooks", mybooks);
		return"order/details";
	}
	
	
	@GetMapping("delete/{id}")
	public String deleteorder(@PathVariable("id") int id, Model model) {
		
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
		
		orderRepository.delete(order);
		
		return "redirect:../list";
	}

	@GetMapping("edit/{id}")
	public String showorderFormToUpdate(@PathVariable("id") int id, Model model) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));

		model.addAttribute("order", order);
		return "order/updateorder";
	}

	@PostMapping("update")
	public String updateorder(@Valid Order order, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "order/updateorder";
		}
		orderRepository.save(order);
		return "redirect:list";
	}
}
