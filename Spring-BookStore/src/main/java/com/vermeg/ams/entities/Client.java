package com.vermeg.ams.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
//@Table(name = "client")
public class Client {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name = "id_client")
	private int idClient;
	
	@Column (name="firstName")
	//@NotBlank (message="required")
	private String firstName;
	
	@Column (name="lastName")
	//@NotBlank (message="required")
	private String lastName;
	
	@Column (name="adress")
	//@NotBlank (message="required")
	private String adress;
	
	@Column (name="mail")
	//@NotBlank (message="required")
	private String mail;
	
	@Column (name="phoneNumber")
	//@NotBlank (message="required")
	private int phoneNumber;
	
	@OneToMany (fetch=FetchType.LAZY,
			cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}
			)
	@JoinColumn(name="id_order")
	private List <Order> orders;
	
	public Client() {

	}
	public Client(String firstName, String lastName, String adress, String mail, int phoneNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.adress = adress;
		this.mail = mail;
		this.phoneNumber = phoneNumber;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", firstName=" + firstName + ", lastName=" + lastName + ", adress="
				+ adress + ", mail=" + mail + ", phoneNumber=" + phoneNumber + "]";
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
	
	

}
