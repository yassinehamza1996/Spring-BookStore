package com.vermeg.ams.RestControllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vermeg.ams.Exceptions.ResourceNotFoundException;
import com.vermeg.ams.entities.Book;
import com.vermeg.ams.entities.Order;
import com.vermeg.ams.repositories.BookRepository;
import com.vermeg.ams.repositories.ClientRepository;
import com.vermeg.ams.repositories.OrderRepository;

@RestController
@RequestMapping({ "/orders/" })
public class RestOrderController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ClientRepository clientrepository;

	@Autowired
	private BookRepository bookrepository;

	@GetMapping("list")
	public List<Order> getallorders() {
		return (List<Order>) orderRepository.findAll();
	}

	@PostMapping("add/{idClient}")
	public Order createCommand(@PathVariable(value = "idClient") int clientId, @Valid @RequestBody Order command) {
		Order command1 = clientrepository.findById(clientId).map(client -> {
			command.setClient(client);
			return orderRepository.save(command);
		}).orElseThrow(() -> new ResourceNotFoundException("clientId" + clientId + " not found"));

		return command1;
	}

//	@PostMapping("add/{idClient}/{idBook}")
//	public Order addOrder(@PathVariable("idClient") int idc, @RequestBody Order order,
//
//			@PathVariable("idBook") List<Integer> idBook) {
//
//		return clientrepository.findById(idc).map(b -> {
//			order.setClient(b);
//			for (int i = 0; i < idBook.size(); i++) {
//				Book bk = bookrepository.findById(idBook.get(i))
//						.orElseThrow(() -> new IllegalArgumentException("Invalid Book"));
//				order.addmybooks(bk);
//			}
//			return orderRepository.save(order);
//		}).orElseThrow(() -> new ResourceNotFoundException("Client id " + idc + " not found"));
//
//	}

//	@PutMapping("edit/{idOrder}/{idBook}")
//	public Order editmyorder(@PathVariable("idOrder") int idorder, @PathVariable("idBook") List<Integer> idBook,
//			@Valid @RequestBody Order order) {
//
//		List<Book> thelist = new ArrayList<>();
//		return orderRepository.findById(idorder).map(myorder -> {
//
//			for (int i = 0; i < idBook.size(); i++) {
//
//				Book b = bookrepository.findById(idBook.get(i))
//						.orElseThrow(() -> new IllegalArgumentException("Invalid id book"));
//				thelist.add(b);
//			}
//			myorder.Setmybooks(thelist);
//			myorder.setOrderDate(order.getOrderDate());
//			myorder.setPrice(order.getPrice());
//			return orderRepository.save(myorder);
//		}).orElseThrow(() -> new ResourceNotFoundException("Order id" + idorder + "not found"));
//
//	}

	@DeleteMapping("{idOrder}")
	public int delete(@PathVariable("idOrder") int idorder) {

		Order myorder = orderRepository.findById(idorder)
				.orElseThrow(() -> new IllegalArgumentException("invalid id" + idorder));
		orderRepository.delete(myorder);

		return 1;
	}

}
