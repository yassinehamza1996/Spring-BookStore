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

import com.vermeg.ams.entities.Book;
import com.vermeg.ams.repositories.BookRepository;

@Controller
@RequestMapping("/book/")
public class BookController {

	private BookRepository bookrepository;
	public static String uploadDirectory =
			System.getProperty("user.dir")+"/src/main/resources/static/uploads";
	@Autowired
	public BookController(BookRepository bookrepository) {
		this.bookrepository = bookrepository;
	}

	@GetMapping("list")
	public String listbooks(Model model) {
		List<Book> liste = (List<Book>) bookrepository.findAll();
		if (liste.size() == 0)
			liste = null;
		model.addAttribute("books", liste);
		return "book/listBooks";
	}

	@GetMapping("add")
	public String showaddform(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		return "book/addBook";
	}

	@PostMapping("add")
	public String addBook(@Valid Book book, BindingResult result, Model model,@RequestParam("releaseDate")String date,@RequestParam("files") MultipartFile[] files) {
		if (result.hasErrors()) {
			return "book/addBook";
		}
		//image
		StringBuilder fileName = new StringBuilder();
		 MultipartFile file = files[0];
		 Path fileNameAndPath = Paths.get(uploadDirectory,
		file.getOriginalFilename());

		 fileName.append(file.getOriginalFilename());
		 try {
		Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
		e.printStackTrace();
		}
		 //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
	        //convert String to LocalDate
	      //LocalDate localDate = LocalDate.parse("2020/11/24", formatter);
		// book.setReleaseDate(localDate);
		 System.out.println("*********************************************************");
		 //System.out.println(localDate);
		 bookrepository.save(book);
		return "redirect:list";

	}

	@GetMapping("delete/{id}")
	public String deleteBook(@PathVariable("id") int id, Model model) {

		Book book = bookrepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
		bookrepository.delete(book);

		return "redirect:../list";
	}

	@GetMapping("edit/{id}")
	public String showBookFormToUpdate(@PathVariable("id") int id, Model model) {
		Book book = bookrepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
		model.addAttribute("book", book);
		return "book/updateBook";
	}

	@PostMapping("update")
	public String updateBook(@Valid Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "book/updateBook";
		}
		bookrepository.save(book);
		return "redirect:list";
	}

}