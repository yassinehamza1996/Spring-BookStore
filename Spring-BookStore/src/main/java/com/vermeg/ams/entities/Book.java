package com.vermeg.ams.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
//@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue
	@Column(name = "id_book")
	private int idBook;

	@Column(name = "title")

	private String title;

	@Column(name = "price")

	private double price;

	@Column(name = "releaseDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+5:30")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate releaseDate;

	@Column(name = "author")

	private String author;

	@Column(name = "coverImage")
	private String coverImage;

//@Column (name="quantity")

//private int quantity;

	public Book() {

	}

	public Book(String title, double price, LocalDate releaseDate, String author, String coverImage)// , int quantity)
	{
		this.title = title;
		this.price = price;
		this.releaseDate = releaseDate;
		this.author = author;
		this.coverImage = coverImage;
		// this.quantity = quantity;
	}

	public int getIdBook() {
		return idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

//public int getQuantity() {
	// return quantity;
//}

//public void setQuantity(int quantity) {
	// this.quantity = quantity;
//}
	@Override
	public String toString() {
		return "Book [idBook=" + idBook + ", title=" + title + ", price=" + price + ", releaseDate=" + releaseDate
				+ ", author=" + author + ", coverImage=" + coverImage + "]";// + ", quantity=" + quantity + "]";
	}

	/*
	 * @JsonIgnore
	 * 
	 * @OneToMany(mappedBy="book",fetch=FetchType.LAZY) private Collection
	 * <OrderDetails> ligneco;
	 * 
	 * 
	 * 
	 * public Collection<OrderDetails> getLigneco() { return ligneco; }
	 * 
	 * public void setLigneco(Collection<OrderDetails> ligneco) { this.ligneco =
	 * ligneco; }
	 */

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinTable(name = "order_details", joinColumns = @JoinColumn(name = "id_book"), inverseJoinColumns = @JoinColumn(name = "id_order"))
	private List<Order> orders;

}
