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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Order_Details implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_details")
	private int id_details;

	@Column(name = "unit_price")
	private double unitprice;

	@Column(name = "total_quantity")
	private int totalquantity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH,CascadeType.REMOVE })
	@JoinColumn(name = "book_id")
	@JsonIgnore
	private Book books;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private Order orders;

	public double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}

	public int getTotalquantity() {
		return totalquantity;
	}

	public void setTotalquantity(int totalquantity) {
		this.totalquantity = totalquantity;
	}

	public Book getBooks() {
		return books;
	}

	public void setBooks(Book books) {
		this.books = books;
	}

	public Order getOrders() {
		return orders;
	}

	@Override
	public String toString() {
		return "Order_Details [id_details=" + id_details + ", unitprice=" + unitprice + ", totalquantity="
				+ totalquantity + ", books=" + books.getAuthor() + ", orders=" + orders.getIdOrder() + "]";
	}

	public int getOrdersid() {
		return orders.getIdOrder();
	}

	public void setOrders(Order orders) {
		this.orders = orders;
	}

	public Order_Details(double unitprice, int totalquantity, Book books, Order orders) {
		super();
		this.unitprice = unitprice;
		this.totalquantity = totalquantity;
		this.books = books;
		this.orders = orders;
	}

	public Order_Details(double unitprice, int totalquantity, Order orders) {
		super();
		this.unitprice = unitprice;
		this.orders = orders;
	}

	public Order_Details() {
	}

}
