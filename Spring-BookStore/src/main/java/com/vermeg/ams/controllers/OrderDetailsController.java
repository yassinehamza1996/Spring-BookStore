package com.vermeg.ams.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vermeg.ams.entities.Book;
import com.vermeg.ams.entities.Client;
import com.vermeg.ams.entities.Order;
import com.vermeg.ams.entities.Order_Details;
import com.vermeg.ams.repositories.BookRepository;
import com.vermeg.ams.repositories.ClientRepository;
import com.vermeg.ams.repositories.OrderDetailsRepository;
import com.vermeg.ams.repositories.OrderRepository;

@RequestMapping("/orderdetails/")
@Controller
public class OrderDetailsController {

	private BookRepository bookrepository;
	private ClientRepository clientrepository;
	private OrderDetailsRepository orderdetailsrepository;
	private OrderRepository orderrepository;

	@Autowired
	public OrderDetailsController(BookRepository bookrepository, ClientRepository clientrepository,
			OrderDetailsRepository orderdetailsrepository, OrderRepository orderrepository) {
		this.bookrepository = bookrepository;
		this.clientrepository = clientrepository;
		this.orderdetailsrepository = orderdetailsrepository;
		this.orderrepository = orderrepository;

	}

	@GetMapping("add")
	public String showAddorderForm(Model model) {
		Order order = new Order();// object dont la valeur des attributs par defaut
		LocalDate lt = LocalDate.now();
		order.setOrderDate(lt);
		model.addAttribute("order", order);
		
		List<Book> lb = (List<Book>) bookrepository.findAll();
		model.addAttribute("books", lb);
		return "order/addorder";
	}

	@PostMapping("addorder")
	public String addOrder(@Valid Order order, @RequestParam("quantite") List<Integer> quantity,
			@RequestParam("checkid") List<Integer> idbooks) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String saymyname = authentication.getName();
		
		
		
		Client c = clientrepository.findByEmail(saymyname);
	
		// affect a client to his order
		order.setClient(c);
		// save an order
		Order savedorder = orderrepository.save(order);
		// initialize total price of the order
		double myprice = 0.0;
		// iterate list of books and insert them to the valid orders
		for (int i = 0; i < idbooks.size(); i++) {
			Book mybook = bookrepository.findById(idbooks.get(i))
					.orElseThrow(() -> new IllegalArgumentException("invalid book id"));
			myprice += quantity.get(i)* mybook.getPrice();
			Order_Details o = new Order_Details(mybook.getPrice(), quantity.get(i), mybook, savedorder);

			orderdetailsrepository.save(o);
		}
		savedorder.setPrice(myprice);
		orderrepository.save(savedorder);
		return "redirect:list";
	}

	@GetMapping("list")
	// @ResponseBody
	public String listorders(Model model) {
		List<Order> com = (List<Order>) orderrepository.findAll();
		if (com.size() == 0)
			com = null;
		model.addAttribute("orders", com);
		return "order/listOrders";
		// System.out.println(lp);
		// return "Nombre de fournisseur = " + lp.size();
	}

	@GetMapping("showdetails/{id}")
	public String showdetails(@PathVariable("id") int id, Model m) {

		Order o = orderrepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID" + id));
		List<Order_Details> myorders = orderdetailsrepository.booksfindAllById(o);
//		List<Book> mybooks = new ArrayList<>();
//		for (int i = 0; i < myorders.size(); i++) {
//			Book b = bookrepository.findById(myorders.get(i).getBooks().getIdBook())
//					.orElseThrow(() -> new IllegalArgumentException("invalid book id"));
//			mybooks.add(b);
//		}
//		m.addAttribute("mybooks", mybooks);
		m.addAttribute("myorders", myorders);
		return "order/details";
	}

}
