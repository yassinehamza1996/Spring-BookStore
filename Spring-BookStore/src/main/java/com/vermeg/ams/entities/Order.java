package com.vermeg.ams.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
//@Table (name = "order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_order")
	private int idOrder;

	@Column(name = "orderDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+5:30")

	private LocalDate orderDate;

	@Column(name = "price")
	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Order() {

	}


	public Order(LocalDate orderDate, double price) {
		super();
		this.orderDate = orderDate;
		this.price = price;
	}

	public Order(LocalDate orderDate) {

		this.orderDate = orderDate;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "Order [idOrder=" + idOrder + ", orderDate=" + orderDate + "]";
	}

	/*@OneToMany(mappedBy = "co", fetch = FetchType.LAZY)
	private List<OrderDetails> lignecom;*/

	// select from id_book from orderdetail where id_order=?1
	//

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinColumn(name = "id_client")
	private Client client;

	/*public List<OrderDetails> getLignecom() {
		return lignecom;
	}

	public void setLignecom(List<OrderDetails> lignecom) {
		this.lignecom = lignecom;
	}
*/
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	
	
	@ManyToMany(fetch=FetchType.LAZY,
			cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			 CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="order_details",
			joinColumns=@JoinColumn(name="id_order"),
			inverseJoinColumns=@JoinColumn(name="id_book")
			)	
	private List<Book> books;

	public void addmybooks(Book book) {
		books.add(book);
	}
	
	

/*public void addorderdetails(OrderDetails orderdetail) {
		this.lignecom.add(orderdetail);
	}
*/
}
