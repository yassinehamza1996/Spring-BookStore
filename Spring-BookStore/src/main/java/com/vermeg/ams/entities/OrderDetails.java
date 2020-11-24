package com.vermeg.ams.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
//@Table (name = "orders")
public class OrderDetails {
@Id
@GeneratedValue
@Column (name = "id_order_details")
private int idOrderDetails;

@Column (name = "order_quantity")
@NotBlank (message = "required")
private int orderQuantity;

@Column (name = "order_price")
@NotBlank (message = "required")
private double orderPrice;





public OrderDetails() {
	
}



public OrderDetails(int orderQuantity,double orderPrice) {
	
	this.orderQuantity = orderQuantity;
	this.orderPrice = orderPrice;
}



public int getIdOrderDetails() {
	return idOrderDetails;
}

public void setIdOrderDetails(int idOrderDetails) {
	this.idOrderDetails = idOrderDetails;
}

public int getOrderQuantity() {
	return orderQuantity;
}

public void setOrderQuantity(int orderQuantity) {
	this.orderQuantity = orderQuantity;
}

public double getOrderPrice() {
	return orderPrice;
}

public void setOrderPrice(double orderPrice) {
	this.orderPrice = orderPrice;
}

@Override
public String toString() {
	return "OrderDetails [idOrderDetails=" + idOrderDetails + ", orderQuantity=" + orderQuantity + ", orderPrice="
			+ orderPrice + "]";
}

@ManyToOne
@JoinColumn(name="lignec_com")
private Order co;

@ManyToOne
@JoinColumn(name="book_lc")
private Book book;





public Order getCo() {
	return co;
}



public void setCo(Order co) {
	this.co = co;
}



public Book getBook() {
	return book;
}



public void setBook(Book book) {
	this.book = book;
}


}
