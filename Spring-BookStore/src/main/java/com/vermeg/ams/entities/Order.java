package com.vermeg.ams.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
//@Table (name = "order")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_order")
	private int idOrder;
	
	@Column(name="orderDate")
	@NotBlank(message = "required")
	private LocalDate orderDate;
	
	
	
	
	public Order() {
		
	}

	public Order(LocalDate orderDate) {
		super();
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
	
	@OneToMany(mappedBy="co",fetch=FetchType.LAZY)
	private List <OrderDetails> lignecom;
	
	public String te() {
		
			//recuperer les meme id de order dans la table order_details
		 lignecom.get(0).getCo().getIdOrder();
		 
		 
		return "";
	}
	//select from id_book from orderdetail where id_order=?1
	//
	
	@ManyToOne(fetch=FetchType.LAZY,
			cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}
			)
	@JoinColumn(name="id_client")
	private Client client;




	public List<OrderDetails> getLignecom() {
		return lignecom;
	}

	public void setLignecom(List<OrderDetails> lignecom) {
		this.lignecom = lignecom;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	

}
