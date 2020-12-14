package com.vermeg.ams.RestControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vermeg.ams.entities.Book;
import com.vermeg.ams.repositories.BookRepository;

@Service
public class OrderService {

	
	
	
	double ComputeTotalPrice(List<Book> lb) {
		double result=0.0;
		
		for(int i=0;i<lb.size();i++) {
			result+=lb.get(i).getPrice();
		}
		
		return result;
	}
	
	
}
