package com.pofigist.hibraLibra2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pofigist.hibraLibra2.UIField;

@Entity  
@Table(name= "books") 
public class Book {
	private int bookId;
	
	@UIField(title="ISBN", useInCreateDialog=true, showInTable=true)
	private String isbn;
	
	@UIField(title="Название", useInCreateDialog=true, showInTable=true)
	private String title;
	
	@UIField(title="Издатель", useInCreateDialog=true, showInTable=true)
	private String publisher;
	
	
	public Book() {
		
	}
	
	public Book(String isbn, String title, String publisher) {
		this.isbn = isbn;
		this.title = title;
		this.publisher = publisher;
	}
	
	@Id
	@Column(name="book_id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getBookId() {
		return bookId;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	@Column(name="isbn", nullable=false, unique=true, length=13)
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	@Column(name="title", nullable=false, unique=false, length=128)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="publisher", nullable=false, unique=false, length=128)
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	@Override
	public String toString() {
		return title + " (" + publisher + ")";
	}
}
