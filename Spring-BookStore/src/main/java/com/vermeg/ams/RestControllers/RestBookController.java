package com.vermeg.ams.RestControllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.vermeg.ams.repositories.BookRepository;

@RestController
@RequestMapping({"/bookrest/"})
@CrossOrigin(origins="*")
public class RestBookController {

	@Autowired
	private BookRepository bookrepository;
	@Autowired
	OrderService orderservice;
	
	@GetMapping("list")
	public List<Book> getBook(){
		List<Book> l = (List<Book>)bookrepository.findAll();
				return l;
	}
	@PostMapping("add")
	public Book addbook(@Valid @RequestBody Book book) {
		
		return bookrepository.save(book);
		
	}
	@PutMapping("{id}")
	public Book editbook(@PathVariable int id,@Valid @RequestBody Book book) {
		
		return bookrepository.findById(id).map(b -> {
			b.setTitle(book.getTitle());
			b.setPrice(book.getPrice());
			b.setAuthor(book.getAuthor());
			b.setReleaseDate(book.getReleaseDate());
			return bookrepository.save(b);
		}).orElseThrow(() -> new ResourceNotFoundException("Book id " + id + " not found"));
			
		}
	@DeleteMapping("/{bookid}")
	public Book deleteBook(@PathVariable int bookid) {

		
		Book book = bookrepository.findById(bookid)
				.orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + bookid));
		bookrepository.delete(book);
		return book;
	   
	}
	@GetMapping("/count/{idBook}")
	public double getmycount(@PathVariable("idBook") List<Integer> lid) {
		
		List<Book> lb = (List<Book>) bookrepository.findAllById(lid);
		
		return orderservice.ComputeTotalPrice(lb);
	}
	
	
}
